package com.hym.shop.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.LayoutInflaterCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.hym.shop.R;
import com.hym.shop.app.MyApplication;
import com.hym.shop.common.exception.BaseException;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.presenter.BasePresenter;
import com.hym.shop.ui.BaseView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.xuexiang.xui.utils.StatusBarUtils;

import org.json.JSONException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ProgressActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    private Unbinder mUnbinder;
    protected MyApplication mMyApplication;

    private LinearLayout mRootView;
    private View mViewProgress;
    private View mViewEmpty;
    private FrameLayout mViewContent;
    private TextView mTextError;
    private Button mLoginButton;
    private Toolbar mToolBar;
    private AppBarLayout mAppBarLayout;
    private View line;

    private boolean isShowToolBarBack = true;


    @Inject
    public T mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory2(getLayoutInflater(),new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        StatusBarUtils.initStatusBarStyle(this,false, ActivityCompat.getColor(this, R.color.theme_blue));
        mMyApplication = (MyApplication) getApplication();
        setupActivityComponent(mMyApplication.getAppComponent());

        mRootView = findViewById(R.id.root_view);

        mViewProgress = findViewById(R.id.view_progress);
        mViewEmpty = findViewById(R.id.view_empty);
        mViewContent = findViewById(R.id.view_content);
        mTextError = findViewById(R.id.text_tip);
        mLoginButton = findViewById(R.id.login_btn);
        mToolBar = findViewById(R.id.tool_bar);
        mAppBarLayout = findViewById(R.id.appBarLayout);
        line = findViewById(R.id.line);

        setRealContentView();
        mTextError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyViewClick();
            }
        });


        init();
        initToolbar();
        initView();
        initEvent();

    }

    /**
     * 设置是否显示toolbar的返回按钮
     * @param showToolBarBack
     */
    public void setShowToolBarBack(boolean showToolBarBack) {
        isShowToolBarBack = showToolBarBack;
    }

    /**
     * 设置toolbar的title
     * @param toolBarTitle
     */
    public void setToolBarTitle(String toolBarTitle) {
        mToolBar.setTitle(toolBarTitle);
    }

    /**
     * 获取toolbar的对象
     * @return
     */
    public Toolbar getToolBar() {
        return mToolBar;
    }

    /**
     * 初始化toolbar
     */
    public void initToolbar(){

        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (isShowToolBarBack){
            mToolBar.setNavigationIcon(
                    new IconicsDrawable(this)
                            .icon(Ionicons.Icon.ion_ios_arrow_back)
                            .sizeDp(16)
                            .color(getResources().getColor(R.color.theme_black)
                            )
            );

            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 设置component
     * @param appComponent
     */
    protected abstract void setupActivityComponent(AppComponent appComponent);

    /**
     * 启动另一个activity
     * @param c
     */
    protected void startActivity(Class c) {
        this.startActivity(new Intent(this, c));
    }

    /**
     * 做一些初始化的操作
     */
    public abstract void init();

    /**
     * 一些View的相关操作
     */
    public abstract void initView();

    /**
     * 一些事件相關的監聽
     */
    public abstract void initEvent();


    /**
     * 子类实现此方法使其点击重新刷新页面
     */
    public void onEmptyViewClick(){

    }

    /**
     * 子类设置实际布局
     */
    protected  void setRealContentView(){
        View realContentView = LayoutInflater.from(this).inflate(setLayoutResourceID(), mViewContent, true);
        mUnbinder = ButterKnife.bind(this, realContentView);
    }

    /**
     * 显示加载中的布局
     */
    public void showProgressView(){
        Log.d("ProgressFragment","showProgressView");
        showView(R.id.view_progress);
    }

    /**
     * 显示内容的布局
     */
    public void showContentView(){
        Log.d("ProgressFragment","showContentView");
        showView(R.id.view_content);
    }

    /**
     *  显示空布局
     */
    public void showEmptyView(){
        showView(R.id.view_empty);
    }

    /**
     *  显示空布局，并制定提示语
     * @param resId
     */
    public void showEmptyView(int resId){
        showView(R.id.view_empty);
        mTextError.setText(resId);
    }

    /**
     * 显示空布局，并制定提示语
     * @param msg
     */
    public void showEmptyView(String msg){
        showView(R.id.view_empty);
        mTextError.setText(msg);
    }

    /**
     * 显示空布局，并根据error code 判断否显示登陆按钮
     * @param msg
     * @param errorCode
     */
    public void showEmptyView(String msg, int errorCode){
        showEmptyView(msg);
        if (errorCode == BaseException.ERROR_TOKEN || errorCode == BaseException.INVALID_TOKEN) {
            mLoginButton.setVisibility(View.VISIBLE);
            mLoginButton.setText("登录");
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplication(),LoginActivity.class));
                }
            });
        }
    }

    /**
     * 显示指定布局
     * @param viewId
     */
    public void showView(int viewId){
        for (int i = 0; i < mRootView.getChildCount(); i++) {
            if (mRootView.getChildAt(i).getId() == viewId || mRootView.getChildAt(i).getId() == R.id.appBarLayout) {
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            }else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    /**
     * 隐藏toolbar
     */
    public void hideToolbar(){
        mAppBarLayout.setVisibility(View.GONE);
        mToolBar.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
    }


    @Override
    public void showLoading() {
        showProgressView();
    }

    @Override
    public void dismissLoading() {
        showContentView();
    }

    @Override
    public void showError(String msg,int errorCode) {
        Log.d("ProgressFragment","showError");
        showEmptyView(msg,errorCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}
