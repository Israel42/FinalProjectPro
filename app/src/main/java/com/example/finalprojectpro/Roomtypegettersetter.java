package com.example.finalprojectpro;

public class Roomtypegettersetter {
    String availabelrooms,roomtypename, roompictureuri;
    int  price;

    public Roomtypegettersetter() {
    }

    public Roomtypegettersetter(String availabelrooms, String roomtypename, String roompictureuri, int price) {
        this.availabelrooms = availabelrooms;
        this.roomtypename = roomtypename;
        this.roompictureuri = roompictureuri;
        this.price = price;
    }

    public String getAvailabelrooms() {
        return availabelrooms;
    }

    public void setAvailabelrooms(String availabelrooms) {
        this.availabelrooms = availabelrooms;
    }

    public String getRoomtypename() {
        return roomtypename;
    }

    public void setRoomtypename(String roomtypename) {
        this.roomtypename = roomtypename;
    }

    public String getRoompictureuri() {
        return roompictureuri;
    }

    public void setRoompictureuri(String roompictureuri) {
        this.roompictureuri = roompictureuri;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}


