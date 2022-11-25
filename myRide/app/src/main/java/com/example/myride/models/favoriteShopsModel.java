package com.example.myride.models;

public class favoriteShopsModel {

    String shopID;
    String shopAddress;
    String shopName;

    public favoriteShopsModel(){}

    public favoriteShopsModel(String shopID, String shopAddress, String shopName) {
        this.shopID = shopID;
        this.shopAddress = shopAddress;
        this.shopName = shopName;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
