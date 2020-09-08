package com.hym.shop.ui.fragment;


import android.icu.text.DecimalFormat;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hym.shop.R;
import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerHomeCampaignComponent;
import com.hym.shop.dagger2.component.DaggerHotWaresComponent;
import com.hym.shop.dagger2.module.HomeCampaignModule;
import com.hym.shop.dagger2.module.HotWaresModule;
import com.hym.shop.presenter.HomeCampaignPresenter;
import com.hym.shop.presenter.HotWaresPresenter;
import com.hym.shop.presenter.contract.HomeCampaignContract;
import com.hym.shop.presenter.contract.HotWaresContract;
import com.hym.shop.ui.adapter.HomeCampaignAdapter;
import com.hym.shop.ui.adapter.HotWaresAdapter;
import com.hym.shop.ui.widget.SpaceItemDecoration4;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class HotWaresFragment extends ProgressFragment<HotWaresPresenter> implements HotWaresContract.HotWaresView {

    private final int STATUS_NORMAL = 1;
    private final int STATUS_MORE = 2;

    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    private HotWaresAdapter mAdapter;

    private int mCurPage = 1;
    private int mPageSize = 10;

    private int mStatus = STATUS_NORMAL;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerHotWaresComponent.builder().appComponent(appComponent).hotWaresModule(new HotWaresModule(this)).build().inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void initView() {
        initRefresh();
    }


    @Override
    protected void init() {

        showToolBar();

        //这里为了解决recycleview不能撑满全屏的问题，这里layoutManager不管你布局里是否设置，都不准确，所以需要在代码里
        //重新设置MATCH_PARENT
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SpaceItemDecoration4 dividerDecoration = new SpaceItemDecoration4(UIUtils.dp2px(8));
        mRecyclerView.addItemDecoration(dividerDecoration);
        mRecyclerView.setLayoutManager(layoutManager);


        mPresenter.getHotWares(true,mCurPage,mPageSize);
    }

    @Override
    protected void initEvent() {

    }

    private void  initRefresh() {
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mStatus = STATUS_NORMAL;
                mCurPage = 1;
                mSmartRefreshLayout.setEnableLoadMore(true);
                mPresenter.getHotWares(false,mCurPage,mPageSize);
            }
        });

        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mStatus = STATUS_MORE;
                mCurPage++;
                mPresenter.getHotWares(false,mCurPage,mPageSize);
            }
        });
    }




    @Override
    public void showHotWares(HotWares hotWares) {

        if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.finishRefresh();
        }

        if (mSmartRefreshLayout.isLoading()) {
            mSmartRefreshLayout.finishLoadMore();
        }



        switch (mStatus){
            case STATUS_NORMAL:
                mAdapter = new HotWaresAdapter();
                mAdapter.addData(hotWares.getList());
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
                mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
                break;
            case STATUS_MORE:
                mAdapter.getData().addAll(hotWares.getList());
                mAdapter.notifyItemRangeInserted(mAdapter.getData().size(),hotWares.getList().size());
                break;
        }



        if (mCurPage * mPageSize < hotWares.getTotalCount()) {
            mSmartRefreshLayout.setEnableLoadMore(true);
        }else {
            mSmartRefreshLayout.setEnableLoadMore(false);
        }
    }
}