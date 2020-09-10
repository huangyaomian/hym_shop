package com.hym.shop.presenter;

import com.hym.shop.bean.HotWares;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.presenter.contract.HotWaresContract;
import com.hym.shop.presenter.contract.SortWaresContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SortWaresPresenter extends BasePresenter<SortWaresContract.ISortWaresModel, SortWaresContract.SortWaresView> {


    @Inject
    public SortWaresPresenter(SortWaresContract.ISortWaresModel iSortWaresModel, SortWaresContract.SortWaresView sortWaresView) {
        super(iSortWaresModel, sortWaresView);
    }



    public void getSortWares(boolean isShowProgress,int curPage,  int pageSize,  int campaignId, int orderBy ){

        mModel.getSortWares(curPage,pageSize,campaignId,orderBy)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<HotWares>(mContext,mView) {
                    @Override
                    public void onNext(HotWares hotWares) {
                        mView.showSortWares(hotWares);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }
                });


    }






}
