package com.example.finalprojectpro;

import android.widget.RatingBar;

public class Hoteldetail {
  String name;
  String kind;
    String imagepath;
    String note;
    String price;
  float rating;


    public Hoteldetail() {
    }

    public Hoteldetail(String name, String kind, String imagepath, String note, String price, float rating) {
        this.name = name;
        this.kind = kind;
        this.imagepath = imagepath;
        this.note = note;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
