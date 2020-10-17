package com.example.finalprojectpro;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private static final String FIRST_TIME="isfirst_time";

    public PrefManager(Context context) {
        this.context = context;
         sharedPreferences=context.getSharedPreferences("FIRST_TIME",Context.MODE_PRIVATE);
         editor=sharedPreferences.edit();
    }
    public void setFirstTime(boolean isfirst_time){
        editor.putBoolean(FIRST_TIME,isfirst_time);
        editor.apply();
    }
    public boolean FirstLounch(){
        return sharedPreferences.getBoolean(FIRST_TIME,true);
    }

}
