package com.example.finalprojectpro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.view.View;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FinalPayment extends AppCompatActivity implements View.OnClickListener {
    EditText checkin, checkout;
    TextView price, roomsize, generatedcode;
    Button plus, minus, pay;
    ImageView roompicture, cbelogo, amolelogo, birhanlogo;
    DatePickerDialog datePickerDialog;
    DatePicker datePicker;
    DatabaseReference reference;
    FirebaseDatabase database;
    int hotelroomsize, currentprice;
    Calendar calendar1, calendar2;
    String selectedhotelname, name, phone, checkinroom, checkoutroom, trans, numberofroom, tp;
    Date checkindate, checkoutdate;
    String checkTrasanction = null;
    String Key, roomtypename, date1, date2, randomcode;
    static final String ab="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom random=new SecureRandom();
    long cout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_payment);
        checkout = findViewById(R.id.checkout);
        checkin = findViewById(R.id.checkin);
        roomsize = findViewById(R.id.numberofrooms);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        generatedcode = findViewById(R.id.generated);
        cbelogo = findViewById(R.id.cbelogo);
        birhanlogo = findViewById(R.id.birhanlogo);
        amolelogo = findViewById(R.id.amolelogo);
        pay = findViewById(R.id.Pay);
        roompicture = findViewById(R.id.imageofroom);
        price = findViewById(R.id.totalsum);
        price.invalidate();
        database = FirebaseDatabase.getInstance();
        checkin.setInputType(InputType.TYPE_NULL);
        checkout.setInputType(InputType.TYPE_NULL);
        String g=randomString(8);
        generatedcode.setText(g);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    datePickerDialog = new DatePickerDialog(FinalPayment.this, new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            checkin.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                            date1 = checkin.getText().toString();
                            price.invalidate();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                checkindate = simpleDateFormat.parse(checkin.getText().toString());
                                calendar1 = new GregorianCalendar();
                                calendar1.setTime(checkindate);
                                cout = calendar1.getTimeInMillis();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    }, year, month, day);
                    long now = System.currentTimeMillis();
                    datePickerDialog.getDatePicker().setMinDate(now);
                    datePickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 7));
                    datePickerDialog.show();
                }
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(FinalPayment.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        checkout.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                        date2 = checkout.getText().toString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            checkoutdate = simpleDateFormat.parse(checkout.getText().toString());
                            calendar2 = new GregorianCalendar();
                            calendar2.setTime(checkoutdate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int valueend = getdaybetween(calendar1.getTime(), calendar2.getTime());
                        int piceof = Integer.parseInt(getIntent().getStringExtra("pricepass"));
                        int finalprice = valueend * piceof;
                        price.setText(String.valueOf(finalprice) + "ETB");
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(cout + (1000 * 60 * 60 * 24));
                datePickerDialog.getDatePicker().setMaxDate(cout + (1000 * 60 * 60 * 24 * 7));
                datePickerDialog.show();
            }
        });
        pay.setOnClickListener(this);
        roomtypename = getIntent().getStringExtra("roompass");
        final String hotel = getIntent().getStringExtra("hotelpass");
        reference = database.getReference().child("HotelDetails").child("Hotels").child(hotel).child("RoomTypes").child(roomtypename);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Roomtypegettersetter roomtypegettersetter = snapshot.getValue(Roomtypegettersetter.class);
                Picasso.get().load(roomtypegettersetter.getImagepath()).fit().into(roompicture);
                final int roomsizehotel = roomtypegettersetter.getNumber();
                hotelroomsize = Integer.parseInt(roomsize.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentprice != 0) {
                            currentprice = getdaybetween(calendar1.getTime(), calendar2.getTime());
                            int priceofroom = roomtypegettersetter.getPrice();
                            int priceperday = priceofroom * currentprice;
                            price.setText(String.valueOf(priceperday) + "ETB");
                        }
                    }
                });
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (hotelroomsize != roomsizehotel) {
                            hotelroomsize++;
                            int valueof = getdaybetween(calendar1.getTime(), calendar2.getTime());
                            roomsize.setText(String.valueOf(hotelroomsize));
                            int priceperroom = roomtypegettersetter.getPrice();
                            int priceroom = Integer.parseInt(roomsize.getText().toString());
                            int finalprice = priceperroom * priceroom * valueof;
                            price.setText(String.valueOf(finalprice) + "ETB");
                        } else {
                            Toast.makeText(FinalPayment.this, "Maximum available rooms", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (hotelroomsize != 1) {
                            hotelroomsize--;
                            int priceof = getdaybetween(calendar1.getTime(), calendar2.getTime());
                            roomsize.setText(String.valueOf(hotelroomsize));
                            int priceperroom = roomtypegettersetter.getPrice();
                            int priceroom = Integer.parseInt(roomsize.getText().toString());
                            int finalprice = priceperroom * priceroom * priceof;
                            price.setText(String.valueOf(finalprice) + "ETB");
                        } else {
                            Toast.makeText(FinalPayment.this, "Minimum number of room", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        checkinroom = checkin.getText().toString();
        checkoutroom = checkout.getText().toString();
        numberofroom = roomsize.getText().toString();
        if (checkinroom.isEmpty()) {
            checkin.setError("Please add check in date");
            checkin.setFocusable(true);
            return;
        }
        if (checkoutroom.isEmpty()) {
            checkout.setError("Please add check out date");
            checkout.setFocusable(true);
            return;
        }
        selectedhotelname = getIntent().getStringExtra("hotelpass");
        roomtypename = getIntent().getStringExtra("roompass");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String UID = auth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(UID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    name = documentSnapshot.get("FirstName").toString() + " " + documentSnapshot.get("LastName").toString();
                    phone = documentSnapshot.get("PhoneNumber").toString();
                    checkinroom = checkin.getText().toString();
                    checkoutroom = checkout.getText().toString();
                    randomcode=generatedcode.getText().toString();
                    tp = price.getText().toString();
                    numberofroom = roomsize.getText().toString();
                    Reservationdetail reservationdetail = new Reservationdetail(name, phone, selectedhotelname, roomtypename, numberofroom, checkinroom, checkoutroom, randomcode, tp);
                    DatabaseReference databaseReference2 = database.getReference().child("HotelDetails").child("Hotels").child(selectedhotelname).child("Reserved");
                    databaseReference2.child(randomcode).setValue(reservationdetail);
                    DatabaseReference myreservation = database.getReference().child("HotelDetails").child("MyReservation");
                    String Uid = auth.getCurrentUser().getUid();
                    myreservation.child(Uid).child(randomcode).setValue(reservationdetail);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    public int getdaybetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
    public String randomString(int len){
        StringBuilder stringBuilder=new StringBuilder(len);
        for (int i=0;i<len;i++) {
            stringBuilder.append(ab.charAt(random.nextInt(ab.length())));
        }
            return stringBuilder.toString();
    }

}