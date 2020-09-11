package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.data.HotWaresModel;
import com.hym.shop.data.SortWaresModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.HotWaresContract;
import com.hym.shop.presenter.contract.SortWaresContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SortWaresModule {

    private SortWaresContract.SortWaresView mView;


    public SortWaresModule(SortWaresContract.SortWaresView view){

        this.mView = view;
    }



    @FragmentScope
    @Provides
    public SortWaresContract.ISortWaresModel provideModel(ApiService apiService){

        return  new SortWaresModel(apiService);
    }

    @FragmentScope
    @Provides
    public SortWaresContract.SortWaresView provideView(){
        return  mView;
    }





}
