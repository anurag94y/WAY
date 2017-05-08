package com.way.way.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.way.way.Activity.LoginActivity;
import com.way.way.Activity.MainActivity;

import java.util.HashMap;


/**
 * Created by anurag.yadav on 4/21/17.
 */

public class SessionManagement {
    SharedPreferences pref;
    Editor editor;
    Context context;
    int PRIVATE_MODE = 6;
    public static final String PREF_NAME = "WAY_LOGIN_CRENEDENTIALS";
    public static final String IS_LOGIN = "Is_Logged_In";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_MOBILE_NUMBER = "8958807064";

    public SessionManagement(Context _context) {
        context = _context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void CreateLoginSession(String username, String mobileNumber) {
        System.out.println("Creating Login Sesssion username : " + username);
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void checkLogin(){
        if(this.isLoggedIn()){
            Intent i = new Intent(context, MainActivity.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_MOBILE_NUMBER, pref.getString(KEY_MOBILE_NUMBER, null));
        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
