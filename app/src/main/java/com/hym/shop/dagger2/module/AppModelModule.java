package com.hym.shop.dagger2.module;

import com.hym.shop.data.AppInfoModel;
import com.hym.shop.data.okhttp.ApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModelModule {


    @Provides
    public AppInfoModel provideModel(ApiService apiService){
        return new AppInfoModel(apiService);
    }


}
