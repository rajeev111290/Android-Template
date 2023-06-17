package com.roundlers.mytemplate.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.roundlers.mytemplate.MyApplication;
import com.roundlers.mytemplate.R;
import com.roundlers.mytemplate.di.base.component.ApplicationComponent;
import com.roundlers.mytemplate.di.base.component.DaggerUserComponent;
import com.roundlers.mytemplate.di.base.component.UserComponent;
import com.roundlers.mytemplate.di.base.module.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    public Activity activity;
    private UserComponent userComponent;
//  protected CompositeDisposable compositeDisposable;

    protected abstract void setViews();

    protected abstract void setActionBar();

    protected abstract void injectActivity(UserComponent userComponent);

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void setTheme() {

//        if (SharedPreferencesHelper.getNightModeStatus()) {
//            setTheme(R.style.AppThemeNightMode);
//        } else {
//            setTheme(R.style.AppTheme);
//        }
//        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.color_e5e5e5));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.activity = this;
        this.overridePendingTransition(0, 0);

        setTheme();

//        EventbusHelper.register(this);

//        AppHelper.LANGUAGE_PREFERENCE = SharedPreferencesHelper.getLanguageStatus();
//        AppHelper.languageChangeHandler(BaseActivity.this, false, true, false, false, SharedPreferencesHelper.getLanguageStatus());

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

//        compositeDisposable = new CompositeDisposable();

        setStatusBarColor(this);
        setViews();
        setActionBar();
    }


//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(INSTANCE_ID_KEY, instanceId);
//    }
//
//    public String getInstanceId() {
//        return instanceId;
//    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        try {
            this.overridePendingTransition(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.overridePendingTransition(0, 0);
        userComponent = null;

//        SocketHelper.close();
//        EventbusHelper.unregister(this);
//        compositeDisposable.dispose();

//        if(isFinishing()){
//            Injector.clearComponent(this);
//        }
    }

//    @Subscribe
//    public void onEvent(NeverFiredEvent neverFiredEvent) {
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (baseNotificationDeeplinkActivityRunning && !(this instanceof NotificationDeeplinkActivity)) {
//            EventbusHelper.post(new KillLauncherActivity());
//        }

//        if (!this.languageChanged) {
//            AppHelper.setNightModeChanges(this);
//        }
    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(NightMode nightMode) {
//        //isBaseActivityRunning = true;
//        onResume();
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(LanguageChange languageChange) {
//        this.languageChanged = languageChange.isLanguageChanged();
//        onResume();
//    }

    private void setStatusBarColor(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(activity.getResources().getColor(R.color.color_262626_nochange));
            }
        } catch (RuntimeException ignore) {
        }
    }

//    public static int getActivityNumber() {
//        return numberOfActivity;
//    }


    private void injectDependencies() {

        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        injectActivity(this.userComponent);

    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }


}
