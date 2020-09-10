package com.hym.shop.ui.fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hym.shop.R;
import com.hym.shop.bean.Category;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.imageloader.ImageLoader;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerCategoryComponent;
import com.hym.shop.dagger2.module.CategoryModule;
import com.hym.shop.presenter.CategoryPresenter;
import com.hym.shop.presenter.contract.CategoryContract;
import com.hym.shop.ui.adapter.CategoryAdapter;
import com.hym.shop.ui.adapter.CategoryWaresAdapter;
import com.hym.shop.ui.widget.SpaceItemDecoration2;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.widget.progress.materialprogressbar.MaterialProgressBar;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.ScaleInTransformer;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class SalesSortWaresFragment extends ProgressFragment<CategoryPresenter> implements CategoryContract.CategoryView {

    private final int STATUS_NORMAL = 1;
    private final int STATUS_MORE = 2;

    @BindView(R.id.category)
    RecyclerView mCategoryRV;
    @BindView(R.id.category_wares)
    RecyclerView mCategoryWaresRV;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.view_progress)
    LinearLayout mProgressView;
    @BindView(R.id.progress)
    MaterialProgressBar mProgress;
    @BindView(R.id.loading)
    TextView mLoading;
    private CategoryAdapter mCategoryAdapter;
    private CategoryWaresAdapter mCategoryWaresAdapter;

    private int mCurPage = 1;
    private int mPageSize = 10;

    private int mStatus = STATUS_NORMAL;
    private int mCategoryFlag = 0;
    private int mCategoryId = 0;

    private LayoutInflater mLayoutInflater;
    private Banner mBanner;
    private View mView;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
//        DaggerCategoryComponent.builder().appComponent(appComponent).categoryModule(new CategoryModule(this)).build().inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView() {
        initBanner();
        initRefresh();
    }


    @Override
    protected void init() {

        showToolBar();

        mCategoryAdapter = new CategoryAdapter();
        mCategoryWaresAdapter = new CategoryWaresAdapter();

        SpaceItemDecoration2 dividerDecoration = new SpaceItemDecoration2(UIUtils.dp2px(4));
        mCategoryWaresRV.addItemDecoration(dividerDecoration);
        mCategoryWaresRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mCategoryRV.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.getCategory();

    }

    @Override
    protected void initEvent() {
        mCategoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                if (!(mCategoryAdapter.getData().get(position).isSelect())) {
                    mStatus = STATUS_NORMAL;
                    mCurPage = 1;
                    mCategoryId = mCategoryAdapter.getData().get(position).getId();
                    requestCategoryWares(mCategoryId);
                    mCategoryAdapter.getData().get(mCategoryFlag).setSelect(false);
                    mCategoryAdapter.getData().get(position).setSelect(true);
                    mCategoryAdapter.notifyItemChanged(mCategoryFlag);
                    mCategoryAdapter.notifyItemChanged(position);
                    mCategoryFlag = position;
                }
                mCategoryWaresRV.scrollToPosition(0);
            }
        });
    }

    private void initRefresh() {
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mStatus = STATUS_NORMAL;
                mCurPage = 1;
                mSmartRefreshLayout.setEnableLoadMore(true);
                requestCategoryWares(mCategoryId);
            }
        });

        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mStatus = STATUS_MORE;
                mCurPage++;
                mPresenter.getCategoryWares(mCurPage, mPageSize, mCategoryId, false);
            }
        });
    }

    private void initBanner(){
        mLayoutInflater= this.getLayoutInflater();
        mView = mLayoutInflater.inflate(R.layout.template_banner, null, false);
        mBanner = mView.findViewById(R.id.template_banner);

    }


    @Override
    public void showCategory(List<Category> categoryList) {

        categoryList.get(0).setSelect(true);
        mCategoryAdapter.addData(categoryList);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mCategoryAdapter);
        mCategoryRV.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
        mCategoryId = categoryList.get(0).getId();
        requestCategoryWaresAndBanner(mCategoryId);


    }

    @Override
    public void showCategoryWares(HotWares hotWares) {
        if (hotWares.getList() == null || hotWares.getList().size() == 0) {
            showEmpty();
        } else {
            showContent();
            if (mSmartRefreshLayout.isRefreshing()) {
                mSmartRefreshLayout.finishRefresh();
            }

            if (mSmartRefreshLayout.isLoading()) {
                mSmartRefreshLayout.finishLoadMore();
            }

            switch (mStatus){
                case STATUS_NORMAL:
                    if (hotWares.getBanners() != null && hotWares.getBanners().size()>0) {
                        mBanner.setAdapter(new BannerImageAdapter<com.hym.shop.bean.Banner>(hotWares.getBanners()) {
                            @Override
                            public void onBindView(BannerImageHolder holder, com.hym.shop.bean.Banner data, int position, int size) {
                                ImageLoader.load(data.getImgUrl(), holder.imageView);
                            }


                        })
                                .addBannerLifecycleObserver(this)//添加生命周期观察者
                                .addPageTransformer(new ScaleInTransformer())
                                .setIndicator(new CircleIndicator(getContext()));

                        mCategoryWaresAdapter.addHeaderView(mView);
                    }


                    if (mCategoryWaresAdapter.getData() != null && mCategoryWaresAdapter.getData().size() > 0) {
                        mCategoryWaresAdapter.getData().clear();
                    }

                    mCategoryWaresAdapter.addData(hotWares.getList());
                    SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mCategoryWaresAdapter);
                    mCategoryWaresRV.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
                    break;
                case STATUS_MORE:
                    mCategoryWaresAdapter.getData().addAll(hotWares.getList());
                    mCategoryWaresAdapter.notifyItemRangeInserted(mCategoryWaresAdapter.getData().size(),hotWares.getList().size());
                    break;
            }

        }


        if (mCurPage * mPageSize < hotWares.getTotalCount()) {
            mSmartRefreshLayout.setEnableLoadMore(true);
        }else {
            mSmartRefreshLayout.setEnableLoadMore(false);
        }

    }

    private void showDialog() {
        mProgress.setVisibility(View.VISIBLE);
        mLoading.setText(R.string.xui_tip_loading_message);
        mProgressView.setVisibility(View.VISIBLE);
        mSmartRefreshLayout.setVisibility(View.GONE);
    }

    private void showContent() {
        mProgressView.setVisibility(View.GONE);
        mSmartRefreshLayout.setVisibility(View.VISIBLE);
    }

    private void requestCategoryWares(int categoryId) {
        showDialog();
        mPresenter.getCategoryWares(mCurPage, mPageSize, categoryId, false);
    }

    private void requestCategoryWaresAndBanner(int categoryId) {
        showDialog();
        mPresenter.getCategoryWaresAndBanner(mCurPage, mPageSize, categoryId, false);
    }

    private void showEmpty(){
        showDialog();
        mProgress.setVisibility(View.GONE);
        mLoading.setText("暫無數據");
    }
}
