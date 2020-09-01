package com.hym.shop.presenter.contract;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.SearchResult;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface SearchContract {

    interface  SearchView extends BaseView {

        void showSearchHistory(List<String> list);
        void showSuggestions(List<String> list);
        void showSearchResult(SearchResult result);

    }


    interface ISearchModel{

        Observable<BaseBean<List<String>>> getSuggestion(String keyword);

        Observable<BaseBean<SearchResult>> search(String keyword);

    }


}
