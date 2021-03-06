package com.hym.shop.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.hym.shop.R;
import com.hym.shop.bean.FragmentInfo;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.ui.adapter.MyViewPagerAdapter2;
import com.hym.shop.ui.fragment.SortWaresFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;


public class CampaignWaresActivity extends ProgressActivity {


    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.main_tab_layout)
    TabLayout mMainTabLayout;

    private int campaignId = 0;

    public static final int PAGE_LIST = 1;
    public static final int PAGE_GIRD = 2;

    private int mPageStatus = PAGE_LIST;

    private List<FragmentInfo> mFragments = new ArrayList<>(3);


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

        campaignId = (int) getIntent().getSerializableExtra("campaignId");
//        if (campaignId == 0){
//            //获取link界面传输的数据，取字段data数据
//            Bundle bundle = getIntent().getExtras();
//            Set<String> set = bundle.keySet();
//            Log.d("hymmmm", "init: " + "   value:" + bundle.get("schemeData"));
//            Gson gson = new Gson();
//
//            Gson schemeData = (Gson) bundle.get("schemeData");
//            try {
//                Log.d("hymmmm", "init: " + "   value:" + schemeData.get("campaignId"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
    }


    @Override
    public void initToolbar() {
        super.initToolbar();
        setToolBarTitle("商品列表");

    }


    private List<FragmentInfo> initFragments() {
        mFragments.add(new FragmentInfo("默认", new SortWaresFragment(campaignId, SortWaresFragment.DEFAULT_SORT)));
        mFragments.add(new FragmentInfo("价格", new SortWaresFragment(campaignId, SortWaresFragment.PRICE_SORT)));
        mFragments.add(new FragmentInfo("销量", new SortWaresFragment(campaignId, SortWaresFragment.SALES_SORT)));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        menu.findItem(R.id.upper_right_corner).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_grid_view_outline).color(getResources().getColor(R.color.TextColor)).actionBar());
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upper_right_corner:
                if(mPageStatus == PAGE_LIST){
                    mPageStatus= PAGE_GIRD;
                    item.setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_list_outline).color(getResources().getColor(R.color.TextColor)).actionBar());
                } else if(mPageStatus == PAGE_GIRD){
                    mPageStatus= PAGE_LIST;
                    item.setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_grid_view_outline).color(getResources().getColor(R.color.TextColor)).actionBar());
                }
                for (int i = 0; i < mFragments.size(); i++) {
                    ((SortWaresFragment)mFragments.get(i).getFragment()).UpdateUI(mPageStatus);
                }
                break;
        }
        return true;
    }


}
