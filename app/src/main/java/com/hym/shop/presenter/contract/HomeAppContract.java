package com.hym.shop.presenter.contract;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.HomeBean;
import com.hym.shop.ui.BaseView;

import io.reactivex.Observable;

public interface HomeAppContract {

    interface HomeAppView  extends BaseView {
        void showApps(HomeBean datas);

    }

    interface IHomeAppModel{
        /*       public Observable<BaseBean<HomeBean>> getHomeRequest(){
                   return  mApiService.getHome();
               }*/
        Observable<BaseBean<HomeBean>> getHomeApps();

    }


}
