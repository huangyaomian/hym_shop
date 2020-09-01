package com.hym.shop.dagger2.component;

import com.hym.shop.dagger2.module.RecommendModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.RecommendFragment;

import dagger.Component;

@FragmentScope
@Component(modules = RecommendModule.class,dependencies = AppComponent.class)
public interface RecommendComponent {

    void inject(RecommendFragment fragment);

}
