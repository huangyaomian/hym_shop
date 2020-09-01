package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.AppDetailModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.activity.AppDetailsActivity3;

import dagger.Component;

@FragmentScope
@Component(modules = AppDetailModule.class,dependencies = AppComponent.class)
public interface AppDetailComponent {
    void injectActivity(AppDetailsActivity3 activity);
}
