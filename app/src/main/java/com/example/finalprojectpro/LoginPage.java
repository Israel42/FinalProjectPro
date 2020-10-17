package com.example.finalprojectpro;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class LoginPage extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    EditText Phone;
    String phonenum,countrycode,phone;
    Button Login;
    VideoView videoView;
    MediaPlayer mPlayer;
    int CurrentPostiton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        countryCodePicker=findViewById(R.id.countrycode);
        Phone=findViewById(R.id.phonenumber);
        Login=findViewById(R.id.Login);
        videoView=findViewById(R.id.videologin);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        if (user!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        Uri uri=Uri.parse("android.resource://"
                +getPackageName()
                +"/"
                +R.raw.projectx);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(mediaPlayer -> {
            mPlayer=mediaPlayer;
            mPlayer.setLooping(true);
            if (CurrentPostiton!=0){
                mPlayer.seekTo(CurrentPostiton);
                mPlayer.start();
            }
        });
        Login.setOnClickListener(view ->{
            phone=Phone.getText().toString();
            if (phone.length()<9){
                Phone.setError("Please Add A phone Number");
                Phone.setFocusable(true);
                return;
            }
            countrycode=countryCodePicker.getSelectedCountryCode();
            phonenum=countrycode+phone;
            Intent intent=new Intent(LoginPage.this,VerifyPhone.class);
            intent.putExtra("phone",phonenum);
            startActivity(intent);
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CurrentPostiton=mPlayer.getCurrentPosition();
        videoView.pause();
    }
}
