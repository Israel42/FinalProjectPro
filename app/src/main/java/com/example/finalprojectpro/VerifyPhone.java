package com.example.finalprojectpro;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.aabhasjindal.otptextview.OtpTextView;

public class VerifyPhone extends AppCompatActivity {
    OtpTextView otpTextView;
    Button verify;
    VideoView videoView;
    FirebaseAuth auth;
    SweetAlertDialog sweetAlertDialog;
    String VerificationId,Phonenum;
    MediaPlayer mplayer;
    int CurrentPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        otpTextView=findViewById(R.id.otpTextView);
        verify=findViewById(R.id.verfiy);
        videoView=findViewById(R.id.videoverify);
        sweetAlertDialog=new SweetAlertDialog(getApplicationContext());
       // sweetAlertDialog.changeAlertType(SweetAlertDialog.NORMAL_TYPE);
        //sweetAlertDialog.setTitle("Waiting For The OTP");
//        sweetAlertDialog.show();
        Uri uri=Uri.parse("android.resource://"
                +getPackageName()
                +"/"
                +R.raw.projectx);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(mediaPlayer -> {
            mplayer=mediaPlayer;
            mplayer.setLooping(true);
            if (CurrentPosition!=0){
                mplayer.seekTo(CurrentPosition);
                mplayer.start();
            }
        });
        auth=FirebaseAuth.getInstance();
        Phonenum=getIntent().getStringExtra("phone");
        sendVerificationCode(Phonenum);
        verify.setOnClickListener(view->{
            String code=otpTextView.getOTP();
            if (code.isEmpty()){
                otpTextView.showError();
            }
            VerifyCode(code);
        });
    }

    private void sendVerificationCode(String phonenum) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+phonenum,
                120,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallback
        );
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            VerificationId=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if (code!=null){
                /*sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitle("Message Recived");
                sweetAlertDialog.show();
                otpTextView.setOTP(code);*/
                VerifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
           /* sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitle("ERROR!!!");
            sweetAlertDialog.setConfirmText(e.getMessage());
            sweetAlertDialog.show();*/
        }
    };

    private void VerifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(VerificationId,code);
        SignInWithCredential(credential);
    }

    private void SignInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               /*FirebaseUser user=task.getResult().getUser();
               long CurrentSignTime=user.getMetadata().getCreationTimestamp();
               long LastTimeStamp=user.getMetadata().getLastSignInTimestamp();
               if (CurrentSignTime==LastTimeStamp){*/
                   Intent intent=new Intent(VerifyPhone.this,Register.class);
                   intent.putExtra("Phonenum",Phonenum);
                   startActivity(intent);
           }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        CurrentPosition=mplayer.getCurrentPosition();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }
}
