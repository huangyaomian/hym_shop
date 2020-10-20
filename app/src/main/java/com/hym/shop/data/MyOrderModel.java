package com.hym.shop.data;

import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.Order;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.MyOrderContract;
import com.hym.shop.presenter.contract.SortWaresContract;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;


public class MyOrderModel implements MyOrderContract.IMyOrderModel {


    private ApiService mApiService;


    public MyOrderModel(ApiService apiService){

        this.mApiService = apiService;
    }



    @Override
    public Observable<List<Order>> getOrders(long user_id, int status, String token) {
        return mApiService.orderList(user_id,status,token);
    }


}
