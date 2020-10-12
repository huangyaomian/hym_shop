package com.hym.shop.data;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.requestbean.AppsUpdateBean;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.MainContract;

import java.util.List;

import io.reactivex.Observable;


public class MainModel implements MainContract.IMainModel {


    private ApiService mApiService;

    public MainModel(ApiService apiService){
        this.mApiService = apiService;
    }


    @Override
    public Observable<BaseBean<List<BaseBean>>> getUpdateApps(AppsUpdateBean param) {
//        return mApiService.getAppsUpdateinfo(param.getPackageName(),param.getVersionCode());
        return null;
    }
}
