package com.genericsol.quickcart.model;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final CartManager instance = new CartManager();
    private List<ProductModel> cartItems = new ArrayList<>();

    private CartManager() {
        // Private constructor to prevent instantiation
    }

    public static CartManager getInstance() {
        return instance;
    }

    public List<ProductModel> getCartItems() {
        return cartItems;
    }

    public void addToCart(ProductModel product) {
        for (ProductModel item : cartItems) {
            if (item.getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }

        product.setQuantity(1);

        cartItems.add(product);
    }



}