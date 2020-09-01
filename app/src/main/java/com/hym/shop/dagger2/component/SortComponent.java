package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.SortModule;
import com.hym.shop.dagger2.scope.ActivityScope;
import com.hym.shop.ui.fragment.SortFragment;

import dagger.Component;

@ActivityScope
@Component(modules = SortModule.class,dependencies = AppComponent.class)
public interface SortComponent {
    void inject(SortFragment fragment);
}
