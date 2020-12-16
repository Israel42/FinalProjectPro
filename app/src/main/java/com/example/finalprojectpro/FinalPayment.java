package com.example.finalprojectpro;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.style.TtsSpan;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FinalPayment extends AppCompatActivity implements View.OnClickListener {
    EditText checkin, checkout;
    TextView price, roomsize, generatedcode;
    Button pay;
    ImageView roompicture, cbelogo, amolelogo, birhanlogo;
    DatePickerDialog datePickerDialog;
    DatePicker datePicker;
    DatabaseReference reference;
    FirebaseDatabase database;
    Calendar calendar1, calendar2;
    String selectedhotelname, name, phone, checkinroom, checkoutroom, trans, numberofroom, tp;
    Date checkindate, checkoutdate;
    String checkTrasanction = null;
    String Key, roomtypename,hkind,hotel,rname, date1, date2, randomcode;
    static final String ab="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom random=new SecureRandom();
    long cout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_payment);
        checkout = findViewById(R.id.checkout);
        checkin = findViewById(R.id.checkin);
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
        cbelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cben="*889#";
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+cben));
                startActivity(intent);
            }
        });
        birhanlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String birn="*881#";
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+birn));
                startActivity(intent);
            }
        });
        amolelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amn="*996#";
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+amn));
                startActivity(intent);
            }
        });
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
                    datePickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 14));
                    datePickerDialog.show();
                }
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar cl = Calendar.getInstance();
                int year = cl.get(Calendar.YEAR);
                int month = cl.get(Calendar.MONTH);
                int day = cl.get(Calendar.DAY_OF_MONTH);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    datePickerDialog = new DatePickerDialog(FinalPayment.this, new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            checkout.setText(String.format("%d/%d/%d", dayOfMonth, month + 1, year));
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
                    /*
                    *it crashs because of versions isru it needs a specific version if steatement wuste asgebechelehalewu
                    *again it crashs this time is the intent pricepass intent you passed ETB to it so it doesn't change letter string to int so esun mastekakel new anyways sertual

                     */
                    datePickerDialog.getDatePicker().setMinDate(cout + (1000 * 60 * 60 * 24) );
                    datePickerDialog.getDatePicker().setMaxDate(cout + (1000 * 60 *  60 * 24 * 14));
                    datePickerDialog.show();
                }
            }
        });
        pay.setOnClickListener(this);
        roomtypename = getIntent().getStringExtra("rkindpass");
        hotel = getIntent().getStringExtra("hotelpass");
        rname=getIntent().getStringExtra("roompass");
        hkind=getIntent().getStringExtra("hkindpass");
        reference = database.getReference().child("Hoteltypes").child(hkind).child(hotel).child("Roomtypes").child(roomtypename).child(rname);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final RoomGS roomtypegettersetter = snapshot.getValue(RoomGS.class);
                Picasso.get().load(roomtypegettersetter.getImagepath()).fit().into(roompicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Calendar.Builder limitrange() {
        return null;
    }

    @Override
    public void onClick(View v) {
        checkinroom = checkin.getText().toString();
        checkoutroom = checkout.getText().toString();
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
                    Reservationdetail reservationdetail = new Reservationdetail(name, phone,hkind, hotel, roomtypename, rname, checkinroom, checkoutroom, randomcode, tp);
                    DatabaseReference databaseReference2 = database.getReference().child("Hoteltypes").child(hkind).child(hotel).child("Reserved");
                    databaseReference2.child(randomcode).setValue(reservationdetail);
                    DatabaseReference myreservation = database.getReference().child("Hoteltypes").child("OwnReservation");
                    String Uid = auth.getCurrentUser().getUid();
                    myreservation.child(Uid).push().setValue(reservationdetail);
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