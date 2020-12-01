package com.example.finalprojectpro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView username;
    ImageView location;
    Animation slide;
    TabLayout tab;
    RecyclerView recyclerView;
    List<Reservationdetail> reservationdetailList=new ArrayList<>();
    RecentHotels recentHotels;
    FirebaseDatabase database;
    DatabaseReference reference;


    public static final int PERMISSION_CODE=99;

    public HomeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slide = AnimationUtils.loadAnimation(getContext(), R.anim.slide);
        username = view.findViewById(R.id.username);
        location = view.findViewById(R.id.location);
        tab = view.findViewById(R.id.tablayout);
        username.setAnimation(slide);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            CheckPermission();
        }
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MapsActivity.class));
            }
        });
        PrefManager manager = new PrefManager(getActivity());
        username.setText(String.format("Hi %s", manager.getusername()));
        database=FirebaseDatabase.getInstance();
        recyclerView=view.findViewById(R.id.visitedhotels);
        recentHotels=new RecentHotels(getContext(),reservationdetailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String i=auth.getCurrentUser().getUid();
        reference=database.getReference().child("HotelDetails").child("MyReservation").child(i);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservationdetailList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Reservationdetail reservationdetail=snapshot1.getValue(Reservationdetail.class);
                    reservationdetailList.add(reservationdetail);
                }
                recyclerView.setAdapter(recentHotels);
                recentHotels.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public boolean CheckPermission(){
        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==PERMISSION_CODE && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults.length>1){
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }

  }
}