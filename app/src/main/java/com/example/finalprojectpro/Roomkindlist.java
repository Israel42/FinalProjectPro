package com.example.finalprojectpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Roomkindlist extends AppCompatActivity {
    Roomkindadaptor roomkindadaptor;
    RecyclerView recyclerView;
    List<RoomKindGS> roomKindGSList=new ArrayList<>();
    String hkind,hname;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomkindlist);
        hkind=getIntent().getStringExtra("hkindpass");
        hname=getIntent().getStringExtra("hotelpass");
        database=FirebaseDatabase.getInstance();
        roomkindadaptor=new Roomkindadaptor(getApplicationContext(),roomKindGSList,hkind,hname);
        recyclerView=findViewById(R.id.roomkindrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();
        reference=database.getReference().child("rooms").child(hname);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomKindGSList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    RoomKindGS roomKindGS=snapshot1.getValue(RoomKindGS.class);
                    roomKindGSList.add(roomKindGS);
                }
                recyclerView.setAdapter(roomkindadaptor);
                roomkindadaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}