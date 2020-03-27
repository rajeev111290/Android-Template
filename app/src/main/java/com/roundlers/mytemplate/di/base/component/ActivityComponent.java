package com.roundlers.mytemplate.di.base.component;

import android.app.Activity;

import com.roundlers.mytemplate.di.base.module.ActivityModule;
import com.roundlers.mytemplate.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    //Exposed to sub-graphs.
    Activity activity();
}
