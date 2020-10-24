package com.example.finalprojectpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation=findViewById(R.id.bottom_nav);
        navigation.show(1,true);
        navigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_hotel_24));
        navigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_bookmark_24));
        navigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_location_on_24));
        navigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_account_circle_24));
        navigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                int icon=model.getId();
                Fragment fragment=null;
                switch (icon){
                    case 1:
                        fragment =new HotelsFragment();
                        break;
                    case 2:
                        fragment=new MyBookingsFragment();
                        break;
                    case 3:
                        fragment=new LocationFragment();
                        break;
                    case 4:
                        fragment=new ProfileFragment();
                        break;
                    default:
                }
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,fragment);
                fragmentTransaction.commit();
                return null;
            }
        });
    }
}