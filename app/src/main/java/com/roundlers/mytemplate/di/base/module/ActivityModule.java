package com.roundlers.mytemplate.di.base.module;

import android.app.Activity;
import android.content.Context;

import com.roundlers.mytemplate.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Context context;

    public ActivityModule(Context activity) {
        this.context = activity;
    }

    @Provides
    @ActivityScope
    Activity getActivity() {
        return (Activity) context;
    }

}
