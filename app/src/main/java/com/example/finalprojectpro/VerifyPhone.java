package com.example.finalprojectpro;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.aabhasjindal.otptextview.OtpTextView;

public class VerifyPhone extends AppCompatActivity {
    OtpTextView otpTextView;
    Button verify;
    VideoView videoView;
    FirebaseAuth auth;
    String VerificationId,Phonenum;
    MediaPlayer mplayer;
    int CurrentPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        otpTextView=findViewById(R.id.otpTextView);
        verify=findViewById(R.id.verfiy);
        auth=FirebaseAuth.getInstance();
        Phonenum=getIntent().getStringExtra("Phonenum");
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
                otpTextView.setOTP(code);
                VerifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }
    };

    private void VerifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(VerificationId,code);
        SignInWithCredential(credential);
    }

    private void SignInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               final FirebaseUser user = task.getResult().getUser();
               String uid = user.getUid();
               final FirebaseFirestore db=FirebaseFirestore.getInstance();
               final DocumentReference docRef = db.collection("Users").document(uid);
               docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(final DocumentSnapshot documentSnapshot) {
                       if (documentSnapshot.exists()) {
                           Toast.makeText(VerifyPhone.this, "There is Value", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(VerifyPhone.this,MainActivity.class));
                           finish();
                       } else {
                           //redirect to sign up page
                           Toast.makeText(VerifyPhone.this, "No Value", Toast.LENGTH_SHORT).show();
                           Intent intent=new Intent(VerifyPhone.this,Register.class);
                           intent.putExtra("Phonenum",Phonenum);
                           startActivity(intent);
                           finish();
                       }
           }
        });
           }
        });
    }


}
