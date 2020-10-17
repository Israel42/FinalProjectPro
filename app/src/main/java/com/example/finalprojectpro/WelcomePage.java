package com.example.finalprojectpro;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class WelcomePage extends AppCompatActivity {
    VideoView videoView;
    Button getstarted;
    MediaPlayer mPlayer;
    int Currentpostition;
    PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        videoView=findViewById(R.id.videowelcomepage);
        getstarted=findViewById(R.id.getstarted);
        prefManager=new PrefManager(getApplicationContext());

        if (!prefManager.FirstLounch()){
           homeactivity();

        }
        Uri uri=Uri.parse("android.resource://"
        +getPackageName()
        +"/"
        +R.raw.demohotelfinding);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(mediaPlayer -> {
            mPlayer=mediaPlayer;
            mPlayer.setLooping(true);
            if (Currentpostition!=0){
                mPlayer.seekTo(Currentpostition);
                mPlayer.start();
            }
        });
        getstarted.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),LoginPage.class));
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Currentpostition=mPlayer.getCurrentPosition();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }
    public void homeactivity(){
        prefManager.setFirstTime(false);
        prefManager.setFirstTime(false);
        Intent intent=new Intent(getApplicationContext(),LoginPage.class);
        startActivity(intent);
        finish();
    }
}
