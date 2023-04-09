package com.example.apporderfood.model;

public class Food {
    private int image;
    private int money;
    private int quantity;
    private String foodName;

    public Food() {

    }

    public Food(int image, int money, int quantity, String foodName) {
        this.image = image;
        this.money = money;
        this.quantity = quantity;
        this.foodName = foodName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
