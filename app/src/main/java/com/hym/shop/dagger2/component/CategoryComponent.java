package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.CategoryModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.CategoryFragment;

import dagger.Component;

@FragmentScope
@Component(modules = CategoryModule.class,dependencies= AppComponent.class)
public interface CategoryComponent {
    void inject(CategoryFragment fragment);
}
