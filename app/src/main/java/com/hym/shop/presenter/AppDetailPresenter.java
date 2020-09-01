package com.hym.shop.presenter;

import com.hym.shop.bean.AppInfoBean;
import com.hym.shop.common.rx.RxHttpResponseCompat;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.data.AppInfoModel;
import com.hym.shop.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;


public class AppDetailPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppDetailView> {


    @Inject
    public AppDetailPresenter(AppInfoContract.AppDetailView mView, AppInfoModel model) {
        super(model, mView);
    }


    public void getAppDetail(int id){
        mModel.getAppDetail(id)
                .compose(RxHttpResponseCompat.compatResult())
                .subscribe(new ProgressDisposableObserver<AppInfoBean>(mContext,mView) {
                    @Override
                    public void onNext(@NonNull AppInfoBean appInfoBean) {
                        mView.showAppDetail(appInfoBean);
                    }
                });


    }



}
