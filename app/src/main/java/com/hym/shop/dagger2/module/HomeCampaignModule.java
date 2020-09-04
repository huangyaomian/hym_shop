package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.data.HomeCampaignModel;
import com.hym.shop.data.SearchModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.HomeCampaignContract;
import com.hym.shop.presenter.contract.SearchContract;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeCampaignModule {

    private HomeCampaignContract.HomeCampaignView mView;


    public HomeCampaignModule(HomeCampaignContract.HomeCampaignView view){

        this.mView = view;
    }



    @FragmentScope
    @Provides
    public HomeCampaignContract.IHomeCampaignModel provideModel(ApiService apiService){

        return  new HomeCampaignModel(apiService);
    }

    @FragmentScope
    @Provides
    public HomeCampaignContract.HomeCampaignView provideView(){
        return  mView;
    }





}
