package com.hym.shop.ui.activity;


import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.hym.shop.R;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.LoginBean;
import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.utils.DESUtil;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerLoginComponent;
import com.hym.shop.dagger2.module.LoginModule;
import com.hym.shop.presenter.LoginPresenter;
import com.hym.shop.presenter.contract.LoginContract;
import com.hym.shop.ui.widget.LoadingButton;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class LoginActivity extends ProgressActivity<LoginPresenter> implements LoginContract.LoginView {


    @BindView(R.id.txt_phone_edit)
    EditText txtPhoneEdit;
    @BindView(R.id.view_phone_wrapper)
    TextInputLayout viewPhoneWrapper;
    @BindView(R.id.txt_password_edit)
    EditText txtPasswordEdit;
    @BindView(R.id.view_password_wrapper)
    TextInputLayout viewPasswordWrapper;
    @BindView(R.id.btn_login)
    LoadingButton mBtnLogin;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.txt_register)
    TextView txtRegister;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);

    }

    @Override
    public void init() {
        setShowToolBarBack(true);
    }

    @Override
    public void initView() {

        setToolBarTitle("登錄");

        InitialValueObservable<CharSequence> obPhone = RxTextView.textChanges(txtPhoneEdit);
        InitialValueObservable<CharSequence> obPassword = RxTextView.textChanges(txtPasswordEdit);

        InitialValueObservable.combineLatest(obPhone, obPassword, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2) {
                return isPhoneValid(txtPhoneEdit.getText().toString().trim()) && isPsdValid(txtPasswordEdit.getText().toString().trim());
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                mBtnLogin.setEnabled(aBoolean);
            }
        });

        RxView.clicks(mBtnLogin).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mPresenter.login(txtPhoneEdit.getText().toString().trim(), DESUtil.encode(Constant.DES_KEY, txtPasswordEdit.getText().toString().trim()));
            }
        });

        txtPhoneEdit.setText("15829238397");
        txtPasswordEdit.setText("bb001314");
    }

    @Override
    public void initEvent() {

    }

    private boolean isPhoneValid(String phone){
        return phone.length() == 11;// && phone.startsWith("1");
    }

    private boolean isPsdValid(String password){
        return password.length() >= 6;
    }

    @Override
    public void checkPhoneError() {
        viewPhoneWrapper.setError("手机号码输入错误");
    }

    @Override
    public void checkPhoneSuccess() {
//        viewPhoneWrapper.setError("手机号码输入错误");
    }

    @Override
    public void loginSuccess(BaseBean<User> bean) {
        finish();
        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mBtnLogin.showLoading();
    }

    @Override
    public void showError(String msg,int errorCode) {
        mBtnLogin.showButtonText();
    }

    @Override
    public void dismissLoading() {
        mBtnLogin.showButtonText();
    }
}
