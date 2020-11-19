package com.example.finalprojectpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.preference.PreferenceManager;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

public class SplashScreen extends AppCompatActivity {
    long ms=0;
    long splashtime=4000;
    boolean pause=true;
    boolean splashActivity=true;

   ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        constraintLayout=findViewById(R.id.cs);
        boolean isFirstRun = getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
          Thread thread=new Thread(){
              @Override
              public void run() {
                  try {
                      Thread.sleep(4000);
                  }catch (Exception e){
                        e.printStackTrace();
                  }finally {
                      if (!isOnline()){
                          Snackbar snackbar=Snackbar.make(constraintLayout,"NO INTERNET CONNECTION",Snackbar.LENGTH_INDEFINITE);
                          snackbar.setAction("Retry", new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                               recreate();
                              }
                          });
                          snackbar.show();
                      }else if(isFirstRun){
                          firstlounch();
                      }else{
                          Intent intent=new Intent(SplashScreen.this,LoginPage.class);
                          startActivity(intent);
                          finish();
                      }
                  }
              }
          };
         thread.start();
        }
        public void firstlounch(){
        Intent intent=new Intent(SplashScreen.this,Language_selection.class);
        startActivity(intent);
            SharedPreferences preferences=getSharedPreferences("PREFERENCE",MODE_PRIVATE);
            SharedPreferences.Editor edit=preferences.edit();
            edit.putBoolean("isFirstRun",false);
            edit.apply();

        }
        public boolean isOnline(){
            ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getActiveNetworkInfo()!=null&&connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
        }
}