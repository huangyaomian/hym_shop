package com.hym.shop.data;

import com.hym.shop.bean.AppInfoBean;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.data.okhttp.ApiService;

import java.util.List;

import io.reactivex.Observable;


public class RecommendModel {

    private ApiService mApiService;

    public RecommendModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }




    public Observable<BaseBean<List<AppInfoBean>>> getRecommendRequest(){
        return  mApiService.getApps("{'page':0}");
    }
}
