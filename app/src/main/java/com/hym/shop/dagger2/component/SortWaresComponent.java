package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.HotWaresModule;
import com.hym.shop.dagger2.module.SortWaresModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.DefaultSortWaresFragment;
import com.hym.shop.ui.fragment.HotWaresFragment;

import dagger.Component;

@FragmentScope
@Component(modules = SortWaresModule.class,dependencies= AppComponent.class)
public interface SortWaresComponent {
    void inject(DefaultSortWaresFragment fragment);
}
