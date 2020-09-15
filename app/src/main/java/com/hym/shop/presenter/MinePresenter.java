package com.hym.shop.presenter;

import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.rx.Optional;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.common.utils.ACache;
import com.hym.shop.presenter.contract.MineContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MinePresenter extends BasePresenter<MineContract.IMineModel, MineContract.MineView> {


    @Inject
    public MinePresenter(MineContract.IMineModel iMineModel, MineContract.MineView mineView) {
        super(iMineModel, mineView);
    }



    public void getUser(boolean isShowProgress){

        Observable.create(new ObservableOnSubscribe<Optional<User>>() {
            @Override
            public void subscribe(ObservableEmitter<Optional<User>> emitter) throws Exception {
                emitter.onNext(initUser());
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<Optional<User>>(mContext,mView) {
                    @Override
                    public void onNext(Optional<User> userOptional) {
                        mView.showMine(userOptional.getIncludeNull());
                    }

                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }

                });



    }

    private Optional<User> initUser() {
        User user = (User) ACache.get(mContext).getAsObject(Constant.USER);
//        User user = new User();
//        user.setUsername("ggggg");
//        user.setLogo_url("https://lh3.googleusercontent.com/IZHUCtBPOPH4-7EkyVwcyRh-UyxhqwmjzguRfOx6mQK0HqRw1zLKLQYdPY4p-pr-KNc_=w300");
        return new Optional<User>(user);
    }








}
