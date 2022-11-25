package com.example.myride.models;

public class serviceHistoryModel {
    String shopId;
    String shopName;
    String shopAddress;
    String serviceStatus;
    String serviceDetails;
    Integer odometer;

    public serviceHistoryModel() {
    }

    public serviceHistoryModel(String shopId, String shopName, String shopAddress, String serviceStatus, String serviceDetails, Integer odometer) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.serviceStatus = serviceStatus;
        this.serviceDetails = serviceDetails;
        this.odometer = odometer;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }
}

