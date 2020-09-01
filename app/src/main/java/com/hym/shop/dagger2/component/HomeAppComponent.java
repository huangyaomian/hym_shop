package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.HomeAppModule;
import com.hym.shop.dagger2.scope.ActivityScope;
import com.hym.shop.ui.activity.HomeAppActivity;

import dagger.Component;

@ActivityScope
@Component(modules = HomeAppModule.class,dependencies= AppComponent.class)
public interface HomeAppComponent {
    void inject(HomeAppActivity activity);
}
