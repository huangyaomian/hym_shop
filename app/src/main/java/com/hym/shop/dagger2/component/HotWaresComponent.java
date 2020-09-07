package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.HomeCampaignModule;
import com.hym.shop.dagger2.module.HotWaresModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.HomeCampaignFragment;
import com.hym.shop.ui.fragment.HotWaresFragment;

import dagger.Component;

@FragmentScope
@Component(modules = HotWaresModule.class,dependencies= AppComponent.class)
public interface HotWaresComponent {
    void inject(HotWaresFragment fragment);
}
