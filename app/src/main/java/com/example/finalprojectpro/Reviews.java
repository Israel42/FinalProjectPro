package com.example.finalprojectpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Reviews extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Review> reviewList=new ArrayList<>();
    ReviewsAdapter reviewsAdapter;
    String hotelname,hkind;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    private DocumentSnapshot documentSnapshot;
    EditText userreview;
    ImageView submit;
    RatingBar userhotelrate;
   float rate;
     String reviews,reviewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        userreview=findViewById(R.id.userreview);
        userhotelrate=findViewById(R.id.userhotelrate);
        submit=findViewById(R.id.submit);
        hotelname=getIntent().getStringExtra("hotelpass");
        hkind=getIntent().getStringExtra("hkindpass");
        database=FirebaseDatabase.getInstance();
        recyclerView=findViewById(R.id.reviewrecycler);
        reviewsAdapter=new ReviewsAdapter(reviewList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();
        reference=database.getReference().child("Hoteltypes").child(hkind).child(hotelname).child("Reviews");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviewList.clear();
                if(snapshot.hasChildren()){
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Log.d("Values", "onDataChange: "+dataSnapshot.getValue().toString());
                    Review review=dataSnapshot.getValue(Review.class);
                    reviewList.add(review);

                }
                    recyclerView.setAdapter(reviewsAdapter);
                    reviewsAdapter.notifyDataSetChanged();

            }else{
                    Toast.makeText(Reviews.this, "No reviews yet! Feel free to add.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        auth=FirebaseAuth.getInstance();
        String id=auth.getCurrentUser().getUid();
        final DocumentReference documentReference= FirebaseFirestore.getInstance().collection("Users").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            documentSnapshot=task.getResult();
            reviewer=String.format("%s %s",documentSnapshot.get("FirstName").toString(),documentSnapshot.get("LastName").toString());
            }
        });
        String date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userreview!=null && userhotelrate!=null){
                    rate=userhotelrate.getRating();
                    reviews=userreview.getText().toString();
                    Review review=new Review(reviewer,reviews,date,rate);
                    reference.push().setValue(review);

                }
            }
        });
    }
}