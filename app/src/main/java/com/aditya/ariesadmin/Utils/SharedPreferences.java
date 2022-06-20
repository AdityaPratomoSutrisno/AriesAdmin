package com.aditya.ariesadmin.Utils;

import android.content.Context;

import java.util.HashMap;

public class SharedPreferences {

    private static SharedPreferences mInstance;
    private static Context mCtx;
    android.content.SharedPreferences pref;
    android.content.SharedPreferences.Editor editor;

    private static final String SHARED_PREF_NAME = "ariesadmin";
    private static final String IS_LOGIN = "isLogin";
    private static final String IS_FIRST = "isFirst";
//    Boolean IS_FIRST_TIME_LAUNCH = "isFisrtLaunch";

    public SharedPreferences(Context context){
        mCtx = context;
        pref = context.getSharedPreferences(SHARED_PREF_NAME,0);
        editor = pref.edit();
    }

    public static synchronized SharedPreferences getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPreferences(context);
        }
        return mInstance;
    }

    public void createSessionLogin(){
        android.content.SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

//    public void createSessionFirtUse(){
//        android.content.SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(IS_FIRST, true);
//        editor.commit();
//    }


//    public boolean isNotFirst(){
//        return pref.getBoolean(IS_FIRST, false);
//    }

    public void logout(){
        android.content.SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
//        editor.putBoolean(IS_FIRST, true);
        editor.commit();
    }

}
