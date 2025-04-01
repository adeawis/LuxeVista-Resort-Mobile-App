package com.example.luxevistaresort;

public class Reservations {
    private String reservationID;
    private String serviceType;
    private String fullnameRes;
    private String resDate;
    private String resTime;

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getFullnameRes() {
        return fullnameRes;
    }

    public void setFullnameRes(String fullnameRes) {
        this.fullnameRes = fullnameRes;
    }

    public String getResDate() {
        return resDate;
    }

    public void setResDate(String resDate) {
        this.resDate = resDate;
    }

    public String getResTime() {
        return resTime;
    }

    public void setResTime(String resTime) {
        this.resTime = resTime;
    }
}
