package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.data.HomeCampaignModel;
import com.hym.shop.data.HotWaresModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.HomeCampaignContract;
import com.hym.shop.presenter.contract.HotWaresContract;

import dagger.Module;
import dagger.Provides;

@Module
public class HotWaresModule {

    private HotWaresContract.HotWaresView mView;


    public HotWaresModule(HotWaresContract.HotWaresView view){

        this.mView = view;
    }



    @FragmentScope
    @Provides
    public HotWaresContract.IHotWaresModel provideModel(ApiService apiService){

        return  new HotWaresModel(apiService);
    }

    @FragmentScope
    @Provides
    public HotWaresContract.HotWaresView provideView(){
        return  mView;
    }





}
