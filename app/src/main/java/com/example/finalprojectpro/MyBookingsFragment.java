package com.example.finalprojectpro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsFragment extends Fragment {
    RecyclerView reserve;
    TextView namev,phonev,hotelv,roomtypev,numberofroomsv,durationv,billnumberv;
    TextView qrv;
    List<Reservationdetail> reservationdetails=new ArrayList<>();
    ReservaitondetailAdapter reservaitondetailAdapter;
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
        namev=view.findViewById(R.id.username);
       /* phonev=view.findViewById(R.id.phone_N);
        hotelv=view.findViewById(R.id.hotel_names);
        roomtypev=view.findViewById(R.id.room_type);
        numberofroomsv=view.findViewById(R.id.numberofrooms);
        durationv=view.findViewById(R.id.reserveduration);
        billnumberv=view.findViewById(R.id.bill);
        */
        qrv=view.findViewById(R.id.newreserve);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        //DatabaseReference reference=firebaseDatabase.getReference().child()

    }
}