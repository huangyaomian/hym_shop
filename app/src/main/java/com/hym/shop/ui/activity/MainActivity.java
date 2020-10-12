package com.hym.shop.ui.activity;

import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hym.shop.R;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.module.MainModule;
import com.hym.shop.presenter.contract.MainContract;
import com.hym.shop.ui.adapter.MyViewPagerAdapter;
import com.hym.shop.ui.fragment.CategoryFragment;
import com.hym.shop.ui.fragment.HomeCampaignFragment;
import com.hym.shop.ui.fragment.HotWaresFragment;
import com.hym.shop.ui.fragment.MineFragment;
import com.hym.shop.ui.fragment.ShoppingCarFragment;
import com.hym.shop.ui.widget.BadgeActionProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//public class MainActivity extends ProgressActivity<MainPresenter> implements MainContract.MainView {
public class MainActivity extends ProgressActivity implements MainContract.MainView {


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
//        DaggerMainComponent.builder().appComponent(appComponent)
//                .mainModule(new MainModule(this))
//                .build()
//                .inject(this);
    }


    @Override
    public void init() {
//        setFitsSystemWindows(false);
       /* RxBus.getDefault().toObservable(User.class).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {

            }
        });*/

//        mPresenter.requestPermission();

//        mPresenter.getAppUpdateInfo();

    }

    @Override
    public void initToolbar() {
        hideToolbar();
    }

    private List<Fragment> initFragments() {
        List<Fragment> mFragments = new ArrayList<>(5);
        mFragments.add(new HomeCampaignFragment());
        mFragments.add(new HotWaresFragment());
        mFragments.add(new CategoryFragment());
        mFragments.add(new ShoppingCarFragment());
        mFragments.add(new MineFragment());
        return mFragments;
    }


    @Override
    public void initView() {
//        StatusBarUtil.setWindowStatusBarColor(this, Color.TRANSPARENT);
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
//        initUser();
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
