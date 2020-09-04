package com.hym.shop.ui.fragment;


import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hym.shop.R;
import com.hym.shop.bean.HomeBean;
import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerHomeCampaignComponent;
import com.hym.shop.dagger2.component.DaggerHomeComponent;
import com.hym.shop.dagger2.component.DaggerSearchComponent;
import com.hym.shop.dagger2.module.HomeCampaignModule;
import com.hym.shop.dagger2.module.HomeModule;
import com.hym.shop.dagger2.module.SearchModule;
import com.hym.shop.presenter.HomeCampaignPresenter;
import com.hym.shop.presenter.HomePresenter;
import com.hym.shop.presenter.contract.AppInfoContract;
import com.hym.shop.presenter.contract.HomeCampaignContract;
import com.hym.shop.ui.adapter.HomeAdapter;
import com.hym.shop.ui.adapter.HomeCampaignAdapter;
import com.hym.shop.ui.widget.SpaceItemDecoration3;
import com.hym.shop.ui.widget.SpaceItemDecoration4;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class HomeCampaignFragment extends ProgressFragment<HomeCampaignPresenter> implements HomeCampaignContract.HomeCampaignView {


    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mySwipeRefreshLayout;
    private HomeCampaignAdapter mAdapter;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

        DaggerHomeCampaignComponent.builder().appComponent(appComponent).homeCampaignModule(new HomeCampaignModule(this)).build().inject(this);

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void initView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
//        mySwipeRefreshLayout.setProgressViewOffset(true, 50, 200);

//设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        mySwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

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
        SpaceItemDecoration4 dividerDecoration = new SpaceItemDecoration4(UIUtils.dp2px(16));
        mRecyclerView.addItemDecoration(dividerDecoration);
        mRecyclerView.setLayoutManager(layoutManager);


        mPresenter.getHomeRecommend(true);
    }

    @Override
    protected void initEvent() {
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHomeRecommend(false);
            }
        });
    }


//    @Override
//    public void showResult(HomeBean homeBean) {
//        if (mySwipeRefreshLayout.isRefreshing()) {
//            mySwipeRefreshLayout.setRefreshing(false);
//        }
//
//        mAdapter = new HomeAdapter(getActivity(), homeBean);
//
//        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
//        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
//        Log.d("showResult", String.valueOf(mRecyclerView.getChildCount()));
//
//    }



    @Override
    public void showHomeRecommend(List<HomeCampaign> list) {

        if (mySwipeRefreshLayout.isRefreshing()) {
            mySwipeRefreshLayout.setRefreshing(false);
        }

        mAdapter = new HomeCampaignAdapter();
        mAdapter.addData(list);

        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
    }
}
