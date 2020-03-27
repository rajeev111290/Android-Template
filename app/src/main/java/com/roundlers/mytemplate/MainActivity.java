package com.roundlers.mytemplate;

import com.roundlers.mytemplate.base.BaseActivity;
import com.roundlers.mytemplate.di.base.component.UserComponent;

public class MainActivity extends BaseActivity {

    @Override
    protected void setViews() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setActionBar() {

    }

    @Override
    protected void injectActivity(UserComponent userComponent) {
        userComponent.inject(this);
    }

}
