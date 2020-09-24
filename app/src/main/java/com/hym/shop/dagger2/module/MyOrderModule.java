package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.data.MyOrderModel;
import com.hym.shop.data.SortWaresModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.MyOrderContract;
import com.hym.shop.presenter.contract.SortWaresContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MyOrderModule {

    private MyOrderContract.MyOrderView mView;


    public MyOrderModule(MyOrderContract.MyOrderView view){

        this.mView = view;
    }



    @FragmentScope
    @Provides
    public MyOrderContract.IMyOrderModel provideModel(ApiService apiService){

        return  new MyOrderModel(apiService);
    }

    @FragmentScope
    @Provides
    public MyOrderContract.MyOrderView provideView(){
        return  mView;
    }





}
