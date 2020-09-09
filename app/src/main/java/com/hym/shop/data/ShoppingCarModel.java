package com.hym.shop.data;

import com.hym.shop.bean.Banner;
import com.hym.shop.bean.Category;
import com.hym.shop.bean.HotWares;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.CategoryContract;
import com.hym.shop.presenter.contract.ShoppingCarContract;

import java.util.List;

import io.reactivex.Observable;


public class ShoppingCarModel implements ShoppingCarContract.IShoppingCarModel {


    @Override
    public Observable<List<HotWares.WaresBean>> getWares() {
        return null;
    }
}
