package com.hym.shop.ui.fragment;

import com.hym.shop.bean.PageBean;
import com.hym.shop.bean.Subject;
import com.hym.shop.bean.SubjectDetail;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerSubjectComponent;
import com.hym.shop.dagger2.module.SubjectModule;
import com.hym.shop.presenter.SubjectPresenter;
import com.hym.shop.presenter.contract.SubjectContract;

public abstract class BaseSubjectFragment extends ProgressFragment<SubjectPresenter> implements SubjectContract.SubjectView {


    @Override
    public void showSubject(PageBean<Subject> pageBean) {

    }

    @Override
    public void onLoadMoreComplete() {

    }

    @Override
    public void showSubjectDetail(SubjectDetail detail) {

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubjectComponent.builder().appComponent(appComponent).subjectModule(new SubjectModule(this)).build().inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }
}
