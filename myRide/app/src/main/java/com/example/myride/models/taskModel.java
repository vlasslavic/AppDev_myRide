package com.example.myride.models;

public class taskModel {
    String task;
    String serviceLocation;
    String company;

    public taskModel() {
    }

    public taskModel(String task, String serviceLocation, String company) {
        this.task = task;
        this.serviceLocation = serviceLocation;
        this.company = company;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "taskModel{" +
                "task='" + task + '\'' +
                ", serviceLocation='" + serviceLocation + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
