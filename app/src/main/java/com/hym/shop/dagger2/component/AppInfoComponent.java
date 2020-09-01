package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.AppInfoModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.GameFragment;
import com.hym.shop.ui.fragment.RankingFragment;
import com.hym.shop.ui.fragment.SortAppFragment;

import dagger.Component;

@FragmentScope
@Component(modules = AppInfoModule.class,dependencies = AppComponent.class)
public interface AppInfoComponent {
    void injectRankingFragment(RankingFragment fragment);
    void injectGameFragment(GameFragment fragment);
    void injectSortAppFragment(SortAppFragment fragment);
}
