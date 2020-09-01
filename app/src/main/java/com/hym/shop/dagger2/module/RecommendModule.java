package com.hym.shop.dagger2.module;


import com.hym.shop.data.RecommendModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.RecommendContract;
import com.hym.shop.ui.adapter.RecommendRVAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class RecommendModule {

    private RecommendContract.View mView;

    public RecommendModule(RecommendContract.View mView) {
        this.mView = mView;
    }


    @Provides
    public RecommendContract.View provideView(){
        return mView;
    }

 /*   @Provides
    public RecommendModel provideRecommendModel(RequestQueue mRequestQueue){
        return new RecommendModel(mRequestQueue);
    }*/

    @Provides
    public RecommendModel provideRecommendModel(ApiService apiService){
        return new RecommendModel(apiService);
    }

    public RecommendRVAdapter provideAdapter(){
        return  null;
    }


}
