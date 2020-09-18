package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.AddressModule;
import com.hym.shop.dagger2.scope.ActivityScope;
import com.hym.shop.ui.activity.CreateAddressActivity;
import com.hym.shop.ui.activity.AddressListActivity;

import dagger.Component;

@ActivityScope
@Component(modules = AddressModule.class, dependencies = AppComponent.class)
public interface AddressComponent {
    void injectList(AddressListActivity activity);
    void injectAdd(CreateAddressActivity activity);
}
