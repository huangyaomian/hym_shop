package com.hym.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.LayoutInflaterCompat;

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

    public void setShowToolBarBack(boolean showToolBarBack) {
        isShowToolBarBack = showToolBarBack;
    }

    public void setToolBarTitle(String toolBarTitle) {
        mToolBar.setTitle(toolBarTitle);
    }

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




    protected abstract int setLayoutResourceID();

    protected abstract void setupActivityComponent(AppComponent appComponent);




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }





    protected void startActivity(Class c) {
        this.startActivity(new Intent(this, c));
    }

    public abstract void init();
    public abstract void initView();
    public abstract void initEvent();


    //子类实现此方法使其点击重新刷新页面
    public void onEmptyViewClick(){

    }


    protected  void setRealContentView(){
        View realContentView = LayoutInflater.from(this).inflate(setLayoutResourceID(), mViewContent, true);
        mUnbinder = ButterKnife.bind(this, realContentView);
    }

    public void showProgressView(){
        Log.d("ProgressFragment","showProgressView");
        showView(R.id.view_progress);
    }

    public void showContentView(){
        Log.d("ProgressFragment","showContentView");
        showView(R.id.view_content);
    }

    public void showEmptyView(){
        showView(R.id.view_empty);
    }

    public void showEmptyView(int resId){
        showView(R.id.view_empty);
        mTextError.setText(resId);
    }

    public void showEmptyView(String msg){
        showView(R.id.view_empty);
        mTextError.setText(msg);
    }

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

    public void showView(int viewId){
        for (int i = 0; i < mRootView.getChildCount(); i++) {
            if (mRootView.getChildAt(i).getId() == viewId || mRootView.getChildAt(i).getId() == R.id.appBarLayout) {
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            }else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
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
}
