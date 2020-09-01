package com.hym.shop.presenter.contract;

import com.hym.shop.bean.AppInfoBean;
import com.hym.shop.bean.RecommendBean2;
import com.hym.shop.ui.BaseView;

import java.util.List;

public interface RecommendContract {

    interface View extends BaseView{

        void showResult(List<AppInfoBean> datas);
        void showMoreResult(RecommendBean2 datas);
        void showNoData();
//        void showError(String msg);

        void onRequestPermissionSuccess();
        void onRequestPermissionError();

    }

/*    interface  Presenter extends BasePresenter{

        public void requestRecommendData(String URL);

    }*/
}
