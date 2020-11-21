package com.example.finalprojectpro;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences sharedPreferences;
    Context context;
    public static final String SAVEDSTATE="save";
    public static final String VALUENAMW="name";
    public static final String CHOOSELANGUAGE="language";
    public PrefManager( Context context) {
        this.context = context;
    }

    public PrefManager() {
    }
    public void savename(String name){
        sharedPreferences=context.getSharedPreferences(SAVEDSTATE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(VALUENAMW,name);
        editor.apply();
    }
    public String getusername(){
        sharedPreferences=context.getSharedPreferences(SAVEDSTATE,Context.MODE_PRIVATE);
        return sharedPreferences.getString(VALUENAMW,null);
    }
    public void updatelanguage(String language){
        sharedPreferences=context.getSharedPreferences(CHOOSELANGUAGE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Language",language);
        editor.apply();
    }


}
