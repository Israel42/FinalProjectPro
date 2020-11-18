package com.example.finalprojectpro;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skyfishjy.library.RippleBackground;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, View.OnClickListener {
    GoogleMap map;
    LatLng mOrigin;
    LazyLoader lazyLoader;
    LinearLayout linearLayout;
    Location location;
    LocationManager manager;
    Button find,search;
    RippleBackground rippleBackground;
    GeoLocation userLocation;
    Marker m;
    MarkerOptions markerOptions=new MarkerOptions();
    List<Hoteldetail> hoteldetailList=new ArrayList<>();
    Hoteldetail hoteldetail;
    private ApiInterface apiInterface;
    FloatingActionButton findmylocation;
    CardView cardView;
    TextView hotelnameview;
    Button checkAvailability;
    ViewFlipper viewFlipper;
    ImageView hotelimage,singleroomimage,doubleroomimage,suitroomimage;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        if (supportMapFragment != null){
            supportMapFragment.getMapAsync(this);
        }
        rippleBackground=findViewById(R.id.ripple);
        lazyLoader=findViewById(R.id.lazydots);
        linearLayout=findViewById(R.id.linearmap);
        search=findViewById(R.id.searchbutton);
        search.setOnClickListener(this);
        findmylocation=findViewById(R.id.mylocation);
        hotelnameview=findViewById(R.id.hotelnameview);
        ratingBar=findViewById(R.id.ratingBarhotel2);
        hotelimage=findViewById(R.id.hotelimageview);
        cardView=findViewById(R.id.cardviewshow);
        checkAvailability=findViewById(R.id.checkAvailability);
        viewFlipper=findViewById(R.id.viewfliper);
        singleroomimage=findViewById(R.id.singleroomimage);
        doubleroomimage=findViewById(R.id.doubleroomimage);
        suitroomimage=findViewById(R.id.suitroomimage);
        findmylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location!=null){
                    onLocationChanged(location);
                }
            }
        });
        lazyLoader=new LazyLoader(this,30,20,ContextCompat.getColor(this,R.color.loader_selected),
                ContextCompat.getColor(this,R.color.loader_selected),
                ContextCompat.getColor(this,R.color.loader_selected));
        lazyLoader.setAnimDuration(500);
        lazyLoader.setFirstDelayDuration(100);
        lazyLoader.setSecondDelayDuration(200);
        lazyLoader.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        manager=(LocationManager) getSystemService(LOCATION_SERVICE);
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.mapstyple));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        if (location!=null){
            onLocationChanged(location);
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,50000,50,this);
        map.moveCamera(CameraUpdateFactory.newCameraPosition(location));
        CustomInfoWindow infoWindow=new CustomInfoWindow(getApplicationContext());
        map.setInfoWindowAdapter(infoWindow);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        mOrigin=new LatLng(location.getLatitude(),location.getLongitude());
        userLocation=new GeoLocation(location.getLatitude(),location.getLongitude());
        Log.d("Location:         ","UserLocation              "+mOrigin);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mOrigin)
                .zoom(5)
                .bearing(location.getBearing())
                .tilt(90)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onClick(View view) {
        rippleBackground.startRippleAnimation();
        linearLayout.setVisibility(View.VISIBLE);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("HotelDetails").child("HotelLocation");
        final GeoFire geoFire=new GeoFire(reference);
        final GeoQuery geoQuery=geoFire.queryAtLocation(userLocation,5);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                final DatabaseReference  databaseReference=FirebaseDatabase.getInstance().getReference().child("HotelDetails").child("Hotels").child(key);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Hoteldetail detail=snapshot.getValue(Hoteldetail.class);
                        LatLng latLng=new LatLng(location.latitude,location.longitude);
                        markerOptions.icon(bitmapDescriptor(getApplicationContext(),R.drawable.locationhotel)).position(latLng);
                        Marker marker1=map.addMarker(markerOptions);
                        rippleBackground.stopRippleAnimation();
                        linearLayout.setVisibility(View.INVISIBLE);
                        marker1.setTag(detail);
                        marker1.getPosition();
                        marker1.showInfoWindow();
                        map.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
                        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                seeinfoWindow(marker);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }
    private void seeinfoWindow(Marker marker) {
        cardView.setVisibility(View.VISIBLE);
        String hotelname = marker.getTitle();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("HotelDetails").child("Hotels").child(hotelname);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Hoteldetail hotelDetail = snapshot.getValue(Hoteldetail.class);
                hotelnameview.setText(hotelDetail.getName());
                ratingBar.setRating(hotelDetail.getRating());
                Picasso.get().load(hotelDetail.getImagepath()).fit().into(hotelimage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private BitmapDescriptor bitmapDescriptor(Context context,int vectorid){
        Drawable drawable=ContextCompat.getDrawable(context,vectorid);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        Bitmap bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
        }

        public void SendNearByNotification(String to,String title,String body){
                DataModel dataModel=new DataModel(title,body);
                RequestNotification requestNotification=new RequestNotification(to,dataModel);
                apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
            Call<ResponseBody> responseBodyCall=apiInterface.SendNearByNotification(requestNotification);
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code()==200){
                        try{
                            Log.d("Response", "onResponse: "+response.body().string());
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        Log.d("Response", "onResponse: "+response.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
    }
}