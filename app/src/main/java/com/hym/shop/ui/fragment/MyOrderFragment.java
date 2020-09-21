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

public class MyOrderFragment extends ProgressFragment<SortWaresPresenter> implements SortWaresContract.SortWaresView {


    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    private HotWaresAdapter mAdapter;


    private int mOrderStatus;

    private List<HotWares.WaresBean> waresList;

    private SpaceItemDecoration4 mSpaceItemDecoration4;


    public MyOrderFragment(int mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
//        DaggerSortWaresComponent.builder().appComponent(appComponent).sortWaresModule(new SortWaresModule(this)).build().inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void initView() {
        mAdapter = new HotWaresAdapter();
        initRefresh();
    }


    @Override
    protected void init() {
        hideToolBar();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSpaceItemDecoration4 = new SpaceItemDecoration4(UIUtils.dp2px(8));
        mRecyclerView.addItemDecoration(mSpaceItemDecoration4);
        mRecyclerView.setLayoutManager(layoutManager);

//        mPresenter.getSortWares(true,mCurPage,mPageSize,mCampaignId,mOrder);
    }

    @Override
    protected void initEvent() {

    }

    private void  initRefresh() {
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mSmartRefreshLayout.setEnableLoadMore(true);
//                mPresenter.getSortWares(false,mCurPage,mPageSize,mCampaignId,mOrder);
            }
        });



    }





    @Override
    public void showSortWares(HotWares hotWares) {

        if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.finishRefresh();
        }

        waresList = hotWares.getList();
        mAdapter.addData(hotWares.getList());
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));




    }



}
