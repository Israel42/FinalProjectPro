package com.example.finalprojectpro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Hotellist extends AppCompatActivity {
    HoteldetailAdapter hoteldetailAdapter;
    RecyclerView recyclerView;
    List<Hoteldetail> hoteldetailList=new ArrayList<>();
    String hkind;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotellist);
        hkind=getIntent().getStringExtra("hkindpass");
        database=FirebaseDatabase.getInstance();
        hoteldetailAdapter=new HoteldetailAdapter(getApplicationContext(),hoteldetailList);
        recyclerView=findViewById(R.id.hotelrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();
        reference=database.getReference().child("Hoteltypes").child(hkind);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoteldetailList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Hoteldetail hoteldetail=dataSnapshot.getValue(Hoteldetail.class);
                    hoteldetailList.add(hoteldetail);
                }
                recyclerView.setAdapter(hoteldetailAdapter);
                hoteldetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}