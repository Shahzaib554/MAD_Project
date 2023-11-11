package com.genericsol.quickcart.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductModel implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("category")
    private String category;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private int price;

    @SerializedName("brand")
    private String brand;

    @SerializedName("__v")
    private int v;

    // Constructors, getters, and setters

    // Constructors

    public ProductModel(String id, String category, String name, String image, String description, int price, String brand, int v) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.v = v;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public int getV() {
        return v;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setV(int v) {
        this.v = v;
    }
}
