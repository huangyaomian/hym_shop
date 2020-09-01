package com.hym.shop.data;


import com.hym.shop.bean.AppInfoBean;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.HomeBean;
import com.hym.shop.bean.PageBean;
import com.hym.shop.data.okhttp.ApiService;

import io.reactivex.Observable;

public class AppInfoModel {

    private ApiService mApiService;

    public AppInfoModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }


    public Observable<BaseBean<HomeBean>> getHomeRequest(){
        return  mApiService.getHome();
    }

    public Observable<BaseBean<PageBean<AppInfoBean>>> getRankingRequest(int page){
        return  mApiService.getTopList(page);
    }

    public Observable<BaseBean<PageBean<AppInfoBean>>> getGameRequest(int page){
        return  mApiService.getGames(page);
    }

    public Observable<BaseBean<PageBean<AppInfoBean>>> getFeaturedAppsBySort(int categoryid, int page) {
        return mApiService.getFeaturedAppsBySort(categoryid, page);
    }

    public Observable<BaseBean<PageBean<AppInfoBean>>> getTopListAppsBySort(int categoryid, int page) {
        return mApiService.getTopListAppsBySort(categoryid, page);
    }

    public Observable<BaseBean<PageBean<AppInfoBean>>> getNewListAppsBySort(int categoryid, int page) {
        return mApiService.getNewListAppsBySort(categoryid, page);
    }

    public Observable<BaseBean<AppInfoBean>> getAppDetail(int id) {
        return mApiService.getAppDetail(id);
    }



}
