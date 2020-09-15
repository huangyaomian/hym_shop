package com.hym.shop.data;

import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.User;
import com.hym.shop.presenter.contract.MineContract;
import com.hym.shop.presenter.contract.ShoppingCarContract;

import java.util.List;

import io.reactivex.Observable;


public class MineModel implements MineContract.IMineModel {



    @Override
    public Observable<User> getUser() {
        return null;
    }
}
