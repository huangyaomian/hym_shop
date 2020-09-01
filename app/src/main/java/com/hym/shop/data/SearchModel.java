package com.hym.shop.data;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.SearchResult;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.SearchContract;

import java.util.List;

import io.reactivex.Observable;


public class SearchModel implements SearchContract.ISearchModel {


    private ApiService mApiService;


    public SearchModel(ApiService apiService){

        this.mApiService = apiService;
    }

    public  Observable<BaseBean<List<String>>> getSuggestion(String keyword){


        return  mApiService.searchSuggest(keyword);

    }

    @Override
    public Observable<BaseBean<SearchResult>> search(String keyword) {
        return  mApiService.search(keyword);
    }
}
