package com.roundlers.mytemplate.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

import java.util.regex.Matcher;

public class AppHelper {

    public static int getStartTime(Matcher youtubeMatcher) {
        String time = "";
        int startTime = 0;
        if (youtubeMatcher.group(0).contains("?t=") || youtubeMatcher.group(0).contains("&start=") || youtubeMatcher.group(0).contains("&t=")) {
            String[] parts = youtubeMatcher.group(0).split("(?:\\?t=|&t=|&start=)");
            if (parts.length > 1)
                time = parts[1].trim();
        }
        if (time != "") {
            if (time.matches(".*[a-z].*")) {  //if time format = xhxmxs
                //String startTimePattern = "([^m]*)m([^s]*)";
                int hr = time.indexOf("h");
                int min = time.indexOf("m");
                int sec = time.indexOf("s");
                try {
                    if (hr > -1) {
                        startTime += Integer.parseInt(time.substring(0, hr)) * 3600000;
                    }
                    if (min > -1) {
                        int startIndexMin = 0;
                        if (hr > -1) {
                            startIndexMin = hr + 1;
                        }
                        startTime += Integer.parseInt(time.substring(startIndexMin, min)) * 60000;
                    }
                    if (sec > -1) {
                        int startIndexSec = 0;
                        if (min > -1) {
                            startIndexSec = min + 1;
                        } else if (hr > -1) {
                            startIndexSec = hr + 1;
                        }
                        startTime += Integer.parseInt(time.substring(startIndexSec, sec)) * 1000;
                    }
                } catch (RuntimeException e) {
                    startTime = 0;
                }
            } else if (time.matches("^\\d+$")) {
                startTime = Integer.parseInt(time) * 1000;
            }
        }
        return startTime;
    }

    public static void dieIfNull(String message, Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new RuntimeException(message);
            }
        }
    }


    public static void dieIfNull(Object... objects) {
        dieIfNull("Death by null object!", objects);
    }

    public static int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static int measureCellWidth(Context context, View cell) {
        if (cell.getParent() == null) {
            // We need a fake parent
            FrameLayout buffer = new FrameLayout(context);
            android.widget.AbsListView.LayoutParams layoutParams = new android.widget.AbsListView.LayoutParams(
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            buffer.addView(cell, layoutParams);
            cell.forceLayout();
            cell.measure(1000, 1000);
            int width = cell.getMeasuredWidth();
            buffer.removeAllViews();
            return width;
        } else {
            cell.forceLayout();
            cell.measure(1000, 1000);
            int width = cell.getMeasuredWidth();
            return width;
        }
    }

    public static boolean isLoggedIn(Context context) {
        return SharedPreferencesHelper.getLoggedInUser() != null;
    }

    public static Point getDeviceDimensions(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }


    public static boolean isConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
}
