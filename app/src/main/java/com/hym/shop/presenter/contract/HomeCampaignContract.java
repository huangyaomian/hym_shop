package com.hym.shop.presenter.contract;

import com.hym.shop.bean.Banner;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.Campaign;
import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.bean.SearchResult;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface HomeCampaignContract {

    interface  HomeCampaignView extends BaseView {

        void showHomeRecommend(List<HomeCampaign> list);
    }


    interface IHomeCampaignModel{

        Observable<List<HomeCampaign>> getHomeRecommend();

        Observable<List<Banner>> getBanner(int type);

    }


}
