package com.example.finalprojectpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    Long ms=0,splashtime=4000;// time new yeha isru mechemr mekenese techelale
    Boolean pause=true;
    Boolean splashActivity=true;
    Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            .getBoolean("isFirstRun", true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                   while (splashActivity&&ms<splashtime){
                       if (!pause){
                           ms+=100;
                           sleep(100);
                       }
                   }
                }catch (e:Exception){
                }
                finally{
                    if (isFirstRun){
                        firstlounch();
                    }else {
                        Intent intent=new Intent(SplashScreen.this,LoginPage.class);
                        startActivity(intent);
                    }
                }thread.start();
                }
            }
        }
        public void firstlounch(){
        Intent intent=new Intent(SplashScreen.this,Language_selection.class);
        startActivity(intent);
            SharedPreferences preferences=getSharedPreferences("PREFERENCE",MODE_PRIVATE);
            SharedPreferences.Editor edit=preferences.edit();
            edit.putBoolean("isFirstRun",false);
            edit.apply();

        }

}