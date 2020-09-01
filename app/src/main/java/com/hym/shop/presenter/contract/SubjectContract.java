package com.hym.shop.presenter.contract;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.PageBean;
import com.hym.shop.bean.Subject;
import com.hym.shop.bean.SubjectDetail;
import com.hym.shop.ui.BaseView;

import io.reactivex.Observable;

public interface SubjectContract {


    interface SubjectView extends BaseView {
        void showSubject(PageBean<Subject> pageBean);
//        void showError(String msg);
        void onLoadMoreComplete();

        void showSubjectDetail(SubjectDetail detail);
    }

    interface ISubjectModel{
        Observable<BaseBean<PageBean<Subject>>> getSubjects(int page);
        Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id);
    }
}
