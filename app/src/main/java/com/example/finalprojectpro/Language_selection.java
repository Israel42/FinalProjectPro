package com.example.finalprojectpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

public class Language_selection extends AppCompatActivity {
    RadioGroup languageselection;
    RadioButton language;
    Button next;
    Locale locale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        languageselection=findViewById(R.id.languageradiogroup);
        next=findViewById(R.id.next);
        languageselection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                language=(RadioButton) radioGroup.findViewById(i);
                if (language!=null){
                    switch (i){
                        case R.id.amharic:
                       PrefManager prefManager=new PrefManager(getApplicationContext());
                        String language="aa";
                        prefManager.updatelanguage(language);

                            break;
                        case R.id.english:
                            PrefManager prefManager1=new PrefManager(getApplicationContext());
                            String language2="en";
                            prefManager1.updatelanguage(language2);
                            break;
                        default:

                    }
                }
            }
        });
       next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Language_selection.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        });


    }

}