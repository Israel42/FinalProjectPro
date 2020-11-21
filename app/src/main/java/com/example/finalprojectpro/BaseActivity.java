package com.example.finalprojectpro;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences sharedPref = newBase.getSharedPreferences("language", Context.MODE_PRIVATE);
        String lang = sharedPref.getString("Language", "en");
        Locale localswitichto=new Locale(lang);
        ContextWrapper localupdatecontext=new ContextUtils(this).updateLocale(newBase,localswitichto);
        super.attachBaseContext(localupdatecontext);
    }
}
