package com.genericsol.quickcart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genericsol.quickcart.R;
import com.genericsol.quickcart.model.OrderModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderModel> orderList;

    public OrderAdapter(List<OrderModel> orderList) {
        this.orderList = orderList;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView customerNameTextView;
        private TextView mobileNumberTextView;
        private TextView addressTextView;
        private TextView cityTextView;
        private TextView paymentMethodTextView;
        private TextView totalPriceTextView;
        private TextView createdAtTextView;
        private RecyclerView productsRecyclerView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.textViewOrderCustomerName);
            mobileNumberTextView = itemView.findViewById(R.id.textViewOrderMobileNumber);
            addressTextView = itemView.findViewById(R.id.textViewOrderAddress);
            cityTextView = itemView.findViewById(R.id.textViewOrderCity);
            paymentMethodTextView = itemView.findViewById(R.id.textViewOrderPaymentMethod);
            totalPriceTextView = itemView.findViewById(R.id.textViewOrderTotalPrice);
            createdAtTextView = itemView.findViewById(R.id.textViewOrderCreatedAt);
            productsRecyclerView = itemView.findViewById(R.id.recyclerViewOrders); // Replace with your actual RecyclerView ID for products
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel currentOrder = orderList.get(position);

        holder.customerNameTextView.setText("Customer: " + currentOrder.getCustomerName());
        holder.mobileNumberTextView.setText("Mobile: " + currentOrder.getMobileNumber());
        holder.addressTextView.setText("Address: " + currentOrder.getAddress());
        holder.cityTextView.setText("City: " + currentOrder.getCity());
        holder.paymentMethodTextView.setText("Payment Method: " + currentOrder.getPaymentMethod());
        holder.totalPriceTextView.setText("Total Price: " + currentOrder.getTotalPrice()+" Rs");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        holder.createdAtTextView.setText("Created At: " + sdf.format(currentOrder.getCreatedAt()));

        // Uncomment this section when you want to display products
        // Create an adapter for products and set it to the products RecyclerView
         OrderProductAdapter productAdapter = new OrderProductAdapter(currentOrder.getProducts());
         holder.productsRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
         holder.productsRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
