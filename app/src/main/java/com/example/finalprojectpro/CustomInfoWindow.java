package com.example.finalprojectpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    Hoteldetail hoteldetail;
    RoomGS roomtypegettersetter;
    LayoutInflater layoutInflater;
    Context context;

    public CustomInfoWindow(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mview=layoutInflater.inflate(R.layout.custom_infowindow,null);
        hoteldetail=(Hoteldetail)marker.getTag();
        String hotelname;
        TextView price=mview.findViewById(R.id.textprice);
        price.setText(String.format("Min:%sETB",hoteldetail.getPrice()));
        hotelname=hoteldetail.getName();
        marker.setTitle(hotelname);
        return mview;
    }
}
