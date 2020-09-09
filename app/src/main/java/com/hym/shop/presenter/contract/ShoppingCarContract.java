package com.hym.shop.presenter.contract;

import com.hym.shop.bean.Banner;
import com.hym.shop.bean.Category;
import com.hym.shop.bean.HotWares;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface ShoppingCarContract {

    interface  ShoppingCarView extends BaseView {
        void showShoppingCar(List<HotWares.WaresBean> waresBeanList);
    }


    interface IShoppingCarModel{
        Observable<List<HotWares.WaresBean>> getWares();

    }


}
