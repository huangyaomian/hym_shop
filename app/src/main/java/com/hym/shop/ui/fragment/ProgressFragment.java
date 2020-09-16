package com.hym.shop.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.hym.shop.R;
import com.hym.shop.app.MyApplication;
import com.hym.shop.common.exception.BaseException;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.presenter.BasePresenter;
import com.hym.shop.ui.BaseView;
import com.hym.shop.ui.activity.LoginActivity;
import com.hym.shop.ui.widget.WindowInsetsFrameLayout;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public abstract class ProgressFragment<T extends BasePresenter> extends Fragment implements BaseView {

    private LinearLayout mRootView;
    private View mViewProgress;
    private View mViewEmpty;
    private FrameLayout mViewContent;
    private TextView mTextError;
    private Button mLoginButton;
    private Button mRetryButton;
    private boolean isShowContent = true;
    private Toolbar mToolBar;
    private AppBarLayout mAppBarLayout;
    private View line;

    private boolean showBackBtn = false;

    protected MyApplication mMyApplication;

    @Inject
    public T mPresenter;

    private Unbinder mUnbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = (LinearLayout) inflater.inflate(R.layout.fragment_progress, container, false);
        mViewProgress = mRootView.findViewById(R.id.view_progress);
        mViewEmpty = mRootView.findViewById(R.id.view_empty);
        mViewContent = mRootView.findViewById(R.id.view_content);
        mTextError = mRootView.findViewById(R.id.text_tip);
        mLoginButton = mRootView.findViewById(R.id.login_btn);
        mRetryButton = mRootView.findViewById(R.id.retry);
        mToolBar = mRootView.findViewById(R.id.fragment_tool_bar);
        mAppBarLayout = mRootView.findViewById(R.id.appBarLayout);
        line = mRootView.findViewById(R.id.line);

        mTextError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyViewClick();
            }
        });

        return mRootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.requestApplyInsets();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMyApplication = (MyApplication) getActivity().getApplication();
        setupActivityComponent(mMyApplication.getAppComponent());
        setRealContentView();
        init();
        initToolbar();
        initView();
        initEvent();
    }



    public void initToolbar(){

        if (showBackBtn){
            ((AppCompatActivity)this.getActivity()).setSupportActionBar(mToolBar);

            ((AppCompatActivity)this.getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

            mToolBar.setNavigationIcon(
                    new IconicsDrawable(getContext())
                            .icon(Ionicons.Icon.ion_ios_arrow_back)
                            .sizeDp(16)
                            .color(getResources().getColor(R.color.theme_black)
                            )
            );

            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                getContext().onBackPressed();
                }
            });
        }

    }

    public void setToolBarTitle(String toolBarTitle) {
        mToolBar.setTitle(toolBarTitle);
    }

    public void showToolBar() {

        mToolBar.setVisibility(View.VISIBLE);
        mAppBarLayout.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
    }

    public void hideToolBar() {
        mToolBar.setVisibility(View.GONE);
        mAppBarLayout.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }

    public void setShowBackBtn(boolean isShowBackBtn) {
       this.showBackBtn = isShowBackBtn;
    }



    //子类实现此方法使其点击重新刷新页面
    public void onEmptyViewClick() {

    }

    protected void setRealContentView() {
        View realContentView = LayoutInflater.from(getActivity()).inflate(setLayoutResourceID(), mViewContent, true);
        mUnbinder = ButterKnife.bind(this, realContentView);
    }

    public void showProgressView() {
        Log.d("ProgressFragment", "showProgressView");
        showView(R.id.view_progress);
    }

    public void showContentView() {
        Log.d("hymmm", "showContentView");
        showView(R.id.view_content);
    }

    public void showEmptyView() {
        showView(R.id.view_empty);
    }

    public void showEmptyView(int resId) {
        showView(R.id.view_empty);
        mTextError.setText(resId);
    }

    public void showEmptyView(String msg) {
        Log.d("hymmm", "showEmptyView");
        showView(R.id.view_empty);
        mTextError.setText(msg);
    }

    public void showEmptyView(String msg, int errorCode) {
        showEmptyView(msg);
        if (errorCode == BaseException.ERROR_TOKEN || errorCode == BaseException.INVALID_TOKEN) {
            mLoginButton.setVisibility(View.VISIBLE);
            mLoginButton.setText("登录");
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        }else{
            mRetryButton.setVisibility(View.VISIBLE);
            mRetryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            });
        }
    }

    public void showView(int viewId) {
        for (int i = 0; i < mRootView.getChildCount(); i++) {
            if (mRootView.getChildAt(i).getId() == viewId || mRootView.getChildAt(i).getId() == R.id.appBarLayout) {
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    public void setShowContent(boolean b) {
        this.isShowContent = b;
    }

    protected void setFitsSystemWindows(boolean b){
        mRootView.setFitsSystemWindows(b);
    }

    @Override
    public void showLoading() {
        showProgressView();
    }

    @Override
    public void dismissLoading() {
        if (isShowContent) {
            showContentView();
        }
    }

    @Override
    public void showError(String msg, int errorCode) {
        Log.d("ProgressFragment", "showError");
        showEmptyView(msg, errorCode);
    }


    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID * * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    /**
     * 做一些初始化的操作
     */
    protected abstract void init();

    /**
     * 一些View的相关操作
     */
    protected abstract void initView();

    /**
     * 一些事件相關的監聽
     */
    protected abstract void initEvent();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}


