package com.hym.shop.presenter;

import com.hym.shop.bean.Address;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.Order;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.presenter.contract.MyOrderContract;
import com.hym.shop.presenter.contract.SortWaresContract;
import com.mob.tools.RxMob;

import java.lang.invoke.CallSite;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MyOrderPresenter extends BasePresenter<MyOrderContract.IMyOrderModel, MyOrderContract.MyOrderView> {


    @Inject
    public MyOrderPresenter(MyOrderContract.IMyOrderModel iMyOrderModel, MyOrderContract.MyOrderView myOrderView) {
        super(iMyOrderModel, myOrderView);
    }


    public void getMyOrder(long user_id, int status, String token,boolean isShowProgress){

        addDisposable(mModel.getOrders(user_id,status,token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ProgressDisposableObserver<List<Order>>(mContext, mView) {
                            @Override
                            public void onNext(List<Order> orders) {
                                mView.showMyOrder(orders);
                            }
                            @Override
                            protected boolean isShowProgress() {
                                return isShowProgress;
                            }
                        }
                ));

//        mModel.getOrders(user_id,status,token)
//                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
//                .subscribe(new ProgressDisposableObserver<List<Order>>(mContext,mView) {
//                    @Override
//                    public void onNext(List<Order> orders) {
//                        mView.showMyOrder(orders);
//                    }
//                    @Override
//                    protected boolean isShowProgress() {
//                        return isShowProgress;
//                    }
//                });

    }






}
