package com.example.finalprojectpro;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        String language="aa";
        Locale localswitichto=new Locale(language);
        ContextWrapper localupdatecontext=new ContextUtils(getApplicationContext()).updateLocale(newBase,localswitichto);
        super.attachBaseContext(localupdatecontext);
    }
}
