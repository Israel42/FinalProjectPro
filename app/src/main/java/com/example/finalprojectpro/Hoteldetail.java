package com.example.finalprojectpro;

import android.widget.RatingBar;

public class Hoteldetail {
    String hotelName;
    String hotelImageurl;
    float rating;
    String direction;

    public Hoteldetail(String hotelName, String hotelImageurl, float rating, String direction) {
        this.hotelName = hotelName;
        this.hotelImageurl = hotelImageurl;
        this.rating = rating;
        this.direction = direction;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelImageurl() {
        return hotelImageurl;
    }

    public void setHotelImageurl(String hotelImageurl) {
        this.hotelImageurl = hotelImageurl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
