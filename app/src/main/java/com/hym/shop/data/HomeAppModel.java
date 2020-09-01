package com.hym.shop.data;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.HomeBean;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.HomeAppContract;

import io.reactivex.Observable;


public class HomeAppModel implements HomeAppContract.IHomeAppModel {


    private ApiService mApiService;

    public HomeAppModel(ApiService apiService){
        this.mApiService = apiService;
    }



    @Override
    public Observable<BaseBean<HomeBean>> getHomeApps() {
        return  mApiService.getHome();
    }
}
