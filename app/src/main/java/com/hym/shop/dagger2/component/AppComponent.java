package com.hym.shop.dagger2.component;

import android.app.Application;

import com.google.gson.Gson;
import com.hym.shop.common.rx.RxErrorHandler;
import com.hym.shop.dagger2.module.AppModule;
import com.hym.shop.dagger2.module.HttpModule;
import com.hym.shop.data.okhttp.ApiService;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    public ApiService getApiService();

    public Application getApplication();

    public RxErrorHandler getRxErrorHandler();

    public Gson getGson();

}
