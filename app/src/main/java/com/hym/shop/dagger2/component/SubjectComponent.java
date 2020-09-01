package com.hym.shop.dagger2.component;


import com.hym.shop.dagger2.module.SubjectModule;
import com.hym.shop.dagger2.scope.FragmentScope;
import com.hym.shop.ui.fragment.BaseSubjectFragment;

import dagger.Component;

@FragmentScope
@Component(modules = SubjectModule.class,dependencies = AppComponent.class)
public interface SubjectComponent {
    void inject(BaseSubjectFragment fragment);
}
