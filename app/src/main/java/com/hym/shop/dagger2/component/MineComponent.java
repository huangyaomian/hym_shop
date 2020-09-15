package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.MineModule;
import com.hym.shop.dagger2.module.ShoppingCarModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.presenter.contract.MineContract;
import com.hym.shop.ui.fragment.MineFragment;
import com.hym.shop.ui.fragment.ShoppingCarFragment;

import dagger.Component;

@FragmentScope
@Component(modules = MineModule.class,dependencies= AppComponent.class)
public interface MineComponent {
    void inject(MineFragment fragment);
}
