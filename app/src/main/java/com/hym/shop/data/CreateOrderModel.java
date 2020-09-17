package com.hym.shop.data;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.OrderRespMsg;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.CreateOrderContract;
import com.hym.shop.presenter.contract.ShoppingCarContract;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;


public class CreateOrderModel implements CreateOrderContract.ICreateOrderModel {


    private ApiService mApiService;

    public CreateOrderModel(ApiService apiService){
        this.mApiService = apiService;
    }

    @Override
    public Observable<List<HotWares.WaresBean>> getWares() {
        return null;
    }


    @Override
    public Observable<BaseBean<OrderRespMsg>> submitOrder(long userId, String itemJson, int amount, int addrId, String payChannel,String token) {
        return  mApiService.submitOrder( userId,   itemJson,   amount,   addrId,  payChannel, token);
    }

    @Override
    public Observable<BaseBean<OrderRespMsg>> completeOrder(String orderNum, String status, String token) {
        return  mApiService.completeOrder( orderNum, status, token);
    }
}
