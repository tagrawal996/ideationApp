package com.example.ideationapp.Model;

import java.io.Serializable;

public class userModel implements Serializable {
    private String userName;
    private String profession;
    private String userID;
    private String imageURL;
    private String bio;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    private String Address;
    private int followCount,totalHits,maxHits;

    public userModel() { }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getMaxHits() {
        return maxHits;
    }

    public void setMaxHits(int maxHits) {
        this.maxHits = maxHits;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }


    public userModel(String userName, String profession, String userID, String address, String imageURL) {
        this.userName = userName;
        this.profession = profession;
        this.userID = userID;
        this.followCount=0;
        this.totalHits=0;
        this.maxHits=0;
        this.Address=address;
        this.imageURL=imageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
