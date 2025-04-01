package com.example.luxevistaresort;

public class Services {
    private String serviceID;
    private String serviceType;
    private double servicePrice;
    private String serviceDetails;
    private byte[] imageService;

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public byte[] getImageService() {
        return imageService;
    }

    public void setImageService(byte[] imageService) {
        this.imageService = imageService;
    }
}
