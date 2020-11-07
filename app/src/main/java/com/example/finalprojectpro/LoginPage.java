package com.example.finalprojectpro;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class LoginPage extends AppCompatActivity {
    Button loginpage,loginphone;
    CountryCodePicker countryCodePicker;
    EditText phone;
    CardView cardView;
    String phonenumber;
    ViewFlipper flipper;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        if (user!=null){
            startActivity(new Intent(LoginPage.this,MainActivity.class));
            finish();
        }
        loginpage=findViewById(R.id.loginbutton);
        loginphone=findViewById(R.id.loginphone);
        countryCodePicker=findViewById(R.id.countrycodepicker);
        cardView=findViewById(R.id.cardview);
        phone=findViewById(R.id.phone);
        flipper=findViewById(R.id.viewflipper);
        flipper.setAutoStart(true);
        flipper.startFlipping();
        flipper.setInAnimation(getApplicationContext(),R.anim.slide_in_right);
        flipper.setOutAnimation(getApplicationContext(),R.anim.slide_out_activity);
        flipper.setFlipInterval(10000);
        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.VISIBLE);
                loginpage.setVisibility(View.VISIBLE);
                loginpage.setVisibility(View.INVISIBLE);
            }
        });
        loginphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenum=phone.getText().toString();
                String code=countryCodePicker.getSelectedCountryCode();
                if (phonenum.isEmpty()||phonenum.length()<9){
                    phone.setError("PhoneNumber Not Valid");
                    phone.setFocusable(true);
                    return;
                }
                phonenumber=code+phonenum;
                Intent intent=new Intent(getApplicationContext(),VerifyPhone.class);
                intent.putExtra("Phonenum",phonenumber);
                startActivity(intent);
            }
        });
 }
}
