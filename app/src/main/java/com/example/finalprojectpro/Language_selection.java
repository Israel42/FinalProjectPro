package com.example.finalprojectpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        languageselection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                language=(RadioButton) radioGroup.findViewById(i);
                if (language!=null){
                    switch (i){
                        case R.id.amharic:
                            locale=new Locale("aa");
                            language.setChecked(true);
                            Locale.setDefault(locale);
                            Configuration configuration = new Configuration();
                            configuration.locale = locale;
                            getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
                            recreate();
                            break;
                        case R.id.english:
                            language.setChecked(true);
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