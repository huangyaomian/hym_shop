package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.MainModule;
import com.hym.shop.dagger2.scope.ActivityScope;
import com.hym.shop.ui.activity.MainActivity;

import dagger.Component;

//@ActivityScope
//@Component(modules = MainModule.class,dependencies= AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
