package com.hym.shop.presenter;

import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.presenter.contract.HomeCampaignContract;
import com.hym.shop.presenter.contract.HotWaresContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class HotWaresPresenter extends BasePresenter<HotWaresContract.IHotWaresModel, HotWaresContract.HotWaresView> {


    @Inject
    public HotWaresPresenter(HotWaresContract.IHotWaresModel iHotWaresModel, HotWaresContract.HotWaresView hotWaresView) {
        super(iHotWaresModel, hotWaresView);
    }



    public void getHotWares(boolean isShowProgress,int curPage,int pageSize){

        mModel.getHotWares(curPage,pageSize)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<HotWares>(mContext,mView) {
                    @Override
                    public void onNext(HotWares hotWares) {
                        mView.showHotWares(hotWares);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }
                });


    }






}
