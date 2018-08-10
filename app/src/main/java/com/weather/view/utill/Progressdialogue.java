package com.weather.view.utill;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.weather.R;


/**
 * Created by CDS125 on 23-02-2018.
 */

public class Progressdialogue {

    static Activity activitys;
    static Dialog dialog;
    static RotateLoading rotateLoading;

    public static void showDialog(Activity activity) {
        activitys = activity;
        dialogu();
    }

    public static void dismiss() {

        rotateLoading.stop();
        dialog.cancel();
    }


    private static void dialogu() {
        dialog = new Dialog(activitys, android.R.style.Theme_Light);
        final TypedValue value = new TypedValue();
        activitys.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.activity_progress);
        dialog.setCancelable(true);
        rotateLoading = (RotateLoading) dialog.findViewById(R.id.rotateloading);
        rotateLoading.start();

        dialog.show();
    }

}
