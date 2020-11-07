package com.example.finalprojectpro;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class Register extends AppCompatActivity {
    EditText firstname,lastname,Age;
    Button register;
    RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ImageView Profile;
    SweetAlertDialog sweetAlertDialog;
    Uri imageUri;
    DocumentReference documentReference;
    public static final int PICK_IMAGE=19;
    int CurrentPosition;
    String token;
    FirebaseInstanceId firebaseInstanceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        Age=findViewById(R.id.Age);
        radioGroup=findViewById(R.id.radiogroup);
        register=findViewById(R.id.register);
        Profile=findViewById(R.id.profileadd);
        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                token=task.getResult().getToken();
            }
        });
        Profile.setOnClickListener(view->{
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"SELECT_IMAGE"),PICK_IMAGE);
        });
        register.setOnClickListener(view->{
            if (imageUri!=null){
                String fname,lname,age,gender,phonenumber;
                fname=firstname.getText().toString();
                lname=lastname.getText().toString();
                age=Age.getText().toString();
                phonenumber=getIntent().getStringExtra("Phonenum");
                int selectedid=radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(selectedid);
                gender=radioButton.getText().toString();
                if (fname.length()<3&&lname.length()<3){
                    firstname.setError("PLEASE ADD YOUR FIRST NAME");
                    firstname.setFocusable(true);
                    lastname.setError("PLEASE ADD YOUR LAST NAME");
                    lastname.setFocusable(true);
                    return;
                }
                int ageInterd=Integer.parseInt(age);
                int agemax=90;
                int agemin=19;
                if (ageInterd<agemin&&ageInterd>agemax){
                    Age.setError("PLEASE ENTER YOUR AGE CORRECTLY");
                    Age.setFocusable(true);
                    return;
                }
                if (gender.isEmpty()){
                    Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
                storageReference=firebaseStorage.getReference().child("UsersImage"+System.currentTimeMillis()+"."+getFileExtension(imageUri));
                storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> task=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    if (task.isSuccessful()){
                        String imageurl;
                        imageurl=task.toString();
                        String UID;
                        UID=auth.getCurrentUser().getUid();
                        documentReference=firebaseFirestore.collection("Users").document(UID);
                        Map<String,Object> map=new HashMap<>();
                        map.put("FirstName",fname);
                        map.put("LastName",lname);
                        map.put("Age",age);
                        map.put("PhoneNumber",phonenumber);
                        map.put("Gender",gender);
                        map.put("ImageUrl",imageUri);
                        map.put("Token",token);
                        PrefManager prefManager=new PrefManager(getApplicationContext());
                        prefManager.savename(fname);
                        documentReference.set(map).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                        });
                    }
                }).addOnProgressListener(snapshot -> {
                    double progress=(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                });
            }else{
                String fname,lname,age,gender,phonenumber;
                fname=firstname.getText().toString();
                lname=lastname.getText().toString();
                age=Age.getText().toString();
                phonenumber=getIntent().getStringExtra("Phonenum");
                int selectedid=radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(selectedid);
                gender=radioButton.getText().toString();
                if (fname.length()<3&&lname.length()<3){
                    firstname.setError("PLEASE ADD YOUR FIRST NAME");
                    firstname.setFocusable(true);
                    lastname.setError("PLEASE ADD YOUR LAST NAME");
                    lastname.setFocusable(true);
                    return;
                }
                int ageInterd=Integer.parseInt(age);
                int agemax=90;
                int agemin=19;
                if (ageInterd<agemin&&ageInterd>agemax){
                    Age.setError("PLEASE ENTER YOUR AGE CORRECTLY");
                    Age.setFocusable(true);
                    return;
                }
                if (gender.isEmpty()){
                    Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
                String UID;
                UID=auth.getCurrentUser().getUid();
                documentReference=firebaseFirestore.collection("Users").document(UID);
                Map<String,Object> map=new HashMap<>();
                map.put("FirstName",fname);
                map.put("LastName",lname);
                map.put("Age",age);
                map.put("PhoneNumber",phonenumber);
                map.put("Gender",gender);
                map.put("ImageUrl",imageUri);
                map.put("Token",token);
                PrefManager prefManager=new PrefManager(getApplicationContext());
                prefManager.savename(fname);
                documentReference.set(map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                });
            }
        });
    }


    protected String getFileExtension(Uri uri){
         ContentResolver cr=getContentResolver();
         MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
         return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
     }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE&&data.getData()!=null&&data!=null){
            imageUri=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                Profile.setImageBitmap(bitmap);
                Picasso.get().load(imageUri).transform(new CropCircleTransformation()).fit().into(Profile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
