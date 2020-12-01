package com.example.finalprojectpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

public class Reservedetail extends AppCompatActivity {
    ImageView detailimage,barcode;
    TextView detailname,detailroom,checkindetail,checkoutdetail,code;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    Reservationdetail reservationdetail;
    String passc,hop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservedetail);
        detailimage=findViewById(R.id.reservedhoteldetailimage);
        barcode=findViewById(R.id.barcodeview);
        database=FirebaseDatabase.getInstance();
        Log.d("handw", "onCreate: "+barcode.getWidth()+"    "+barcode.getHeight());
        detailname=findViewById(R.id.reservedhoteldetailname);
        detailroom=findViewById(R.id.reservedroomdetailname);
        checkindetail=findViewById(R.id.detailcheckin);
        checkoutdetail=findViewById(R.id.detailcheckout);
        code=findViewById(R.id.bookcode);
        passc=getIntent().getStringExtra("codepass");
        code.setText(passc);
        displaybitmap(passc);
        Log.d("passc", "onCreate: "+passc);
        auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid();
       reference=database.getReference().child("HotelDetails").child("MyReservation").child(uid).child(passc);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservationdetail=snapshot.getValue(Reservationdetail.class);
                hop=reservationdetail.getReservedhotel();
                detailname.setText(hop);
                detailroom.setText(String.valueOf(reservationdetail.getReservedroomtype()));
                checkindetail.setText(String.valueOf(reservationdetail.getIndate()));
                checkoutdetail.setText(String.valueOf(reservationdetail.getOutdate()));
                DatabaseReference reference1=database.getReference().child("HotelDetails").child("Hotels").child(hop);
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Detail detail=snapshot.getValue(Detail.class);
                        Log.d("value", "onDataChange: "+detail.getImagepath());
                        Picasso.get().load(detail.getImagepath()).fit().into(detailimage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void displaybitmap(String passc) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(passc, BarcodeFormat.CODE_128,barcode.getWidth(),barcode.getHeight());
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
