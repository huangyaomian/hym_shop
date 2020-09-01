package com.hym.shop.data;


import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.SortBean;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.SortContract;

import java.util.List;

import io.reactivex.Observable;


public class SortModel implements SortContract.ISortModel {
    private ApiService mApiService;

    public SortModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }


    @Override
    public Observable<BaseBean<List<SortBean>>> getSort() {
        return mApiService.getCategories();
    }
}
