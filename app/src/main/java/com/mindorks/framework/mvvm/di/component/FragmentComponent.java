package com.mindorks.framework.mvvm.di.component;

import com.mindorks.framework.mvvm.di.module.FragmentModule;
import com.mindorks.framework.mvvm.di.scope.FragmentScope;

import dagger.Component;

/*
 * Author: rotbolt
 */

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
//    void inject(BlogFragment fragment);

//    void inject(OpenSourceFragment fragment);

//    void inject(AboutFragment fragment);
}
