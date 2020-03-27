package com.roundlers.mytemplate.helper;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.widget.Toast;

import com.roundlers.mytemplate.constants.BuildConstants;
import com.roundlers.mytemplate.utils.Logger;

/**
 * Created by abhayalekal on 02/11/17.
 */

public class LogHelper {

    public static void log(String key, String value) {
        if (BuildConstants.DEBUG) {
            Logger.debugEntire(
                    BuildConstants.APP_NAME +":-->  "+ key +" : "+ value);
        }
    }

    public static void log2(String key, String value) {
        if (BuildConstants.DEBUG) {
            Logger.e(BuildConstants.APP_NAME +":-->  "+ key, value);
        }
    }

    public static Toast showBottomToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static Toast showBottomToast(Context context, int id) {
        Toast toast = Toast.makeText(context, context.getString(id), Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static void showCentreToast(Context context, int stringRes) {
        showCentreToast(context, context.getString(stringRes), false);
    }

    public static void showCentreToast(Context context, String msg, boolean translate) {

        Toast toast = Toast.makeText(context, Html.fromHtml(msg), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
//
//    public static void showSnackBar(View anyView, String msg) {
//
//        Snackbar snackbar = Snackbar
//                .make(anyView, msg, Snackbar.LENGTH_LONG);
//        snackbar.show();
//    }
//
//    public static void showSnackBar(View anyView, String msg, String clickText, View.OnClickListener onClickListener) {
//
//        Snackbar snackbar = Snackbar
//                .make(anyView, msg, Snackbar.LENGTH_LONG)
//                .setAction(clickText, onClickListener);
//        snackbar.show();
//    }
}
