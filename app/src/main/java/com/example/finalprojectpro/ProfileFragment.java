package com.example.finalprojectpro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {
    TextView nameview,phoneview;
    ImageView profilepic;

    Button logout,englishlang,amhariclang;
    FirebaseAuth auth;
    private DocumentSnapshot documentSnapshot;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameview=view.findViewById(R.id.nameview);
        phoneview=view.findViewById(R.id.phonview);
        profilepic=view.findViewById(R.id.pro_pic);
        logout=view.findViewById(R.id.logout);
        englishlang=view.findViewById(R.id.englishlang);
        amhariclang=view.findViewById(R.id.amhariclang);
        nameview.setClickable(false);
        nameview.setInputType(InputType.TYPE_NULL);
        phoneview.setClickable(false);
        phoneview.setInputType(InputType.TYPE_NULL);
        auth=FirebaseAuth.getInstance();
        String userid=auth.getCurrentUser().getUid();
        final DocumentReference documentReference= FirebaseFirestore.getInstance().collection("Users").document(userid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    nameview.setText(String.format("%s %s", documentSnapshot.get("FirstName").toString(), documentSnapshot.get("LastName").toString()));
                    phoneview.setText(documentSnapshot.get("PhoneNumber").toString());
                }
                 //   Picasso.get().load(documentSnapshot.get("ImageUrl").toString()).transform(new CropCircleTransformation()).fit().into(profilepic);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getContext(),LoginPage.class));
            }
        });
        englishlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManager prefManager=new PrefManager(getContext());
                String language="en";
                prefManager.updatelanguage(language);
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });
        amhariclang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManager prefManager=new PrefManager(getContext());
                String language1="aa";
                prefManager.updatelanguage(language1);
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });
    }

}