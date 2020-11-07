package com.example.finalprojectpro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HotelsFragment extends Fragment {
    RecyclerView recyclerView;
    List<Hoteldetail> hoteldetailList= new ArrayList<>();
    HoteldetailAdapter hoteldetailAdapter;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotels,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database=FirebaseDatabase.getInstance();
        recyclerView=view.findViewById(R.id.hotelsrecycler);
        hoteldetailAdapter=new HoteldetailAdapter(getContext(),hoteldetailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        reference=database.getReference().child("HotelDetails").child("Hotels");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoteldetailList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Hoteldetail hoteldetail=snapshot1.getValue(Hoteldetail.class);
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
