package com.hym.shop.presenter.contract;

import com.hym.shop.bean.HotWares;
import com.hym.shop.ui.BaseView;

import io.reactivex.Observable;
import retrofit2.http.Query;

public interface SortWaresContract {

    interface  SortWaresView extends BaseView {

        void showSortWares(HotWares hotWares);
    }


    interface ISortWaresModel{

        Observable<HotWares> getSortWares(int curPage,  int pageSize,  int campaignId, int orderBy );

    }


}
