package com.genericsol.quickcart;

public class Product {
    private int imgResource;
    private String title;
    private String price;

    public Product(int imgResource, String title, String price) {
        this.imgResource = imgResource;
        this.title = title;
        this.price = price;
    }

    public int getImageResource() {
        return imgResource;
    }
    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public void setImageResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}