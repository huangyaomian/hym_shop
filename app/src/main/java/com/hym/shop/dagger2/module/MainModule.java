package com.hym.shop.dagger2.module;

import com.hym.shop.data.MainModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.MainContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainContract.MainView mView;


    public MainModule(MainContract.MainView view){
        this.mView = view;
    }



//    @FragmentScope
    @Provides
    public MainContract.MainView provideView(){
        return  mView;
    }


//    @FragmentScope
    @Provides
    public MainContract.IMainModel provideModel(ApiService apiService){
        return  new MainModel(apiService);
    }





}
