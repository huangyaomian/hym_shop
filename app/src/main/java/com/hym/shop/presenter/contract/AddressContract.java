package com.hym.shop.presenter.contract;

import com.hym.shop.bean.Address;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.OrderRespMsg;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.Query;

public interface AddressContract {

    interface  AddressView extends BaseView {
        void showAddressList(List<Address> addressList);
        void addAddress(BaseBean baseBean);
        void updateAddress(BaseBean baseBean);
        void delAddress(BaseBean baseBean);
    }


    interface IAddressModel{
        Observable<List<Address>> getAddressList(long userId, String token);
        Observable<BaseBean> createAddress(long user_id, String consignee,
                                           String phone, String addr,
                                           String zip_code, String token);
        Observable<BaseBean> updateAddress(long id, String consignee,
                                        String phone, String addr,
                                         String zip_code, boolean is_default,
                                         String token);

        Observable<BaseBean> delAddress(long id, String token);
    }


}
