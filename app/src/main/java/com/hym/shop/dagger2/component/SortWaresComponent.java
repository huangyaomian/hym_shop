package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.SortWaresModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.SortWaresFragment;

import dagger.Component;

@FragmentScope
@Component(modules = SortWaresModule.class,dependencies= AppComponent.class)
public interface SortWaresComponent {
    void inject(SortWaresFragment fragment);
}
