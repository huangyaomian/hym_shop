package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.CategoryModule;
import com.hym.shop.dagger2.module.ShoppingCarModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.CategoryFragment;
import com.hym.shop.ui.fragment.ShoppingCarFragment;

import dagger.Component;

@FragmentScope
@Component(modules = ShoppingCarModule.class,dependencies= AppComponent.class)
public interface ShoppingCarComponent {
    void inject(ShoppingCarFragment fragment);
}
