package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.data.CategoryModel;
import com.hym.shop.data.HotWaresModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.CategoryContract;
import com.hym.shop.presenter.contract.HotWaresContract;

import dagger.Module;
import dagger.Provides;

@Module
public class CategoryModule {

    private CategoryContract.CategoryView mView;


    public CategoryModule(CategoryContract.CategoryView view){

        this.mView = view;
    }



    @FragmentScope
    @Provides
    public CategoryContract.ICategoryModel provideModel(ApiService apiService){

        return  new CategoryModel(apiService);
    }

    @FragmentScope
    @Provides
    public CategoryContract.CategoryView provideView(){
        return  mView;
    }





}
