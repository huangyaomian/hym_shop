package com.hym.shop.presenter;

import android.util.Log;

import com.hym.shop.bean.HomeBean;
import com.hym.shop.common.rx.Optional;
import com.hym.shop.common.rx.RxHttpResponseCompat;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.data.AppInfoModel;
import com.hym.shop.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;


public class HomePresenter extends BasePresenter<AppInfoModel, AppInfoContract.View> {

    @Inject
    public HomePresenter(AppInfoContract.View mView, AppInfoModel model) {
        super(model, mView);
    }



    public void requestHomeData(boolean isShowProgress) {
        mModel.getHomeRequest()
                .compose(RxHttpResponseCompat.handle_result())
                .subscribe(new ProgressDisposableObserver<Optional<HomeBean>>(mContext, mView) {
                    @Override
                    public void onNext(@NonNull Optional<HomeBean> homeBeanOptional) {
                        Log.d("requestHomeData", String.valueOf(homeBeanOptional.getIncludeNull().getBanners().size()));
                        mView.showResult(homeBeanOptional.getIncludeNull());

                    }
                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }
                });




    }
}

