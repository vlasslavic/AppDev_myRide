package com.example.myride.models;

public class garageModel {
    private  String make;
    private  String model;
    private  String registration;
    private  String color;
    private  String year;
    private  String picture;
    private  String nickname;
    private  serviceHistoryModel serviceHistory;

    public garageModel(){}

    public garageModel(String make, String model, String year, String nickname, String registration, String color, String picture, serviceHistoryModel serviceHistory) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.nickname =nickname;
        this.registration = registration;
        this.color = color;
        this.picture = picture;
        this.serviceHistory = serviceHistory;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMake() {

        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public serviceHistoryModel getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(serviceHistoryModel serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

    public String getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "garageModel{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", registration='" + registration + '\'' +
                ", color='" + color + '\'' +
                ", year='" + year + '\'' +
                ", picture='" + picture + '\'' +
                ", nickname='" + nickname + '\'' +
                ", serviceHistory=" + serviceHistory +
                '}';
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
