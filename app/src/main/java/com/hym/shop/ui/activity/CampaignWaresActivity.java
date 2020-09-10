package com.hym.shop.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.hym.shop.R;
import com.hym.shop.bean.FragmentInfo;
import com.hym.shop.common.Constant;
import com.hym.shop.common.imageloader.ImageLoader;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.ui.adapter.MyViewPagerAdapter2;
import com.hym.shop.ui.fragment.GameFragment;
import com.hym.shop.ui.fragment.HomeFragment;
import com.hym.shop.ui.fragment.RankingFragment;
import com.hym.shop.ui.fragment.SortFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampaignWaresActivity extends ProgressActivity {


    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.main_tab_layout)
    TabLayout mMainTabLayout;

    private int campaignId = 0;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_campaign_wares;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }


    @Override
    public void init() {
        campaignId = (int) getIntent().getSerializableExtra("campaignId");
    }

    @Override
    public void initToolbar() {
    }

    private List<FragmentInfo> initFragments() {
        List<FragmentInfo> mFragments = new ArrayList<>(3);
        mFragments.add(new FragmentInfo(getString(R.string.home_tab_recommend), HomeFragment.class));
        mFragments.add(new FragmentInfo(getString(R.string.home_tab_ranking), RankingFragment.class));
        mFragments.add(new FragmentInfo(getString(R.string.home_tab_game), GameFragment.class));
        return mFragments;
    }


    @Override
    public void initView() {
        initTabLayout();
    }

    private void initTabLayout() {
        MyViewPagerAdapter2 myViewPagerAdapter2 = new MyViewPagerAdapter2(getSupportFragmentManager(), initFragments());
        mainViewpager.setAdapter(myViewPagerAdapter2);
        mainViewpager.setOffscreenPageLimit(5);
        mMainTabLayout.setupWithViewPager(mainViewpager);
    }


    @Override
    public void initEvent() {

    }

}
