package com.example.finalprojectpro;

public class Roomtypegettersetter {
    String type,imagepath;
    int number;
    int  price;

    public Roomtypegettersetter() {
    }

    public Roomtypegettersetter(String type, String imagepath, int number, int price) {
        this.type = type;
        this.imagepath = imagepath;
        this.number = number;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}


