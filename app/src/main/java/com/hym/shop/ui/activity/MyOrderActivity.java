package com.hym.shop.ui.activity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hym.shop.R;
import com.hym.shop.bean.FragmentInfo;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.ui.adapter.MyViewPagerAdapter2;
import com.hym.shop.ui.fragment.MyOrderFragment;
import com.hym.shop.ui.fragment.SortWaresFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyOrderActivity extends ProgressActivity {


    public final static int ORDER_STATUS_ALL =2;
    public final static int ORDER_STATUS_SUCCESS =1;
    public final static int ORDER_STATUS_UNPAID =0;
    public final static int ORDER_STATUS_FAIL =-2;



    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.main_tab_layout)
    TabLayout mMainTabLayout;


    private List<FragmentInfo> mFragments = new ArrayList<>(4);


    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_up_tab_layout;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }


    @Override
    public void init() {
        setShowToolBarBack(true);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        setToolBarTitle("我的订单");

    }

    private List<FragmentInfo> initFragments() {
        mFragments.add(new FragmentInfo("全部", new MyOrderFragment(ORDER_STATUS_ALL)));
        mFragments.add(new FragmentInfo("已成功", new MyOrderFragment(ORDER_STATUS_SUCCESS)));
        mFragments.add(new FragmentInfo("待支付", new MyOrderFragment(ORDER_STATUS_UNPAID)));
        mFragments.add(new FragmentInfo("已失败", new MyOrderFragment(ORDER_STATUS_FAIL)));
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
