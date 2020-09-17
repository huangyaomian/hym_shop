package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.CreateOrderModule;
import com.hym.shop.dagger2.module.ShoppingCarModule;
import com.hym.shop.dagger2.scope.ActivityScope;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.activity.CreateOrderActivity;
import com.hym.shop.ui.fragment.ShoppingCarFragment;

import dagger.Component;

@ActivityScope
@Component(modules = CreateOrderModule.class, dependencies = AppComponent.class)
public interface CreateOrderComponent {
    void inject(CreateOrderActivity activity);
}
