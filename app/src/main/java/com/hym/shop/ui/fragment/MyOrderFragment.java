package com.hym.shop.ui.fragment;


import android.media.session.MediaSession;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.Order;
import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.utils.ACache;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerMyOrderComponent;
import com.hym.shop.dagger2.component.DaggerSortWaresComponent;
import com.hym.shop.dagger2.module.MyOrderModule;
import com.hym.shop.dagger2.module.SortWaresModule;
import com.hym.shop.presenter.MyOrderPresenter;
import com.hym.shop.presenter.SortWaresPresenter;
import com.hym.shop.presenter.contract.MyOrderContract;
import com.hym.shop.presenter.contract.SortWaresContract;
import com.hym.shop.ui.activity.CampaignWaresActivity;
import com.hym.shop.ui.adapter.HotWaresAdapter;
import com.hym.shop.ui.adapter.MyOrderAdapter;
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

public class MyOrderFragment extends ProgressFragment<MyOrderPresenter> implements MyOrderContract.MyOrderView {


    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    private MyOrderAdapter mAdapter;


    private int mOrderStatus;
    private long mUserId;
    private String mToken;

    private SpaceItemDecoration4 mSpaceItemDecoration4;


    public MyOrderFragment(int mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMyOrderComponent.builder().appComponent(appComponent).myOrderModule(new MyOrderModule(this)).build().inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void initView() {
        mAdapter = new MyOrderAdapter();
        initRefresh();

    }


    @Override
    protected void init() {
        hideToolBar();
        getUser();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSpaceItemDecoration4 = new SpaceItemDecoration4(UIUtils.dp2px(8));
        mRecyclerView.addItemDecoration(mSpaceItemDecoration4);
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.getMyOrder(mUserId,mOrderStatus, mToken,true);
    }

    @Override
    protected void initEvent() {

    }

    private void  initRefresh() {
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mSmartRefreshLayout.setEnableLoadMore(true);
                mPresenter.getMyOrder(mUserId,mOrderStatus, mToken,false);
            }
        });

    }



    @Override
    public void showMyOrder(List<Order> orders) {
        if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.finishRefresh();
        }
        mAdapter.addData(orders);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
    }

    private void getUser(){

        User user = (User) ACache.get(getContext()).getAsObject(Constant.USER);

        mToken = ACache.get(getContext()).getAsString(Constant.TOKEN);

        mUserId = user.getId();
    }
}
