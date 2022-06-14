package com.example.myproject;

public class ModelUsers {
    private String userId;
    private String userName;
    private String userPw;
    private String userType;
    private Double userLatitude;
    private Double userLongitude;
    private String storeId;
    private String storeName;

    public ModelUsers (String userId, String userName, String userPw, String userType, Double userLatitude, Double userLongitude, String storeId, String StoreName){
        this.userId = userId;
        this.userName = userName;
        this.userPw = userPw;
        this.userType = userType;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.storeId = storeId;
        this.storeName = StoreName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(Double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public Double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(Double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }





}
