package com.genericsol.quickcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.genericsol.quickcart.R;
import com.genericsol.quickcart.model.ProductModel;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductModel> products;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ProductModel product);
    }

    public ProductAdapter(Context context, List<ProductModel> products, OnItemClickListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = products.get(position);
        holder.productNameTextView.setText(product.getName());

        // Simply convert the product price to a string
        String formattedPrice = String.valueOf(product.getPrice());

        // Remove decimal point if it has .00
//        formattedPrice = formattedPrice.replaceAll("\\.00", "");

        NumberFormat numberFormat = NumberFormat.getInstance();
        formattedPrice = numberFormat.format(product.getPrice()); // Corrected line

        holder.productPriceTextView.setText("Price: " + formattedPrice+ "Rs");
        // Load product image using Glide
        Glide.with(context)
                .load(product.getImage())
                .into(holder.productImageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(product); // Trigger the item click listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView productPriceTextView;
        public ImageView productImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
        }
    }
}
