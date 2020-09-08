package com.hym.shop.presenter;

import com.hym.shop.bean.Banner;
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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
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


    }

    public void getHomeRecommendAndBanner(boolean isShowProgress){

        Observable<List<HomeCampaign>> homeRecommend = mModel.getHomeRecommend();
        Observable<List<Banner>> banner = mModel.getBanner(1);

        Observable.zip(homeRecommend, banner, new BiFunction<List<HomeCampaign>, List<Banner>, List<HomeCampaign>>() {
            @Override
            public List<HomeCampaign> apply(List<HomeCampaign> homeCampaigns, List<Banner> banners) throws Exception {
                homeCampaigns.get(0).setBanners(banners);
                return homeCampaigns;
            }
        })
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


    }






}
