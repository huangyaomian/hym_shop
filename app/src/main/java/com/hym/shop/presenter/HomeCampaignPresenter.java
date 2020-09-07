package com.hym.shop.presenter;

import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.bean.SearchResult;
import com.hym.shop.common.db.DBManager;
import com.hym.shop.common.rx.Optional;
import com.hym.shop.common.rx.RxHttpResponseCompat;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.presenter.contract.HomeCampaignContract;
import com.hym.shop.presenter.contract.SearchContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomeCampaignPresenter extends BasePresenter<HomeCampaignContract.IHomeCampaignModel, HomeCampaignContract.HomeCampaignView> {


    @Inject
    public HomeCampaignPresenter(HomeCampaignContract.IHomeCampaignModel iHomeCampaignModel, HomeCampaignContract.HomeCampaignView homeCampaignView) {
        super(iHomeCampaignModel, homeCampaignView);
    }



    public void getHomeRecommend(boolean isShowProgress){

        mModel.getHomeRecommend()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<List<HomeCampaign>>(mContext,mView) {
                    @Override
                    public void onNext(List<HomeCampaign> homeCampaigns) {
                        mView.showHomeRecommend(homeCampaigns);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }
                });
////                .compose(RxHttpResponseCompat.handle_result())
//                .subscribe(new ProgressDisposableObserver<Optional<List<HomeCampaign>>>(mContext,mView) {
//                    @Override
//                    public void onNext(@NonNull Optional<List<HomeCampaign>> searchResultOptional) {
//                        mView.showHomeRecommend(searchResultOptional.getIncludeNull());
//
//                    }
//
//                    @Override
//                    protected boolean isShowProgress() {
//                        return isShowProgress;
//                    }
//                });

    }






}
