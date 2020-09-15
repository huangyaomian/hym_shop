package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.data.MineModel;
import com.hym.shop.data.ShoppingCarModel;
import com.hym.shop.presenter.contract.MineContract;
import com.hym.shop.presenter.contract.ShoppingCarContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MineModule {

    private MineContract.MineView mView;


    public MineModule(MineContract.MineView view){

        this.mView = view;
    }


    @FragmentScope
    @Provides
    public MineContract.IMineModel provideModel(){

        return  new MineModel();
    }

    @FragmentScope
    @Provides
    public MineContract.MineView provideView(){
        return  mView;
    }





}
