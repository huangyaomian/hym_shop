package com.hym.shop.ui.fragment;

import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerAppInfoComponent;
import com.hym.shop.dagger2.module.AppInfoModule;
import com.hym.shop.presenter.AppInfoPresenter;
import com.hym.shop.ui.adapter.AppInfoAdapter;



public class RankingFragment extends AppInfoFragment {


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent).appInfoModule(new AppInfoModule(this)).build().injectRankingFragment(this);
    }



    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showPosition(true).showCategoryName(true).showBrief(false).build();
    }

    @Override
    int setType() {
        return AppInfoPresenter.RANKING_LIST;
    }


}
