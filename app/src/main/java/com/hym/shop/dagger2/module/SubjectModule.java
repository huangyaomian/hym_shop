package com.hym.shop.dagger2.module;

import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.data.SubjectModel;
import com.hym.shop.data.okhttp.ApiService;
import com.hym.shop.presenter.contract.SubjectContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SubjectModule {

    private SubjectContract.SubjectView mView;

    public SubjectModule(SubjectContract.SubjectView view) {
        this.mView = view;
    }


    @FragmentScope
    @Provides
    public SubjectContract.ISubjectModel provideModel(ApiService apiService) {

        return new SubjectModel(apiService);
    }

    @FragmentScope
    @Provides
    public SubjectContract.SubjectView provideView() {
        return mView;
    }


}
