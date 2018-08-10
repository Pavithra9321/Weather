package com.weather.view.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.weather.view.utill.AppConstants;
import com.weather.view.utill.AppController;
import com.weather.view.utill.ConnectivityReceiver;


/**
 * Created by CDS124 on 07-02-2018.
 */

public class BaseActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    View parentLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        parentLayout = findViewById(android.R.id.content);
        showSnack(ConnectivityReceiver.isConnected());
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (!isConnected) { // message = "Good! Connected to Internet"; color = Color.WHITE;
            message = "Sorry! Not connected to internet";
            color = Color.WHITE;

        Snackbar snackbar = Snackbar
                .make(parentLayout, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        AppController.getInstance().setConnectivityListener(this);
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(parentLayout, message, Snackbar.LENGTH_SHORT);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }


    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

    }

}
