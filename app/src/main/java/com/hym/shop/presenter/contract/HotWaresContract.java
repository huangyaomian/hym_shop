package com.hym.shop.presenter.contract;

import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.bean.HotWares;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface HotWaresContract {

    interface  HotWaresView extends BaseView {

        void showHotWares(HotWares hotWares);
    }


    interface IHotWaresModel{

        Observable<HotWares> getHotWares(int curPage, int pageSize);

    }


}
