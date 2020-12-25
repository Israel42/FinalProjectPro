package com.example.finalprojectpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    MeowBottomNavigation bottomNavigation;
    private final static int PERMISSION = 99;
    ApiInterface apiInterface;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkphonepermission();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(uid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    token = task.getResult().get("Token").toString();
                    Log.d("another token", "onComplete: " + token);
                }
            }
        });

        bottomNavigation = findViewById(R.id.bottom_nav);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_hotel_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_book_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_account_circle_24));
        bottomNavigation.show(3, true);
        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment fragment = null;
                switch (model.getId()) {
                    case 1:
                        fragment = new HotelsFragment();
                        break;
                    case 2:
                        fragment = new MyBookingsFragment();
                        break;
                    case 3:
                        fragment = new HomeFragment();
                        break;
                    case 4:
                        fragment = new ProfileFragment();
                        break;
                    default:
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout, fragment);
                transaction.commit();
                return null;
            }
        });

    }

    private void checkphonepermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                checkphonepermission();
            }
        }
    }
}
