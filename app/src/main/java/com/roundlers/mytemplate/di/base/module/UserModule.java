package com.roundlers.mytemplate.di.base.module;

import java.util.HashSet;

import dagger.Module;

@Module
public class UserModule {
    private static HashSet<String> set = new HashSet<String>();

    public UserModule() {
    }

//    @Provides
//    @ActivityScope
//    FeedAPIService getFeedAPIClient(Retrofit retrofit) {
//        set.add(retrofit.toString());
//        return retrofit.create(FeedAPIService.class);
//    }

//    @Provides
//    @ActivityScope
//    ArrayList<Exam> getExamList(Context context) {
//        return SharedPreferencesHelper.getDeepCopyOfGTMExams();
//    }

}
