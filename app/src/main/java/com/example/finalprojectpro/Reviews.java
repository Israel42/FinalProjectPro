package com.example.finalprojectpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Reviews extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Review> reviewList=new ArrayList<>();
    ReviewsAdapter reviewsAdapter;
    String hotelname;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        hotelname=getIntent().getStringExtra("hotelpass");
        database=FirebaseDatabase.getInstance();
        recyclerView=findViewById(R.id.reviewrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recyclerView.hasFixedSize();
        reference=database.getReference().child("HotelDetails").child("Hotels").child(hotelname).child("Reviews");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviewList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Review review=snapshot.getValue(Review.class);
                    reviewList.add(review);
                }
                recyclerView.setAdapter(reviewsAdapter);
                reviewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}