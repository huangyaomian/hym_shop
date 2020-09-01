package com.hym.shop.presenter.contract;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.SortBean;
import com.hym.shop.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface SortContract {

    interface SortView extends BaseView {

        void showResult(List<SortBean> datas);
//        void showError(String msg);

    }

    interface ISortModel{
        Observable<BaseBean<List<SortBean>>> getSort();
    }


}
