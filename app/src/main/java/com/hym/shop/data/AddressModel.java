package com.hym.shop.data;

import com.hym.shop.bean.Address;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.OrderRespMsg;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.AddressContract;
import com.hym.shop.presenter.contract.CreateOrderContract;

import java.util.List;

import io.reactivex.Observable;


public class AddressModel implements AddressContract.IAddressModel {


    private ApiService mApiService;

    public AddressModel(ApiService apiService){
        this.mApiService = apiService;
    }


    @Override
    public Observable<List<Address>> getAddressList(long userId, String token) {
        return mApiService.getAddrList(userId, token);
    }

    @Override
    public Observable<BaseBean> createAddress(long user_id, String consignee, String phone, String addr, String zip_code, String token) {
        return mApiService.addAddr(user_id,consignee,phone,addr,zip_code,token);
    }

    @Override
    public Observable<BaseBean> updateAddress(long id, String consignee, String phone, String addr, String zip_code, boolean is_default, String token) {
        return mApiService.updateAddr(id,consignee,phone,addr,zip_code,is_default,token);
    }

    @Override
    public Observable<BaseBean> delAddress(long id, String token) {
        return mApiService.delAddr(id,token);
    }
}
