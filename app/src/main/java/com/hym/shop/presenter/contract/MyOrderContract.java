package com.hym.shop.presenter.contract;

import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.Order;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface MyOrderContract {

    interface  MyOrderView extends BaseView {

        void showMyOrder(List<Order> orders);
    }


    interface IMyOrderModel{

        Observable<List<Order>> getOrders(long user_id, int status, String token);

    }


}
