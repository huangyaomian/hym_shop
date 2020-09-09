package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.data.CategoryModel;
import com.hym.shop.data.ShoppingCarModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.CategoryContract;
import com.hym.shop.presenter.contract.ShoppingCarContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ShoppingCarModule {

    private ShoppingCarContract.ShoppingCarView mView;


    public ShoppingCarModule(ShoppingCarContract.ShoppingCarView view){

        this.mView = view;
    }


    @FragmentScope
    @Provides
    public ShoppingCarContract.IShoppingCarModel provideModel(){

        return  new ShoppingCarModel();
    }

    @FragmentScope
    @Provides
    public ShoppingCarContract.ShoppingCarView provideView(){
        return  mView;
    }





}
