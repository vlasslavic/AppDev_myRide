package com.example.myride.models;

import com.google.firebase.database.ValueEventListener;

public class userModel {

    String email;
    String fullName;
    String mainCar;
    String profilePicUrl;
    favoriteShopsModel favoriteShops;
    garageModel myGarage;

    public userModel() {
    }

    public userModel(String email, String fullName, String mainCar, String profilePicUrl, favoriteShopsModel favoriteShops, garageModel myGarage) {
        this.email = email;
        this.fullName = fullName;
        this.mainCar = mainCar;
        this.profilePicUrl = profilePicUrl;
        this.favoriteShops = favoriteShops;
        this.myGarage = myGarage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMainCar() {
        return mainCar;
    }

    public void setMainCar(String mainCar) {
        this.mainCar = mainCar;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public favoriteShopsModel getFavoriteShops() {
        return favoriteShops;
    }

    public void setFavoriteShops(favoriteShopsModel favoriteShops) {
        this.favoriteShops = favoriteShops;
    }

    public garageModel getMyGarage() {
        return myGarage;
    }

    public void setMyGarage(garageModel myGarage) {
        this.myGarage = myGarage;
    }


}