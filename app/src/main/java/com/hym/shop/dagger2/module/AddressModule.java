package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.ActivityScope;
import com.hym.shop.data.AddressModel;
import com.hym.shop.data.CreateOrderModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.AddressContract;
import com.hym.shop.presenter.contract.CreateOrderContract;

import dagger.Module;
import dagger.Provides;

@Module
public class AddressModule {

    private AddressContract.AddressView mView;


    public AddressModule(AddressContract.AddressView view){

        this.mView = view;
    }


    @ActivityScope
    @Provides
    public AddressContract.IAddressModel provideModel(ApiService apiService){

        return  new AddressModel(apiService);
    }

    @ActivityScope
    @Provides
    public AddressContract.AddressView provideView(){
        return  mView;
    }





}
