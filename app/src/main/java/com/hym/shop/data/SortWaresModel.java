package com.hym.shop.data;

import com.hym.shop.bean.HotWares;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.HotWaresContract;
import com.hym.shop.presenter.contract.SortWaresContract;

import io.reactivex.Observable;
import retrofit2.http.Query;


public class SortWaresModel implements SortWaresContract.ISortWaresModel {


    private ApiService mApiService;


    public SortWaresModel(ApiService apiService){

        this.mApiService = apiService;
    }



    @Override
    public Observable<HotWares> getSortWares( int curPage,  int pageSize,  int campaignId,  int orderBy) {
        return  mApiService.getCampaignWaresList(curPage,pageSize,campaignId,orderBy);
    }
}
