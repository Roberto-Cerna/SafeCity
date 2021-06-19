package com.example.safecity.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class SafePreferences {
    private static final String KEY_LOGIN_STATE = "key_login_state";

    private SharedPreferences preferences;

    public SafePreferences(Context context){
        preferences =
                context.getSharedPreferences("travel-blog", Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(KEY_LOGIN_STATE, false);
    }

    public void setLoggedIn(boolean loggedIn){
        preferences.edit().putBoolean(KEY_LOGIN_STATE, loggedIn).apply();
    }
}
