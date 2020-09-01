package com.hym.shop.ui.activity;

import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hym.shop.R;
import com.hym.shop.bean.AppInfoBean;
import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.imageloader.ImageLoader;
import com.hym.shop.common.rx.RxBus;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerAppDetailComponent;
import com.hym.shop.dagger2.module.AppDetailModule;
import com.hym.shop.presenter.AppDetailPresenter;
import com.hym.shop.presenter.contract.AppInfoContract;
import com.hym.shop.service.receiver.MyInstallListener;
import com.hym.shop.service.receiver.MyInstallReceiver;
import com.hym.shop.ui.adapter.AppInfoAdapter;
import com.hym.shop.ui.adapter.screenShortAdapter;
import com.hym.shop.ui.widget.SpaceItemDecoration3;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.xuexiang.xui.widget.textview.ExpandableTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;
import io.reactivex.functions.Consumer;


public class AppDetailsActivity3 extends ProgressActivity<AppDetailPresenter> implements AppInfoContract.AppDetailView, MyInstallListener {


    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;


    @BindView(R.id.view_gallery)
    RecyclerView viewGallery;

    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.view_introduction)
    ExpandableTextView viewIntroduction;
    @BindView(R.id.txt_update_time)
    TextView txtUpdateTime;
    @BindView(R.id.txt_version)
    TextView txtVersion;
    @BindView(R.id.txt_apk_size)
    TextView txtApkSize;
    @BindView(R.id.txt_publisher)
    TextView txtPublisher;
    @BindView(R.id.txt_publisher2)
    TextView txtPublisher2;
    @BindView(R.id.recycler_view_same_dev)
    RecyclerView recyclerViewSameDev;
    @BindView(R.id.recycler_view_relate)
    RecyclerView recyclerViewRelate;
    @BindView(R.id.layout_view_same_dev)
    LinearLayout layoutViewSameDev;
    @BindView(R.id.layout_view_relate)
    LinearLayout layoutViewRelate;
    @BindView(R.id.linearLayout_btn)
    LinearLayout mLinearLayoutBtn;



    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;



    private int mAppId;
    private LayoutInflater mLayoutInflater;
    private AppInfoAdapter mAppInfoAdapterSame;
    private AppInfoAdapter mAppInfoAdapterRelate;
    private AppInfoBean mAppInfoBean;
    private MyInstallReceiver mMyInstallReceiver;
    private int mFlag = 0;



    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_app_details3;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerAppDetailComponent.builder().appComponent(appComponent).appDetailModule(new AppDetailModule(this))
                .build().injectActivity(this);
    }



    @Override
    public void init() {

        initToolbar();

        registerMyInstallReceiver();

        mAppInfoBean = (AppInfoBean) getIntent().getSerializableExtra("appInfo");
        if (mAppInfoBean != null) {
            mToolbar.setTitle(mAppInfoBean.getDisplayName());
            mToolbarLayout.setTitle(mAppInfoBean.getDisplayName());
            ImageLoader.load(Constant.BASE_IMG_URL + mAppInfoBean.getIcon(), imgIcon);
        }

        mAppId = mAppInfoBean.getId();

        mLayoutInflater = LayoutInflater.from(this);

    }




    @Override
    public void initView() {

        mPresenter.getAppDetail(mAppId);
    }

    @Override
    public void initEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        RxBus.getDefault().toObservable(User.class).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {
                mPresenter.getAppDetail(mAppId);
            }
        });
    }


    @Override
    public void showAppDetail(AppInfoBean appInfoBean) {


    }

    private void showScreenshot(String screenshot) {
        viewGallery.setNestedScrollingEnabled(false);
        List<String> urls = Arrays.asList(screenshot.split(","));
        List<String> completeUrls = new ArrayList<>();

        ImageInfo imageInfo;
        final List<ImageInfo> imageInfoList = new ArrayList<>();

        for (String url : urls) {
            imageInfo = new ImageInfo();
            imageInfo.setOriginUrl(Constant.HD_BASE_IMG_URL + url);// 原图url
            imageInfo.setThumbnailUrl(Constant.BASE_IMG_URL + url);// 缩略图url
            imageInfoList.add(imageInfo);
            completeUrls.add(Constant.BASE_IMG_URL + url);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局

        SpaceItemDecoration3 dividerDecoration = new SpaceItemDecoration3(UIUtils.dp2px(4));
        viewGallery.addItemDecoration(dividerDecoration);
        viewGallery.setLayoutManager(layoutManager);
        screenShortAdapter adapter = new screenShortAdapter();
        adapter.addData(completeUrls);
        viewGallery.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ImagePreview
                        .getInstance()
                        // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好；
                        .setContext(AppDetailsActivity3.this)
                        // 设置从第几张开始看（索引从0开始）
                        .setIndex(position)
                        // 1：第一步生成的imageInfo List
                        .setImageInfoList(imageInfoList)
                        .setShowCloseButton(true)
                        .start();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_details, menu); //加载toolbar.xml 菜单文件
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        Log.d("hymmm", "onPrepareOptionsMenu: mFlag=" + mFlag);
        super.onPrepareOptionsMenu(menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }


    private void registerMyInstallReceiver(){
        mMyInstallReceiver = new MyInstallReceiver();
        mMyInstallReceiver.registerListener(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addAction("android.intent.action.PACKAGE_REPLACED");
        filter.addDataScheme("package");
        this.registerReceiver(mMyInstallReceiver, filter);
    }

    @Override
    public void PackageAdded(String packageName) {
        Log.d("hymmm", "AppDetailsActivity2: " + "安装了应用："+packageName);


    }

    @Override
    public void PackageRemoved(String packageName) {
        Log.d("hymmm", "AppDetailsActivity2: " + "卸載了应用："+packageName);

    }

    @Override
    public void PackageReplaced(String packageName) {

    }

    @Override
    protected void onDestroy() {
        if(mMyInstallReceiver != null) {
            this.unregisterReceiver(mMyInstallReceiver);
        }
        super.onDestroy();
    }




}
