package com.example.finalprojectpro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    public MyBookingsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        reservaitondetailAdapter=new ReservaitondetailAdapter(getContext(),reservationdetails);
        reserve.setLayoutManager(new LinearLayoutManager(getContext()));
        reserve.hasFixedSize();
        reference=database.getReference().child("HotelDetails").child("Hotels").child("Reserved");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservationdetails.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        Reservationdetail reservationdetail=snapshot1.getValue(Reservationdetail.class);
                        reservationdetails.add(reservationdetail);
                    }
                    reserve.setAdapter(reservaitondetailAdapter);
                    reservaitondetailAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "There are no reserved hotels", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}