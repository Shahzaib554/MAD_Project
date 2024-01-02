package com.genericsol.quickcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genericsol.quickcart.R;
import com.genericsol.quickcart.model.ProductModel;

import java.text.NumberFormat;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    private List<ProductModel> productList;
    private Context context;

    public CheckoutAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = productList.get(position);

        // Bind data to the ViewHolder
        holder.itemNameTextView.setText("Product Name: "+product.getName());
        holder.itemQuantityTextView.setText("Quantity: "+String.valueOf(product.getQuantity()));
        holder.itemPriceTextView.setText("Price: " + formatPrice(product.getPrice() * product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private String formatPrice(double price) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(price) + " Rs";
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemQuantityTextView;
        TextView itemPriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.checkoutItemNameTextView);
            itemQuantityTextView = itemView.findViewById(R.id.checkoutItemQuantityTextView);
            itemPriceTextView = itemView.findViewById(R.id.checkoutItemPriceTextView);
        }
    }
}
