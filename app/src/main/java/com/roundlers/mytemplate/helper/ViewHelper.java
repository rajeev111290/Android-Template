package com.roundlers.mytemplate.helper;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by abhayalekal on 15/01/18.
 */

public class ViewHelper {
    public static final long FRAME_DURATION = 1000 / 60;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    @SuppressLint("NewApi")
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue))
                    return result;
            }
        } else
            return View.generateViewId();
    }

    public static boolean hasState(int[] states, int state) {
        if (states == null)
            return false;
        for (int state1 : states)
            if (state1 == state)
                return true;
        return false;
    }

    public static void setBackground(View v, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            v.setBackground(drawable);
        else
            v.setBackgroundDrawable(drawable);
    }
}