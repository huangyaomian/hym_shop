package com.hym.shop.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hym.shop.R;
import com.hym.shop.bean.FragmentInfo;
import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.rx.RxBus;
import com.hym.shop.common.utils.ACache;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerMainComponent;
import com.hym.shop.dagger2.module.MainModule;
import com.hym.shop.presenter.MainPresenter;
import com.hym.shop.presenter.contract.MainContract;
import com.hym.shop.ui.adapter.MyViewPagerAdapter;
import com.hym.shop.ui.fragment.GameFragment;
import com.hym.shop.ui.fragment.HomeFragment;
import com.hym.shop.ui.fragment.RankingFragment;
import com.hym.shop.ui.fragment.SortFragment;
import com.hym.shop.ui.widget.BadgeActionProvider;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MainActivity extends ProgressActivity<MainPresenter> implements MainContract.MainView {


    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.nav_view)
    BottomNavigationView mNavView;
    @BindView(R.id.drawer_layout)
    LinearLayout mDrawerLayout;


    private long lastClickTime = 0;

    private BadgeActionProvider badgeActionProvider;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void init() {

        RxBus.getDefault().toObservable(User.class).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {

            }
        });

        mPresenter.requestPermission();

        mPresenter.getAppUpdateInfo();

    }

    @Override
    public void initToolbar() {
        hideToolbar();
    }

    private List<Fragment> initFragments() {
        List<Fragment> mFragments = new ArrayList<>(5);
        mFragments.add(new HomeFragment());
        mFragments.add(new RankingFragment());
        mFragments.add(new GameFragment());
        mFragments.add(new SortFragment());
        mFragments.add(new SortFragment());
        return mFragments;
    }


    @Override
    public void initView() {

        initViewpager();
    }

    private void initViewpager() {
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), initFragments());
        mainViewpager.setAdapter(myViewPagerAdapter);
        mainViewpager.setOffscreenPageLimit(5);
    }


    @Override
    public void initEvent() {
        //BottomNavigationView 点击事件监听
        mNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int menuId = menuItem.getItemId();
                // 跳转指定页面：Fragment
                switch (menuId) {
                    case R.id.navigation_home:
                        mainViewpager.setCurrentItem(0);
                        break;
                    case R.id.navigation_selling:
                        mainViewpager.setCurrentItem(1);
                        break;
                    case R.id.navigation_sort:
                        mainViewpager.setCurrentItem(2);
                        break;
                    case R.id.navigation_shopping_cart:
                        mainViewpager.setCurrentItem(3);
                        break;
                    case R.id.navigation_mine:
                        mainViewpager.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });


        // ViewPager 滑动事件监听
        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //将滑动到的页面对应的 menu 设置为选中状态
                mNavView.getMenu().getItem(i).setChecked(true);
                if (mNavView.getMenu().getItem(i).getItemId() ==R.id.navigation_home) {
                    hideToolbar();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.toolbar, menu);
//        menu.getItem(2).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_trash_outline).color(getResources().getColor(R.color.TextColor)).actionBar());
//        MenuItem downloadMenuItem = toolbar.getMenu().findItem(R.id.delete);
//        badgeActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(downloadMenuItem);
//        menu.findItem(R.id.search).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_search).color(getResources().getColor(R.color.TextColor)).actionBar());

        return true;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        badgeActionProvider.setIcon(DrawableCompat.wrap(new IconicsDrawable(this, Ionicons.Icon.ion_ios_cloud_download_outline).color(ContextCompat.getColor(this, R.color.TextColor))));
//        badgeActionProvider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(MainActivity.this, AppManagerActivity.class);
////                if (badgeActionProvider.getBadgeNum() > 0) {
////                    intent.putExtra(Constant.POSITION,2);
////                }
////                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home://Toolbar 的最左边加入一个导航按钮；引得用户滑动
//
//                break;
//            case R.id.search:
//                startActivity(SearchActivity.class);
//                break;
///*            case R.id.setting:
//                Toast.makeText(this, "you clicked 11", Toast.LENGTH_SHORT).show();
//                break;*/
//            case R.id.setting2:
//                Toast.makeText(this, "you clicked 22", Toast.LENGTH_SHORT).show();
//                break;
//        }
        return true;
    }

//    private void logout() {
//        headerView.setEnabled(true);
//        ACache aCache = ACache.get(this);
//        aCache.put(Constant.TOKEN, "");
//        aCache.put(Constant.USER, "");
//        mUserHeadView.setImageDrawable(new IconicsDrawable(this, Cniao5Font.Icon.cniao_head).colorRes(R.color.theme_while));
//        mTextUserName.setText(R.string.no_login);
//        mTextUserPhone.setText(R.string.phone_num);
//        headerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });
//        Toast.makeText(MainActivity.this, "退出登錄", Toast.LENGTH_LONG).show();
//        navigationView.getMenu().setGroupVisible(R.id.group2,false);
//        mUserHeadView.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_no_log));
//    }

    private void initUser() {
        Object objUser = ACache.get(this).getAsObject(Constant.USER);
        if (objUser == null) {

        } else {
            User user = (User) objUser;
        }
    }


    @Override
    public void onBackPressed() {
        if (lastClickTime <= 0) {
            Toast.makeText(this, "再点击一下，退出应用", Toast.LENGTH_SHORT).show();
            lastClickTime = System.currentTimeMillis();
        } else {
            if ((System.currentTimeMillis() - lastClickTime) > 3000) {  //这里3000，表示两次点击的间隔时间
                Toast.makeText(this, "再点击一下，退出应用", Toast.LENGTH_SHORT).show();
                lastClickTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }

        }

    }


    @Override
    public void requestPermissionSuccess() {
        initUser();
    }

    @Override
    public void requestPermissionFail() {
        Toast.makeText(MainActivity.this, "授权失败....", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeAppNeedUpdateCount(int count) {
        if (count > 0) {
//            badgeActionProvider.setText(count + "");
        } else {
//            badgeActionProvider.hideBadge();
        }
    }


}
