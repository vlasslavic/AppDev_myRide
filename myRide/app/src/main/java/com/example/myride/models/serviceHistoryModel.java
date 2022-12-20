package com.example.myride.models;

public class serviceHistoryModel {
    String shopId;
    String shopName;
    String shopAddress;
    String nickname;
    String serviceID;
    String serviceStatus;
    String serviceDetails;
    String odometer;

    public serviceHistoryModel() {
    }

    public serviceHistoryModel(String serviceID,String nickname, String shopId, String shopName, String shopAddress, String serviceStatus, String serviceDetails, String odometer) {
        this.serviceID = serviceID;
        this.nickname = nickname;
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.serviceStatus = serviceStatus;
        this.serviceDetails = serviceDetails;
        this.odometer = odometer;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
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

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }
}

