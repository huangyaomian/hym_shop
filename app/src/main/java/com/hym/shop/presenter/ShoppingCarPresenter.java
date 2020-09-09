package com.hym.shop.presenter;

import android.util.Log;

import com.hym.shop.bean.Banner;
import com.hym.shop.bean.Category;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.db.DBManager;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.presenter.contract.CategoryContract;
import com.hym.shop.presenter.contract.ShoppingCarContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


public class ShoppingCarPresenter extends BasePresenter<ShoppingCarContract.IShoppingCarModel, ShoppingCarContract.ShoppingCarView> {


    @Inject
    public ShoppingCarPresenter(ShoppingCarContract.IShoppingCarModel iShoppingCarModel, ShoppingCarContract.ShoppingCarView shoppingCarView) {
        super(iShoppingCarModel, shoppingCarView);
    }



    public void getShoppingCarWares(boolean isShowProgress){

        Observable.create(new ObservableOnSubscribe<List<HotWares.WaresBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HotWares.WaresBean>> emitter) throws Exception {
                emitter.onNext(DBManager.getAllWaresList());
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<List<HotWares.WaresBean>>(mContext,mView) {
                    @Override
                    public void onNext(List<HotWares.WaresBean> waresBeanList) {
                        mView.showShoppingCar(waresBeanList);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }


                });
//        mModel.getCategoryWares()
//                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
//                .subscribe(new ProgressDisposableObserver<List<HotWares.WaresBean>>(mContext,mView) {
//                    @Override
//                    public void onNext(List<HotWares.WaresBean> waresBeanList) {
//                        mView.showShoppingCar(waresBeanList);
//                    }
//
//
//
//
//                });
    }








}
