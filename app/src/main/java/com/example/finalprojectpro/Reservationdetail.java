package com.example.finalprojectpro;

public class Reservationdetail {
    String reserver;
    String reserverphoneNumber;
    String reservedhotel;
    String reservedroomtype;
    String reservednoofroom;
    String bookingdate;
    String paybill;

    public Reservationdetail(String reserver, String reserverphoneNumber, String reservedhotel, String reservedroomtype, String reservednoofroom, String bookingdate, String paybill) {
        this.reserver = reserver;
        this.reserverphoneNumber = reserverphoneNumber;
        this.reservedhotel = reservedhotel;
        this.reservedroomtype = reservedroomtype;
        this.reservednoofroom = reservednoofroom;
        this.bookingdate = bookingdate;
        this.paybill = paybill;
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

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getPaybill() {
        return paybill;
    }

    public void setPaybill(String paybill) {
        this.paybill = paybill;
    }
}
