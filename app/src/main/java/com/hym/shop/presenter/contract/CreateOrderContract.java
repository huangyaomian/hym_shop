package com.hym.shop.presenter.contract;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.OrderRespMsg;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;

public interface CreateOrderContract {

    interface  CreateOrderView extends BaseView {
        void showWares(List<HotWares.WaresBean> waresBeanList);
        void showSubmitOrderResult(BaseBean<OrderRespMsg> orderRespMsgBaseBean);
        void showCompleteOrderResult(BaseBean<OrderRespMsg> orderRespMsgBaseBean);
    }


    interface ICreateOrderModel{
        Observable<List<HotWares.WaresBean>> getWares();
        Observable<BaseBean<OrderRespMsg>> submitOrder(long userId, String itemJson, int amount, int addrId, String payChannel, String token);
        Observable<BaseBean<OrderRespMsg>> completeOrder(String orderNum, String status, String token);
    }


}
