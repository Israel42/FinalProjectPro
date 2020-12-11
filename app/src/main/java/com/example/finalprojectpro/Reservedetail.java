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
    TextView detailname,detailroom,checkindetail,checkoutdetail,code,rtype;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    Reservationdetail reservationdetail;
    String passc,hop , hkind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservedetail);
        detailimage=findViewById(R.id.reservedhoteldetailimage);
        barcode=findViewById(R.id.barcodeview);
        detailname=findViewById(R.id.reservedhoteldetailname);
        detailroom=findViewById(R.id.reservedroomdetailname);
        checkindetail=findViewById(R.id.detailcheckin);
        checkoutdetail=findViewById(R.id.detailcheckout);
        code=findViewById(R.id.bookcode);
        rtype.findViewById(R.id.reservedroomtype);
        passc=getIntent().getStringExtra("codepass");
        code.setText(passc);
       database=FirebaseDatabase.getInstance();
        displaybitmap(passc);
        auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid();
        reference=database.getReference().child("Hoteltypes").child("OwnReservation").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservationdetail=snapshot.getValue(Reservationdetail.class);
                hop=reservationdetail.getReservedhotel();
                detailname.setText(hop);
                hkind=reservationdetail.getReservedhkind();
                detailroom.setText(String.valueOf(reservationdetail.getReservednoofroom()));
                rtype.setText(String.valueOf(reservationdetail.getReservedroomtype()));
                checkindetail.setText(String.valueOf(reservationdetail.getIndate()));
                checkoutdetail.setText(String.valueOf(reservationdetail.getOutdate()));
                DatabaseReference reference1=database.getReference().child("Hoteltypes").child(hkind).child(hop);
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Detail detail=snapshot.getValue(Detail.class);
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