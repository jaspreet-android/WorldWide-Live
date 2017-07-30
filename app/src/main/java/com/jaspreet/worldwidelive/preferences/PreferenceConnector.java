package com.jaspreet.worldwidelive.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.jaspreet.worldwidelive.application.WWLApp;

/**
 * Created by office on 30/07/17.
 */

/**
 * The class PreferenceConnector is a class useful to simplify you the
 * interaction with your app preferences. In fact it has methods that interact
 * with the basical features of SharedPreferences but still the possibility to
 * obtain preferences.
 */
public class PreferenceConnector {
    public static final String PREF_NAME = "WWL_PREFERENCES";
    public static final int MODE = Context.MODE_PRIVATE;

    // App User
    public final static String PREF_IS_USER_LOGGEDIN = "user_loogged_in";
    public final static String PREF_USER_PHONE_NUMBER = "phone_number";
    public final static String PREF_CODE = "code";

    public static void writeBoolean(String key, boolean value, Context context) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(String key, boolean defValue, Context context) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(String key, int value, Context context) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(String key, int defValue, Context context) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(String key, String value, Context context) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(String key, String defValue, Context context) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(String key, float value, Context context) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(String key, float defValue, Context context) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(String key, long value, Context context) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(String key, long defValue, Context context) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        if (null == context) {
            context = WWLApp.getInstance().getApplicationContext();
        }
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void clearAll(Context context) {
        getEditor(context).clear();
        getEditor(context).commit();
    }

}
