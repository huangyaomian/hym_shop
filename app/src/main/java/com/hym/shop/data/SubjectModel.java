package com.hym.shop.data;


import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.PageBean;
import com.hym.shop.bean.Subject;
import com.hym.shop.bean.SubjectDetail;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.SubjectContract;

import io.reactivex.Observable;

public class SubjectModel implements SubjectContract.ISubjectModel {
    private ApiService mApiService;

    public SubjectModel(ApiService apiService) {
        this.mApiService = apiService;

    }


    @Override
    public Observable<BaseBean<PageBean<Subject>>> getSubjects(int page) {
        return mApiService.subjects(page);
    }

    @Override
    public Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id) {
        return mApiService.subjectDetail(id);
    }

}
