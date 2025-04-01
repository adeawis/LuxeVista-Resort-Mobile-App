package com.example.luxevistaresort;

public class Bookings {
    private String BookingID;
    private String roomType;
    private String checkInDate;
    private String checkOutDate;
    private String fullname;
    private String email;
    private int contNo;

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        this.BookingID = bookingID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getContNo() {
        return contNo;
    }

    public void setContNo(int contNo) {
        this.contNo = contNo;
    }
}
