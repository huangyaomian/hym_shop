package com.hym.shop.dagger2.module;

import com.hym.shop.data.SortModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.SortContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SortModule {

    private SortContract.SortView mView;

    public SortModule(SortContract.SortView mView) {
        this.mView = mView;
    }


    @Provides
    public SortContract.SortView provideView(){
        return mView;
    }


    @Provides
    public SortContract.ISortModel provideSortModel(ApiService apiService){
        return new SortModel(apiService);
    }


}
