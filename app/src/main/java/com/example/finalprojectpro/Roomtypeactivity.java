package com.example.finalprojectpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Roomtypeactivity extends AppCompatActivity {
    RoomtypeAdapter roomtypeAdapter;
    RecyclerView recyclerView;
    List<Roomtypegettersetter> roomtypegettersetters=new ArrayList<>();
    String hotelname;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomtypeactivity);
        hotelname=getIntent().getStringExtra("hotelpass");
        database=FirebaseDatabase.getInstance();
        roomtypeAdapter=new RoomtypeAdapter(getApplicationContext(),roomtypegettersetters,hotelname);
        recyclerView=findViewById(R.id.roomtyperecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();
        reference=database.getReference().child("HotelDetails").child("Hotels").child(hotelname).child("RoomTypes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomtypegettersetters.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Roomtypegettersetter roomtypegettersetter = dataSnapshot.getValue(Roomtypegettersetter.class);
                    roomtypegettersetters.add(roomtypegettersetter);
                    Log.d("Data","RoomTypes:"+roomtypegettersetter);
                }
                recyclerView.setAdapter(roomtypeAdapter);
                roomtypeAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }}