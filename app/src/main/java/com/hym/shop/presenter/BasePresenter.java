package com.hym.shop.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.ui.BaseView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<M,V extends BaseView>  {

    private CompositeDisposable compositeDisposable;
    protected M mModel;
    protected V mView;

    protected Context mContext;

    public BasePresenter(M mModel, V mView) {
        this.mModel = mModel;
        this.mView = mView;
        initContext();
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        mView = null;
        removeDisposable();
    }
    /**
     * 返回 view
     *
     * @return
     */
    public V getBaseView() {
        return mView;
    }

    private void initContext(){
        if (mView instanceof Fragment) {
            mContext = ((Fragment) mView).getActivity();
        }else {
            mContext = (Activity) mView;
        }
    }

    /**
     * 將Disposable添加到compositeDisposable中，方便後續取消訂閲
     * @param observer
     */
    public void addDisposable(Observer observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
//        observable.subscribe(observer);
        compositeDisposable.add(((ProgressDisposableObserver)observer).getDisposable());

    }

    /**
     * 取消所有訂閲
     */
    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            Log.d("hymmmm", "removeDisposable: " + "取消訂閲");
        }
    }

}
