package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.HomeCampaignModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.HomeCampaignFragment;

import dagger.Component;

@FragmentScope
@Component(modules = HomeCampaignModule.class,dependencies= AppComponent.class)
public interface HomeCampaignComponent {
    void inject(HomeCampaignFragment fragment);
}
