package com.hym.shop.presenter;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.Category;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.OrderRespMsg;
import com.hym.shop.common.db.DBManager;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.presenter.contract.CreateOrderContract;
import com.hym.shop.presenter.contract.ShoppingCarContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CreateOrderPresenter extends BasePresenter<CreateOrderContract.ICreateOrderModel, CreateOrderContract.CreateOrderView> {


    @Inject
    public CreateOrderPresenter(CreateOrderContract.ICreateOrderModel iCreateOrderModel, CreateOrderContract.CreateOrderView createOrderView) {
        super(iCreateOrderModel, createOrderView);
    }



    public void getWares(boolean isShowProgress){

        Observable.create(new ObservableOnSubscribe<List<HotWares.WaresBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HotWares.WaresBean>> emitter) throws Exception {
                emitter.onNext(DBManager.getAllCheckedWares());
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<List<HotWares.WaresBean>>(mContext,mView) {
                    @Override
                    public void onNext(List<HotWares.WaresBean> waresBeanList) {
                        mView.showWares(waresBeanList);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }


                });

    }

    public void submitOrder(long userId, String itemJson, int amount, int addrId, String payChannel,String token){
        mModel.submitOrder( userId,  itemJson, amount, addrId,  payChannel,token)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<BaseBean<OrderRespMsg>>(mContext,mView) {
                    @Override
                    public void onNext(BaseBean<OrderRespMsg> orderRespMsgBaseBean) {
                        mView.showSubmitOrderResult(orderRespMsgBaseBean);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return false;
                    }
                });
    }

    public void completeOrder(String orderNum, String status, String token){
        mModel.completeOrder(orderNum, status, token)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<BaseBean>(mContext,mView) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.showCompleteOrderResult(baseBean);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return false;
                    }
                });
    }








}
