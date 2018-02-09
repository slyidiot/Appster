package com.example.hbg.appster;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    public static final String USERNAME = ".username";
    public static final String PASSWORD = ".password";
    public static final String CATEGORY = ".category";
    public static final String LOGGED_IN = ".loggedin";
    public static final int CAT_OWNER = 0;
    public static final int CAT_CUST = 1;

    private static SharedPreferences loginPreferences;
    private static SharedPreferences appPrefereneces;
    private PreferenceHelper instance;

    public PreferenceHelper(Context context) {
        loginPreferences = context.getSharedPreferences(context.getString(R.string.login_prefs), Context.MODE_PRIVATE);
        appPrefereneces = context.getSharedPreferences(context.getString(R.string.app_prefs), Context.MODE_PRIVATE);
    }

    public PreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceHelper(context);
        }
        return instance;
    }

    public static boolean isLoggedIn() {
        return loginPreferences.getBoolean(LOGGED_IN, false);
    }

    public static void saveUserDetails(String username, String password) {
        loginPreferences.edit().putBoolean(LOGGED_IN, true).putString(USERNAME, username).putString(PASSWORD, password).apply();
    }
}