package com.genericsol.quickcart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genericsol.quickcart.R;
import com.genericsol.quickcart.model.OrderModel;

import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {

    private List<OrderModel.ProductModel> productList;

    public OrderProductAdapter(List<OrderModel.ProductModel> productList) {
        this.productList = productList;
    }

    public static class OrderProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView;
        private TextView productQuantityTextView;
        private TextView productPriceTextView;

        public OrderProductViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.textViewProductName);
            productQuantityTextView = itemView.findViewById(R.id.textViewProductQuantity);
            productPriceTextView = itemView.findViewById(R.id.textViewProductPrice);
        }
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new OrderProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position) {
        OrderModel.ProductModel currentProduct = productList.get(position);

        holder.productNameTextView.setText("Product: " + currentProduct.getName());
        holder.productQuantityTextView.setText("Quantity: " + currentProduct.getQuantity());
        holder.productPriceTextView.setText("Price: " + currentProduct.getPrice()+" Rs");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
