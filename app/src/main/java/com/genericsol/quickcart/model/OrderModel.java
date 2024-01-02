package com.genericsol.quickcart.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import java.util.List;

public class OrderModel {

    @SerializedName("customerName")
    private String customerName;

    @SerializedName("mobileNumber")
    private String mobileNumber;

    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("paymentMethod")
    private String paymentMethod;

    @SerializedName("totalPrice")
    private double totalPrice;

    @SerializedName("products")
    private List<ProductModel> products;

    @SerializedName("createdAt")
    private Date createdAt;

    public static class ProductModel {
        @SerializedName("name")
        private String name;

        @SerializedName("quantity")
        private int quantity;

        @SerializedName("price")
        private double price;



        public ProductModel() {
        }

        public ProductModel(String name, int quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters and setters for ProductModel fields
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public OrderModel() {
    }

    // Getters and setters for OrderModel fields
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Getters and setters for the products field
    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
