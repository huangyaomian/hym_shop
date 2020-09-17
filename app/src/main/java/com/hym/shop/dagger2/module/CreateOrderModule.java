package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.ActivityScope;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.data.CreateOrderModel;
import com.hym.shop.data.ShoppingCarModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.CreateOrderContract;
import com.hym.shop.presenter.contract.ShoppingCarContract;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateOrderModule {

    private CreateOrderContract.CreateOrderView mView;


    public CreateOrderModule(CreateOrderContract.CreateOrderView view){

        this.mView = view;
    }


    @ActivityScope
    @Provides
    public CreateOrderContract.ICreateOrderModel provideModel(ApiService apiService){

        return  new CreateOrderModel(apiService);
    }

    @ActivityScope
    @Provides
    public CreateOrderContract.CreateOrderView provideView(){
        return  mView;
    }





}
