package com.hym.shop.dagger2.module;

import com.hym.shop.data.LoginModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private LoginContract.LoginView mView;

    public LoginModule(LoginContract.LoginView mView) {
        this.mView = mView;
    }


    @Provides
    public LoginContract.LoginView provideView(){
        return mView;
    }


    @Provides
    public LoginContract.ILoginModel provideLoginModel(ApiService apiService){
        return new LoginModel(apiService);
    }


}
