package com.atreya.myapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by atreya on 31/5/17.
 */

public class SharedPref
{

    public static final String REMIND_ME_SHARED_PREF = "Rmindme";
    public static SharedPreferences sharedpreferences;


    public static boolean setPreference(Context context, String key, String value) {
        sharedpreferences = context.getSharedPreferences(REMIND_ME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getPreference(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(REMIND_ME_SHARED_PREF, Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "empty");
    }
}