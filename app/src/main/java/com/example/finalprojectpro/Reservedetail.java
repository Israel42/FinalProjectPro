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
import com.squareup.picasso.Picasso;

public class Reservedetail extends AppCompatActivity {
    ImageView detailimage,barcode;
    TextView detailname,detailroom,checkindetail,checkoutdetail,code;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    Hoteldetail hoteldetail;
    Reservationdetail reservationdetail;
    String passc,hop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservedetail);
        detailimage=findViewById(R.id.reservedhoteldetailimage);
        barcode=findViewById(R.id.barcodeview);
        Log.d("handw", "onCreate: "+barcode.getWidth()+"    "+barcode.getHeight());
        detailname=findViewById(R.id.reservedhoteldetailname);
        detailroom=findViewById(R.id.reservedroomdetailname);
        checkindetail=findViewById(R.id.detailcheckin);
        checkoutdetail=findViewById(R.id.detailcheckout);
        code=findViewById(R.id.bookcode);
        passc=getIntent().getStringExtra("codepass");
        code.setText(passc);
        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(passc,BarcodeFormat.CODE_128, barcode.getWidth(), barcode.getHeight());
            Bitmap bitmap = Bitmap.createBitmap(barcode.getWidth(), barcode.getHeight(), Bitmap.Config.RGB_565);
            for (int i = 0; i < barcode.getWidth(); i++) {
                for (int j = 0; j < barcode.getHeight(); j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.WHITE);

                }
            }
            barcode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
        auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid();
        reference=database.getReference().child("HotelDetails").child("Hotels").child("MyReservation").child(uid).child(passc);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservationdetail=snapshot.getValue(Reservationdetail.class);
                hop=reservationdetail.getReservedhotel().toString();
                detailname.setText(hop);
                detailroom.setText(String.valueOf(reservationdetail.getReservedroomtype()));
                checkindetail.setText(String.valueOf(reservationdetail.getIndate()));
                checkoutdetail.setText(String.valueOf(reservationdetail.getOutdate()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference reference1=database.getReference().child("HotelDetails").child("Hotels").child(hop);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoteldetail=snapshot.getValue(Hoteldetail.class);
                Picasso.get().load(hoteldetail.getImagepath()).fit().into(detailimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}