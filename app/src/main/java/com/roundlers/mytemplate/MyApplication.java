package com.roundlers.mytemplate;

import android.app.Application;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;

import com.roundlers.mytemplate.di.base.component.ApplicationComponent;
import com.roundlers.mytemplate.di.base.component.DaggerApplicationComponent;
import com.roundlers.mytemplate.di.base.module.ApplicationModule;

public class MyApplication extends Application {
    ApplicationComponent component;
    private static MyApplication appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance=this;
        intializeDagger();

    }

    public void intializeDagger() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }

    public static MyApplication getInstance() {
        return appInstance;
    }



    private Handler mHandler;
    public static Typeface nunitoSans, nunitoSansBold, nunitoSansSemiBold, poppinsBold;


    private void fetchFontsTypeFace(String font) {
        FontRequest request = new FontRequest("com.google.android.gms.fonts",
                "com.google.android.gms", font, R.array.com_google_android_gms_fonts_certs);
        FontsContractCompat.FontRequestCallback callback =
                new FontsContractCompat.FontRequestCallback() {
                    @Override
                    public void onTypefaceRetrieved(Typeface typeface) {
                        switch (font) {
                            case "Nunito Sans":
                                nunitoSans = typeface;
                                break;
                            case "name=Nunito Sans&weight=700":
                                nunitoSansBold = typeface;
                                break;
                            case "name=Nunito Sans&weight=600":
                                nunitoSansSemiBold = typeface;
                                break;
                            case "name=Poppins&weight=700":
                                poppinsBold = typeface;
//                                setActionBar();
                                break;
                        }
                    }

                    @Override
                    public void onTypefaceRequestFailed(int reason) {
                    }
                };
        FontsContractCompat.requestFont(this, request, callback, getHandlerThreadHandler());
    }


    private Handler getHandlerThreadHandler() {
        if (mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("fonts");
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper());
        }
        return mHandler;
    }

}
