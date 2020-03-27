package com.roundlers.mytemplate.di.base.component;

import com.roundlers.mytemplate.base.BaseActivity;
import com.roundlers.mytemplate.base.BaseFragment;
import com.roundlers.mytemplate.di.base.module.ActivityModule;
import com.roundlers.mytemplate.di.base.module.UserModule;
import com.roundlers.mytemplate.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class,
                UserModule.class
        })
public interface UserComponent extends ActivityComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    //Activity

    //Fragments
}
