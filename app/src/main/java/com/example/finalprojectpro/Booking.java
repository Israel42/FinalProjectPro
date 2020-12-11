package com.example.finalprojectpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Booking extends AppCompatActivity {
    ImageView imageView;
    TextView hotelN,hotelnot,rate,review,price;
    Button selectroom;
    FirebaseDatabase database;
    DatabaseReference reference;
    String hkind,hotelintentname,pricelow,pricehigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        imageView=findViewById(R.id.hotelimage2);
        hotelN=findViewById(R.id.hotelnamedetail);
        hotelnot=findViewById(R.id.shortnote);
        rate=findViewById(R.id.rating1);
        review=findViewById(R.id.reviews);
        selectroom=findViewById(R.id.selectroom);
        database=FirebaseDatabase.getInstance();
        hotelintentname=getIntent().getStringExtra("hotelpass");
        hkind=getIntent().getStringExtra("hkindpass");
        reference=database.getReference().child("Hoteltypes").child(hkind).child(hotelintentname);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Reviews.class);
                intent.putExtra("hotelpass",hotelintentname);
                intent.putExtra("hkindpass",hkind);
                startActivity(intent);
            }
        });
        selectroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Roomkindlist.class);
                intent.putExtra("hotelpass",hotelintentname);
                intent.putExtra("hkindpass",hkind);
                startActivity(intent);
            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Hoteldetail hoteldetail=snapshot.getValue(Hoteldetail.class);
                hotelN.setText(hoteldetail.getName());
                hotelnot.setText(hoteldetail.getNote());
                rate.setText(String.valueOf(hoteldetail.getRating()));
                Picasso.get().load(hoteldetail.getImagepath()).fit().into(imageView);
                /*   DatabaseReference reference1=reference.child("RoomTypes");
                DatabaseReference pricelowref=reference1.child("Single");
                DatabaseReference pricehighref=reference1.child("Presidential");
                pricelowref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        RoomGS roomtypegettersetter=snapshot.getValue(RoomGS.class);
                        pricelow=String.valueOf(roomtypegettersetter.getPrice());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                pricehighref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        RoomGS roomtypegettersetter=snapshot.getValue(RoomGS.class);
                        pricehigh=String.valueOf(roomtypegettersetter.getPrice());
                        price.setText(String.format("%sETB-%sETB", pricelow, pricehigh));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}