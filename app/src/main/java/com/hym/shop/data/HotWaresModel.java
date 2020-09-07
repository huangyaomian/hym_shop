package com.hym.shop.data;

import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.bean.HotWares;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.HomeCampaignContract;
import com.hym.shop.presenter.contract.HotWaresContract;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;


public class HotWaresModel implements HotWaresContract.IHotWaresModel {


    private ApiService mApiService;


    public HotWaresModel(ApiService apiService){

        this.mApiService = apiService;
    }



    @Override
    public Observable<HotWares> getHotWares(int curPage, int pageSize) {
        return  mApiService.getHotWares(curPage,pageSize);
    }
}
