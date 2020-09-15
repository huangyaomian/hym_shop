package com.hym.shop.data;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.LoginBean;
import com.hym.shop.bean.User;
import com.hym.shop.bean.requestbean.LoginRequestBean;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.LoginContract;

import io.reactivex.Observable;


public class LoginModel implements LoginContract.ILoginModel {


    private ApiService mApiService;

    public LoginModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    @Override
    public Observable<BaseBean<User>> login(String phone, String pwd) {
//        LoginRequestBean param = new LoginRequestBean();
//        param.setEmail(phone);
//        param.setPassword(pwd);
        return mApiService.shopLogin(phone,pwd);
    }

    @Override
    public Observable<LoginBean> reg(String phone, String pwd) {
        LoginRequestBean param = new LoginRequestBean();
        param.setEmail(phone);
        param.setPassword(pwd);
        return mApiService.shopReg(param);
    }
}
