package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.HomeModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.HomeFragment;

import dagger.Component;

@FragmentScope
@Component(modules = HomeModule.class,dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);
}
