package com.example.finalprojectpro;

import android.widget.RatingBar;

public class Hoteldetail {
  String name;
    String imagepath;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Hoteldetail(String note) {
        this.note = note;
    }

    String note;
  float rating;
  int price;

    public Hoteldetail() {
    }

    public Hoteldetail(String name, String imagepath, float rating, int price) {
        this.name = name;
        this.imagepath = imagepath;
        this.rating = rating;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
