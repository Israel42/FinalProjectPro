package com.example.finalprojectpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
  MeowBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation=findViewById(R.id.bottom_nav);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_hotel_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_book_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_account_circle_24));
        bottomNavigation.show(3,true);
        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment fragment=null;
                switch (model.getId()){
                    case 1:
                        fragment=new HotelsFragment();
                        break;
                    case 2:
                        fragment=new MyBookingsFragment();
                        break;
                    case 3:
                        fragment=new HomeFragment();
                        break;
                    case 4:
                        fragment=new ProfileFragment();
                        break;
                    default:
                }
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout,fragment);
                transaction.commit();
                return null;
            }
        });

    }
   // public checkAvailability(Date checkin,Date checkout){
    //    return true;}
}