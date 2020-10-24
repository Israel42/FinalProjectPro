package com.example.finalprojectpro;

public class Roomtypegettersetter {
    String roomtypename,roompictureuri;
    int availabelrooms;

    public Roomtypegettersetter(String roomtypename, String roompictureuri, int availabelrooms) {
        this.roomtypename = roomtypename;
        this.roompictureuri = roompictureuri;
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

    public int getAvailabelrooms() {
        return availabelrooms;
    }

    public void setAvailabelrooms(int availabelrooms) {
        this.availabelrooms = availabelrooms;
    }
}
