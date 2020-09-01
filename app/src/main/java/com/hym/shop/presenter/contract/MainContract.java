package com.hym.shop.presenter.contract;

import com.hym.shop.bean.AppInfoBean;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.requestbean.AppsUpdateBean;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface MainContract {

    interface MainView  extends BaseView {

        void requestPermissionSuccess();
        void requestPermissionFail();

        void changeAppNeedUpdateCount(int count);


    }

    interface IMainModel{

        Observable<BaseBean<List<AppInfoBean>>> getUpdateApps(AppsUpdateBean param);

    }


}
