package com.hym.shop.data;

import com.hym.shop.bean.Category;
import com.hym.shop.bean.HotWares;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.CategoryContract;
import com.hym.shop.presenter.contract.HotWaresContract;

import java.util.List;

import io.reactivex.Observable;


public class CategoryModel implements CategoryContract.ICategoryModel {


    private ApiService mApiService;


    public CategoryModel(ApiService apiService){

        this.mApiService = apiService;
    }



    @Override
    public Observable<List<Category>> getCategory() {
        return  mApiService.getCategoryList();
    }

    @Override
    public Observable<HotWares> getCategoryWares(int curPage, int pageSize, int categoryId) {
        return mApiService.getWaresList(curPage,pageSize,categoryId);
    }
}
