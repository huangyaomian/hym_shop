package com.hym.shop.ui.fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerSortWaresComponent;
import com.hym.shop.dagger2.module.SortWaresModule;
import com.hym.shop.presenter.SortWaresPresenter;
import com.hym.shop.presenter.contract.SortWaresContract;
import com.hym.shop.ui.activity.CampaignWaresActivity;
import com.hym.shop.ui.adapter.HotWaresAdapter;
import com.hym.shop.ui.widget.SpaceItemDecoration2;
import com.hym.shop.ui.widget.SpaceItemDecoration4;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class SortWaresFragment extends ProgressFragment<SortWaresPresenter> implements SortWaresContract.SortWaresView {

    private final int STATUS_NORMAL = 1;
    private final int STATUS_MORE = 2;


    public static final int DEFAULT_SORT = 0;
    public static final int PRICE_SORT = 1;
    public static final int SALES_SORT = 2;


    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    private HotWaresAdapter mAdapter;

    private int mCurPage = 1;
    private int mPageSize = 10;

    private int mStatus = STATUS_NORMAL;

    private int mCampaignId;
    private int mOrder;

    private LayoutInflater mLayoutInflater;
    private TextView totalWares;
    private View mView;

    private List<HotWares.WaresBean> waresList;

    private SpaceItemDecoration4 mSpaceItemDecoration4;
    private SpaceItemDecoration2 mSpaceItemDecoration2;


    public SortWaresFragment(int campaignId, int mOrder) {
        this.mCampaignId = campaignId;
        this.mOrder = mOrder;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSortWaresComponent.builder().appComponent(appComponent).sortWaresModule(new SortWaresModule(this)).build().inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void initView() {
        mAdapter = new HotWaresAdapter();
        initRefresh();
        initHead();
    }


    @Override
    protected void init() {
        hideToolBar();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSpaceItemDecoration4 = new SpaceItemDecoration4(UIUtils.dp2px(8));
        mSpaceItemDecoration2 = new SpaceItemDecoration2(UIUtils.dp2px(8));
        mRecyclerView.addItemDecoration(mSpaceItemDecoration4);
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.getSortWares(true,mCurPage,mPageSize,mCampaignId,mOrder);
    }

    @Override
    protected void initEvent() {

    }

    private void  initRefresh() {
        mSmartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mStatus = STATUS_NORMAL;
                mCurPage = 1;
                mSmartRefreshLayout.setEnableLoadMore(true);
                mPresenter.getSortWares(false,mCurPage,mPageSize,mCampaignId,mOrder);
            }
        });

        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mStatus = STATUS_MORE;
                mCurPage++;
                mPresenter.getSortWares(false,mCurPage,mPageSize,mCampaignId,mOrder);
            }
        });

    }


    private void initHead(){

        mLayoutInflater= this.getLayoutInflater();
        mView = mLayoutInflater.inflate(R.layout.sort_wares_head, null, false);
        totalWares = mView.findViewById(R.id.total_wares);

        totalWares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUI(2);
            }
        });

    }




    @Override
    public void showSortWares(HotWares hotWares) {

        if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.finishRefresh();
        }



        switch (mStatus){
            case STATUS_NORMAL:
                waresList = hotWares.getList();
                totalWares.setText(String.format(getResources().getString(R.string.total_sort_wares),hotWares.getTotalCount()));
                if (!(mAdapter.getHeaderLayoutCount() > 0)) {
                    mAdapter.addHeaderView(mView);
                }

                mAdapter.addData(hotWares.getList());
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
                mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
                break;
            case STATUS_MORE:
                waresList.addAll(hotWares.getList());
                mAdapter.getData().addAll(hotWares.getList());
                mAdapter.notifyItemRangeInserted(mAdapter.getData().size(),hotWares.getList().size());
                break;
        }

        if (mCurPage * mPageSize < hotWares.getTotalCount()) {
            mSmartRefreshLayout.setEnableLoadMore(true);
        }else {
//            mSmartRefreshLayout.setEnableLoadMore(false);
            mSmartRefreshLayout.finishLoadMoreWithNoMoreData();//显示全部加载完成，并不再触发加载更事件
        }

        if (mSmartRefreshLayout.isLoading()) {
            mSmartRefreshLayout.finishLoadMore();
        }


    }


    public void UpdateUI(int pageStatus){

        mAdapter.removeHeaderView(mView);

        if (pageStatus == CampaignWaresActivity.PAGE_LIST){
            mRecyclerView.removeItemDecoration(mSpaceItemDecoration2);
            if (mRecyclerView.getItemDecorationCount() == 0){
                mRecyclerView.addItemDecoration(mSpaceItemDecoration4);
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new HotWaresAdapter();
        }else {
            mRecyclerView.removeItemDecoration(mSpaceItemDecoration4);
            if (mRecyclerView.getItemDecorationCount() == 0){
                mRecyclerView.addItemDecoration(mSpaceItemDecoration2);
            }
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            mAdapter = new HotWaresAdapter(R.layout.template_category_wares);

        }

        if (!(mAdapter.getHeaderLayoutCount() > 0)) {
            mAdapter.addHeaderView(mView);
        }

        mAdapter.addData(waresList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
