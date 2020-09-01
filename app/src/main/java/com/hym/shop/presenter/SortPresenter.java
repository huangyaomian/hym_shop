package com.hym.shop.presenter;

import com.hym.shop.bean.SortBean;
import com.hym.shop.common.rx.RxHttpResponseCompat;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.presenter.contract.SortContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;


public class SortPresenter extends BasePresenter<SortContract.ISortModel, SortContract.SortView> {


    @Inject
    public SortPresenter(SortContract.ISortModel mModel, SortContract.SortView mView) {
        super(mModel, mView);
    }



    public void getAllSort(){
        mModel.getSort().compose(RxHttpResponseCompat.compatResult())
                .subscribe(new ProgressDisposableObserver<List<SortBean>>(mContext,mView) {
                    @Override
                    public void onNext(@NonNull List<SortBean> sortBeans) {
                        mView.showResult(sortBeans);
                    }
                });
    }


}
