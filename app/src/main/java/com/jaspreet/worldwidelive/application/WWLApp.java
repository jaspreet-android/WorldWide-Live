package com.jaspreet.worldwidelive.application;

import android.app.Application;

/**
 * Created by office on 30/07/17.
 */

public class WWLApp extends Application {

    public static WWLApp instance;

    public static WWLApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
