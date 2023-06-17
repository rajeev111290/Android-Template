package com.roundlers.mytemplate.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.roundlers.mytemplate.MyApplication;
import com.roundlers.mytemplate.di.HasComponent;
import com.roundlers.mytemplate.di.base.component.ApplicationComponent;
import com.roundlers.mytemplate.di.base.component.DaggerUserComponent;
import com.roundlers.mytemplate.di.base.component.UserComponent;
import com.roundlers.mytemplate.di.base.module.ActivityModule;
import com.roundlers.mytemplate.helper.EventbusHelper;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseFragment extends Fragment implements HasComponent {
    private static final String TAG = BaseFragment.class.getSimpleName();
    protected View rootView;
    protected CompositeDisposable compositeDisposable;
    //    private boolean injected=false;
    private UserComponent userComponent;

    protected abstract void injectFragment(UserComponent userComponent);

    private void injectDependencies() {
        injectFragment(userComponent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventbusHelper.register(this);
        compositeDisposable = new CompositeDisposable();

        initializeInjector(this.getContext());
        injectDependencies();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        userComponent = null;
        EventbusHelper.unregister(this);
    }

//    @Subscribe
//    public void onEvent(NeverFiredEvent neverFiredEvent) {
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(NightMode nightMode) {
//        onResume();
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected ActivityModule getActivityModule(Context context) {
        return new ActivityModule(context);
    }

    protected ApplicationComponent getApplicationComponent() {
        return MyApplication.getInstance().getApplicationComponent();
    }

    private void initializeInjector(Context context) {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule(context))
                .build();
    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getRootView(inflater, container);
        getIntentData();
        setViews(rootView);
        setActionBar(rootView);
        return rootView;
    }

    protected abstract void getIntentData();

    protected abstract void setActionBar(View rootView);

    protected abstract void setViews(View rootView);

    protected abstract View getRootView(LayoutInflater inflater, ViewGroup container);

    public View getRootView() {
        return rootView;
    }
}