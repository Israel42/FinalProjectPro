package com.example.finalprojectpro;

public class RoomGS {
    String imagepath;
    String number;
    int price;

    public RoomGS() {
    }

    public RoomGS(String imagepath, String number, int price) {
        this.imagepath = imagepath;
        this.number = number;
        this.price = price;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}