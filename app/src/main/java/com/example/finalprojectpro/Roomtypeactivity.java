package com.example.finalprojectpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Roomtypeactivity extends AppCompatActivity {
    RoomtypeAdapter roomtypeAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomtypeactivity);
        recyclerView=findViewById(R.id.chooseroomtype);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }
}