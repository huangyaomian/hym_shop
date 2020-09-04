package com.hym.shop.data;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.bean.SearchResult;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.HomeCampaignContract;
import com.hym.shop.presenter.contract.SearchContract;

import java.util.List;

import io.reactivex.Observable;


public class HomeCampaignModel implements HomeCampaignContract.IHomeCampaignModel {


    private ApiService mApiService;


    public HomeCampaignModel(ApiService apiService){

        this.mApiService = apiService;
    }


    @Override
    public Observable<BaseBean<List<HomeCampaign>>> getHomeRecommend() {
        return  mApiService.getHomeCampaign();
    }
}
