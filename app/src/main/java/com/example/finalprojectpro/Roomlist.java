package com.example.finalprojectpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Roomlist extends AppCompatActivity {
    RoomAdapter roomAdapter;
    RecyclerView recyclerView;
    List<RoomGS> roomGSList=new ArrayList<>();
    String hotelname,hkind,rkind;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomlistactivity);
        hotelname=getIntent().getStringExtra("hotelpass");
        hkind=getIntent().getStringExtra("hkindpass");
        rkind=getIntent().getStringExtra("rkindpass");
        database=FirebaseDatabase.getInstance();
        recyclerView=findViewById(R.id.roomtyperecycler);
        roomAdapter=new RoomAdapter(getApplicationContext(),roomGSList,hkind,hotelname,rkind);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();
        reference=database.getReference().child("Hoteltypes").child(hkind).child(hotelname).child("Roomtypes").child(rkind);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomGSList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    RoomGS roomGS=snapshot1.getValue(RoomGS.class);
                    roomGSList.add(roomGS);
                }
                recyclerView.setAdapter(roomAdapter);
                roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
}