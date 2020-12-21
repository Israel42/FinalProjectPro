package com.example.finalprojectpro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsFragment extends Fragment {
    RecyclerView reserve;
    List<Reservationdetail> reservationdetails=new ArrayList<>();
    ReservaitondetailAdapter reservaitondetailAdapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    String pkey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reserve=view.findViewById(R.id.reservehistory);
        database=FirebaseDatabase.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid();
        reference=database.getReference().child("Hoteltypes").child("OwnReservation").child(uid);
        reservaitondetailAdapter=new ReservaitondetailAdapter(getContext(),reservationdetails);
        reserve.setLayoutManager(new LinearLayoutManager(getContext()));
        reserve.hasFixedSize();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservationdetails.clear();
                    for (DataSnapshot snapshot1:snapshot.getChildren()){

                        Log.d("Values", "onDataChange: "+snapshot1.getValue().toString());
                        Reservationdetail reservationdetail=snapshot1.getValue(Reservationdetail.class);
                        reservationdetails.add(reservationdetail);
                    }
                    reserve.setAdapter(reservaitondetailAdapter);
                    reservaitondetailAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}