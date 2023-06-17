package com.roundlers.mytemplate.di.base.component;

import android.content.Context;

import com.roundlers.mytemplate.MyApplication;
import com.roundlers.mytemplate.base.BaseActivity;
import com.roundlers.mytemplate.base.BaseFragment;
import com.roundlers.mytemplate.di.base.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class
})
public interface ApplicationComponent {

    // list of places from where you have to initialize/build this component
    void inject(MyApplication myApplication);

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);


    //Expose dependency for sub graphs
    Context getContext();

//    @Named("json")
//    OkHttpClient getJsonClient();

}
