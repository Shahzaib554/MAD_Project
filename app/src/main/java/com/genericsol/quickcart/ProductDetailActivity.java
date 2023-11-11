package com.genericsol.quickcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.genericsol.quickcart.model.ProductModel;

import java.text.NumberFormat;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Retrieve the selected product from the intent
        ProductModel selectedProduct = (ProductModel) getIntent().getSerializableExtra("selected_product");

        // Now you can access the details of the selected product and update the views in your layout
        // For example:
        TextView productNameTextView = findViewById(R.id.productNameTextView);
        TextView productPriceTextView = findViewById(R.id.productPriceTextView);
        TextView productDescriptionTextView = findViewById(R.id.productDescription);
        ImageView productImageView = findViewById(R.id.productImageView);


        productNameTextView.setText(selectedProduct.getName());
        productDescriptionTextView.setText(selectedProduct.getDescription());
        // Format the price as needed
        productPriceTextView.setText("Price: " + formatPrice(selectedProduct.getPrice()));
        Glide.with(this).load(selectedProduct.getImage()).into(productImageView);



    }

//    private String formatPrice(double price) {
//        // Format the price as needed, e.g., add currency symbol
//        return "Rs " + price; // Assuming you're using "Rs" as the currency symbol for Pakistani Rupees
//    }

    private String formatPrice(double price) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return  numberFormat.format(price) + "Rs";
    }

}