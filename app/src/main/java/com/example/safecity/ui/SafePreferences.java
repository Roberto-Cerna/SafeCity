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

    public void SetName(String name){
        preferences.edit().putString("name",name).apply();
    }

    public String getName(){
        return preferences.getString("name","");
    }

    public void setPhone(String phone){
        preferences.edit().putString("phone",phone).apply();
    }
    public String getPhone(){
        return preferences.getString("phone","");
    }

    public void setEmail(String email){
        preferences.edit().putString("email",email).apply();
    }
    public String getEmail(){
        return preferences.getString("email","");
    }

    public void setId(String id){
        preferences.edit().putString("id",id).apply();
    }
    public String getId(){
        return preferences.getString("id","");
    }
}