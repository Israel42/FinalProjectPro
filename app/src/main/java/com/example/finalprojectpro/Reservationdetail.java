package com.example.finalprojectpro;

public class Reservationdetail {
    String reserver;
    String reserverphoneNumber;
    String reservedhkind;
    String reservedhotel;
    String reservedroomtype;
    String reservednoofroom;
    String indate;
    String outdate;
    String generatedcode;
    String totalprice;

    public Reservationdetail() {
    }

    public Reservationdetail(String reserver, String reserverphoneNumber, String reservedhkind, String reservedhotel, String reservedroomtype, String reservednoofroom, String indate, String outdate, String generatedcode, String totalprice) {
        this.reserver = reserver;
        this.reserverphoneNumber = reserverphoneNumber;
        this.reservedhkind = reservedhkind;
        this.reservedhotel = reservedhotel;
        this.reservedroomtype = reservedroomtype;
        this.reservednoofroom = reservednoofroom;
        this.indate = indate;
        this.outdate = outdate;
        this.generatedcode = generatedcode;
        this.totalprice = totalprice;
    }

    public String getReserver() {
        return reserver;
    }

    public void setReserver(String reserver) {
        this.reserver = reserver;
    }

    public String getReserverphoneNumber() {
        return reserverphoneNumber;
    }

    public void setReserverphoneNumber(String reserverphoneNumber) {
        this.reserverphoneNumber = reserverphoneNumber;
    }

    public String getReservedhkind() {
        return reservedhkind;
    }

    public void setReservedhkind(String reservedhkind) {
        this.reservedhkind = reservedhkind;
    }

    public String getReservedhotel() {
        return reservedhotel;
    }

    public void setReservedhotel(String reservedhotel) {
        this.reservedhotel = reservedhotel;
    }

    public String getReservedroomtype() {
        return reservedroomtype;
    }

    public void setReservedroomtype(String reservedroomtype) {
        this.reservedroomtype = reservedroomtype;
    }

    public String getReservednoofroom() {
        return reservednoofroom;
    }

    public void setReservednoofroom(String reservednoofroom) {
        this.reservednoofroom = reservednoofroom;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getOutdate() {
        return outdate;
    }

    public void setOutdate(String outdate) {
        this.outdate = outdate;
    }

    public String getGeneratedcode() {
        return generatedcode;
    }

    public void setGeneratedcode(String generatedcode) {
        this.generatedcode = generatedcode;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
