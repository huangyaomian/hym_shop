package com.hym.shop.presenter.contract;

import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.User;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface MineContract {

    interface  MineView extends BaseView {
        void showMine(User user);
    }


    interface IMineModel{
        Observable<User> getUser();
    }


}
