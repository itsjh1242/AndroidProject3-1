package com.example.myproject;

public class ModelItems {
    private int itemId;
    private String itemName;
    private int itemPrice;
    private int itemDiscountPrice;
    private int itemExpirationDate;
    private String itemDeliveryDate;
    private String storeId;

    public ModelItems (int itemId, String itemName, int itemPrice, int itemDiscountPrice, int itemExpirationDate, String itemDeliveryDate, String storeId){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDiscountPrice = itemDiscountPrice;
        this.itemExpirationDate = itemExpirationDate;
        this.itemDeliveryDate = itemDeliveryDate;
        this.storeId = storeId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemDiscountPrice() {
        return itemDiscountPrice;
    }

    public void setItemDiscountPrice(int itemDiscountPrice) {
        this.itemDiscountPrice = itemDiscountPrice;
    }

    public int getItemExpirationDate() {
        return itemExpirationDate;
    }

    public void setItemExpirationDate(int itemExpirationDate) {
        this.itemExpirationDate = itemExpirationDate;
    }

    public String getItemDeliveryDate() {
        return itemDeliveryDate;
    }

    public void setItemDeliveryDate(String itemDeliveryDate) {
        this.itemDeliveryDate = itemDeliveryDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
