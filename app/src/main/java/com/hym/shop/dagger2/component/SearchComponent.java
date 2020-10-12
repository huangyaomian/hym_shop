package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.SearchModule;
import com.hym.shop.dagger2.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = SearchModule.class,dependencies= AppComponent.class)
public interface SearchComponent {
//    void inject(SearchActivity activity);
}
