package com.example.myride.models;

public class garageModel {
    String make;
    String model;
    String nickname;
    String trim;
    String year;
    serviceHistoryModel serviceHistory;

    public garageModel(){}

    public garageModel(String make, String model, String nickname, String trim, String year, serviceHistoryModel serviceHistory) {
        this.make = make;
        this.model = model;
        this.nickname = nickname;
        this.trim = trim;
        this.year = year;
        this.serviceHistory = serviceHistory;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
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
}
