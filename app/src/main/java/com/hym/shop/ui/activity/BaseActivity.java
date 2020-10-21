package com.hym.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.LayoutInflaterCompat;

import com.hym.shop.R;
import com.hym.shop.app.MyApplication;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.presenter.BasePresenter;
import com.hym.shop.ui.BaseView;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.StatusBarUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    private Unbinder mUnbinder;
    protected MyApplication mMyApplication;

    @Inject
    public T mPresenter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(),new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        mUnbinder = ButterKnife.bind(this);
        XUI.initTheme(this);
        mMyApplication = (MyApplication) getApplication();
        setupActivityComponent(mMyApplication.getAppComponent());
        init();
        initView();
        initEvent();
    }

    protected abstract int setLayoutResourceID();

    protected abstract void setupActivityComponent(AppComponent appComponent);



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }

        if (mPresenter != null) {
            mPresenter.removeDisposable();
        }
    }





    protected void startActivity(Class c) {
        this.startActivity(new Intent(this, c));
    }

    public abstract void init();
    public abstract void initView();
    public abstract void initEvent();


    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg, int errorCode) {

    }

    @Override
    public void dismissLoading() {
    }
}
