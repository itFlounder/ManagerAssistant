package com.shsany.managerassistant;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PC on 2017/10/17.
 */

public class PreManager {

    private static final String IS_FIRST_TIME_LAUNCH = "IsFrist";

    private Context mContext;
    SharedPreferences preferences;

    private static final int PRIVATE_MODE = 0;
    //SharedPreferences的文件名
    private static final String PRE_NAME = "intro_slider";
    private final SharedPreferences.Editor editor;

    public PreManager(Context context){
        this.mContext = context;
        preferences = context.getSharedPreferences(PRE_NAME,PRIVATE_MODE);
        editor = preferences.edit();

    }

    public void setIsFirstTimeLaunch(Boolean isFirst){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirst);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }
}
