package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.LoginModule;
import com.hym.shop.dagger2.scope.ActivityScope;
import com.hym.shop.ui.activity.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}
