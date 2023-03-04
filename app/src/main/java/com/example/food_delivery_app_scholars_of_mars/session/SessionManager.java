package com.example.food_delivery_app_scholars_of_mars.session;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager   {


    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULL_NAME = "fullName";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String kEY_EMAIL = "email";
    public static final String kEY_ADDRESS = "address";

    public SessionManager(Context context) {
        this.context = context;
        userSession = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = userSession.edit();

    }

    public void createLoginSession(String name, String email, String phoneNumber, String address){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FULL_NAME, name);
        editor.putString(KEY_PHONE_NUMBER, phoneNumber);
        editor.putString(kEY_EMAIL, email);
        editor.putString(kEY_ADDRESS, address);
        editor.commit();
    }

    public HashMap<String, String> getUserInfo(){
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put(KEY_FULL_NAME, userSession.getString(KEY_FULL_NAME, null));
        userInfo.put(KEY_PHONE_NUMBER, userSession.getString(KEY_PHONE_NUMBER, null));
        userInfo.put(kEY_EMAIL, userSession.getString(kEY_EMAIL, null));
        userInfo.put(kEY_ADDRESS, userSession.getString(kEY_ADDRESS, null));
        return userInfo;
    }
    public boolean isLogged(){
        return userSession.getBoolean(IS_LOGIN, false);
    }
    public void logOut(){
        editor.clear();
        editor.commit();
    }
}
