package com.hym.shop.dagger2.module;

import com.hym.shop.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModelModule.class})
public class AppInfoModule {

    private AppInfoContract.AppInfoView mView;

    public AppInfoModule(AppInfoContract.AppInfoView mView) {
        this.mView = mView;
    }


    @Provides
    public AppInfoContract.AppInfoView provideView(){
        return mView;
    }





}
