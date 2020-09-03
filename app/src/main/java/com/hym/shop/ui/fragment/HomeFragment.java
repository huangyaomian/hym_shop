package com.hym.shop.ui.fragment;


import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hym.shop.R;
import com.hym.shop.bean.HomeBean;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerHomeComponent;
import com.hym.shop.dagger2.module.HomeModule;
import com.hym.shop.presenter.HomePresenter;
import com.hym.shop.presenter.contract.AppInfoContract;
import com.hym.shop.ui.adapter.HomeAdapter;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class HomeFragment extends ProgressFragment<HomePresenter> implements AppInfoContract.View {


    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mySwipeRefreshLayout;
    private HomeAdapter mAdapter;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerHomeComponent.builder().appComponent(appComponent).homeModule(new HomeModule(this)).build().inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void initView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        mySwipeRefreshLayout.setProgressViewOffset(true, 50, 200);

//设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        mySwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

// 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mySwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // 设定下拉圆圈的背景
//        mySwipeRefreshLayout.setBackground(getResources().getColor(R.color.theme_blue));
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
        mRecyclerView.setLayoutManager(layoutManager);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

//        mHomeRv.setItemAnimator(new DefaultItemAnimator());
        mPresenter.requestHomeData(true);
    }

    @Override
    protected void initEvent() {
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestHomeData(false);
            }
        });
    }


    @Override
    public void showResult(HomeBean homeBean) {
        if (mySwipeRefreshLayout.isRefreshing()) {
            mySwipeRefreshLayout.setRefreshing(false);
        }

        mAdapter = new HomeAdapter(getActivity(), homeBean);

        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
        Log.d("showResult", String.valueOf(mRecyclerView.getChildCount()));

    }


    @Override
    public void showNoData() {

    }

    @Override
    public void onRequestPermissionSuccess() {

    }

    @Override
    public void onRequestPermissionError() {
        Toast.makeText(getActivity(), "您已拒絕授權!", Toast.LENGTH_SHORT).show();
    }


}
