package com.example.hbg.appster;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    public static final String EMAIL = ".email";
    public static final String PASSWORD = ".password";
    public static final String CATEGORY = ".category";
    public static final String NAME = ".name";
    public static final String CONTACT = ".contact";
    public static final String LOGGED_IN = ".loggedin";
    public static final String DATA_SAVED = ".datasaved";
    public static final int CAT_OWNER = 0;
    public static final int CAT_CUST = 1;

    private SharedPreferences loginPreferences;
    private SharedPreferences appPrefereneces;
    private static PreferenceHelper instance;

    public PreferenceHelper(Context context) {
        loginPreferences = context.getSharedPreferences(context.getString(R.string.login_prefs), Context.MODE_PRIVATE);
        appPrefereneces = context.getSharedPreferences(context.getString(R.string.app_prefs), Context.MODE_PRIVATE);
    }

    public static PreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceHelper(context);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return loginPreferences.getBoolean(LOGGED_IN, false);
    }

    public boolean isDataSaved() {
        return loginPreferences.getBoolean(DATA_SAVED, false);
    }

    public void saveUserDetails(String username, String password) {
        loginPreferences.edit().putBoolean(LOGGED_IN, true).putString(EMAIL, username).putString(PASSWORD, password).apply();
    }

    public void saveUserInfo(String name, int cat, String contact){
        loginPreferences.edit().putString(NAME, name).putString(CONTACT, contact).putInt(CATEGORY, cat).putBoolean(DATA_SAVED, true).apply();
    }

    public UserDetail getUserInfo() {
        UserDetail detail = new UserDetail();
        detail.name = loginPreferences.getString(NAME, null);
        detail.category = loginPreferences.getInt(CATEGORY, 0);
        detail.email = loginPreferences.getString(EMAIL, null);
        return detail;
    }
}