package com.example.finalprojectpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class Reservation extends AppCompatActivity {
    Button pluse,minus,reservebt;
    TextInputLayout checkinD,checkoutD;
    EditText billin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        //Date picker....dialogue connected with activity..reserv


    }
}