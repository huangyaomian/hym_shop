package com.hym.shop.ui.fragment;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hym.shop.R;
import com.hym.shop.bean.HomeBean;
import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.common.imageloader.ImageLoadConfig;
import com.hym.shop.common.imageloader.ImageLoader;
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
import com.hym.shop.ui.widget.BannerLayout;
import com.hym.shop.ui.widget.SpaceItemDecoration3;
import com.hym.shop.ui.widget.SpaceItemDecoration4;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class HomeCampaignFragment extends ProgressFragment<HomeCampaignPresenter> implements HomeCampaignContract.HomeCampaignView {


    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    private HomeCampaignAdapter mAdapter;

    private LayoutInflater mLayoutInflater;
    private Banner mBanner;


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
        mAdapter = new HomeCampaignAdapter();
        initBanner();
        initRefresh();
    }

    private void initBanner(){

        mLayoutInflater= this.getLayoutInflater();

        View view = mLayoutInflater.inflate(R.layout.template_banner, null, false);
        mBanner = view.findViewById(R.id.template_banner);




        List<String> urls = new ArrayList<>(5);

        urls.add("https://img.cniao5.com/5608f3b5Nc8d90151.jpg");
        urls.add("https://img.cniao5.com/5608eb8cN9b9a0a39.jpg");
        urls.add("https://img.cniao5.com/5608cae6Nbb1a39f9.jpg");



        //—————————————————————————如果你想偷懒，而又只是图片轮播————————————————————————
        mBanner.setAdapter(new BannerImageAdapter<List<String>>(Collections.singletonList(urls)) {
            @Override
            public void onBindView(BannerImageHolder holder, List<String> urls, int position, int size) {
                ImageLoader.load(urls.get(position), holder.imageView);
            }
        })
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getContext()));
        //更多使用方法仔细阅读文档，或者查看demo

        mAdapter.addHeaderView(view);
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


        mPresenter.getHomeRecommend(true);
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
                mPresenter.getHomeRecommend(false);
            }
        });
    }




    @Override
    public void showHomeRecommend(List<HomeCampaign> list) {

        if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.finishRefresh();
        }









        mAdapter.addData(list);

        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
    }
}
