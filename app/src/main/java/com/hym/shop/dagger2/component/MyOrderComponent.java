package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.module.MyOrderModule;
import com.hym.shop.dagger2.module.SortWaresModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.MyOrderFragment;
import com.hym.shop.ui.fragment.SortWaresFragment;

import dagger.Component;

@FragmentScope
@Component(modules = MyOrderModule.class,dependencies= AppComponent.class)
public interface MyOrderComponent {
    void inject(MyOrderFragment fragment);
}
