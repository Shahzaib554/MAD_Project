package com.genericsol.quickcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.genericsol.quickcart.model.CartManager;
import com.genericsol.quickcart.model.ProductModel;

import java.text.NumberFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private CartListAdapter adapter;
    private TextView totalPriceTextView;  // Declare totalPriceTextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize totalPriceTextView
        totalPriceTextView = findViewById(R.id.totalPriceTextView);



        cartListView = findViewById(R.id.cartListView);
        adapter = new CartListAdapter(this, R.layout.cart_item, CartManager.getInstance().getCartItems());
        cartListView.setAdapter(adapter);

        // Call updateTotalPrice to initialize and display the total price
        updateTotalPrice();

        // Set click listener for the "Processed To CheckOut" button
        Button checkOutBtn = findViewById(R.id.checkOutBtn);
        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheckOutActivity
//                Intent intent = new Intent(CartDataActivity.this, CheckOutActivity.class);
//                startActivity(intent);
            }
        });

        // Set click listener for the "Cancel" button
        Button cancelBtn = findViewById(R.id.Cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Category activity
                Intent intent = new Intent(CartActivity.this, Category.class);
                startActivity(intent);
            }
        });
    }

    // Adapter class for the ListView
    private class CartListAdapter extends ArrayAdapter<ProductModel> {

        public CartListAdapter(Context context, int resource, List<ProductModel> items) {
            super(context, resource, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.cart_item, parent, false);
            }

            final ProductModel item = getItem(position);

            TextView itemNameTextView = convertView.findViewById(R.id.cartItemNameTextView);
            final TextView itemQuantityTextView = convertView.findViewById(R.id.cartItemQuantityTextView);
            TextView itemPriceTextView = convertView.findViewById(R.id.cartItemPriceTextView);
            ImageButton decreaseQuantityButton = convertView.findViewById(R.id.decreaseQuantityButton);
            ImageButton increaseQuantityButton = convertView.findViewById(R.id.increaseQuantityButton);

            if (item != null) {
                itemNameTextView.setText(item.getName());
                itemQuantityTextView.setText(String.valueOf(item.getQuantity()));
                itemPriceTextView.setText(formatPrice(item.getPrice() * item.getQuantity())); // Update item price

                // Set the image resources for decrease and increase buttons
//                decreaseQuantityButton.setImageResource(R.drawable.decr);
//                increaseQuantityButton.setImageResource(R.drawable.incr);

                // Decrease quantity button click listener
                decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = item.getQuantity();
                        if (quantity > 1) {
                            item.setQuantity(quantity - 1);
                            itemQuantityTextView.setText(String.valueOf(item.getQuantity()));
                            itemPriceTextView.setText(formatPrice(item.getPrice() * item.getQuantity())); // Update item price
                            updateTotalPrice();  // Update total price whenever quantity changes
                        } else {
                            // If quantity becomes 0, remove the item from the cart
                            remove(item);
                            notifyDataSetChanged();  // Notify adapter to refresh the list view
                            updateTotalPrice();      // Update total price after removal
                        }
                    }
                });

                // Increase quantity button click listener
                increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setQuantity(item.getQuantity() + 1);
                        itemQuantityTextView.setText(String.valueOf(item.getQuantity()));
                        itemPriceTextView.setText(formatPrice(item.getPrice() * item.getQuantity())); // Update item price
                        updateTotalPrice();  // Update total price whenever quantity changes
                    }
                });
            }

            return convertView;
        }

        private String formatPrice(double price) {
            NumberFormat numberFormat = NumberFormat.getInstance();
            return numberFormat.format(price) + " Rs";
        }
    }

    // Update total price based on the current cart items
    private void updateTotalPrice() {
        double totalPrice = 0;
        for (ProductModel item : CartManager.getInstance().getCartItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        // Display total price in the totalPriceTextView
        if (totalPriceTextView != null) {
            totalPriceTextView.setText("Total Price: " + formatPrice(totalPrice));
        }

        // If the cart is empty, set the total price to zero
        if (CartManager.getInstance().getCartItems().isEmpty() && totalPriceTextView != null) {
            totalPriceTextView.setText("Total Price: " + formatPrice(0));
        }
    }

    private String formatPrice(double price) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(price) + " Rs";
    }
}