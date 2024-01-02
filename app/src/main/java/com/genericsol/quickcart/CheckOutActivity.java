package com.genericsol.quickcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genericsol.quickcart.adapter.CheckoutAdapter;
import com.genericsol.quickcart.apiClient.ApiClient;
import com.genericsol.quickcart.model.CartManager;
import com.genericsol.quickcart.model.OrderApi;
import com.genericsol.quickcart.model.OrderModel;
import com.genericsol.quickcart.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    String[] cities = {"Lahore", "Karachi", "Islamabad"};
    private Spinner citySpinner;
    private EditText edtName;
    private EditText mobileNumber;
    private EditText address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        citySpinner = findViewById(R.id.cityspinner);
        edtName = findViewById(R.id.edtName);
        mobileNumber = findViewById(R.id.MobileNumber);
        address = findViewById(R.id.Address);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        RecyclerView orderRecyclerView = findViewById(R.id.orderRecyclerView);
        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(this, CartManager.getInstance().getCartItems());
        orderRecyclerView.setAdapter(checkoutAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        orderRecyclerView.setLayoutManager(layoutManager);

        checkoutAdapter.notifyDataSetChanged();

        Button paymentButton = findViewById(R.id.Payment);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCity = cities[citySpinner.getSelectedItemPosition()];
                String customerName = edtName.getText().toString();
                String customerMobileNumber = mobileNumber.getText().toString();
                String customerAddress = address.getText().toString();
                String paymentMethod = getSelectedPaymentMethod();
                double totalPrice = calculateTotalPrice(CartManager.getInstance().getCartItems());
                List<ProductModel> products = CartManager.getInstance().getCartItems();

                List<OrderModel.ProductModel> convertedProducts = new ArrayList<>();
                for (ProductModel productModel : products) {
                    OrderModel.ProductModel product = new OrderModel.ProductModel();
                    product.setName(productModel.getName());
                    product.setQuantity(productModel.getQuantity());
                    product.setPrice(productModel.getPrice());
                    convertedProducts.add(product);
                }

                OrderModel orderModel = new OrderModel();
                orderModel.setCustomerName(customerName);
                orderModel.setMobileNumber(customerMobileNumber);
                orderModel.setAddress(customerAddress);
                orderModel.setCity(selectedCity);
                orderModel.setPaymentMethod(paymentMethod);
                orderModel.setTotalPrice(totalPrice);
                orderModel.setProducts(convertedProducts);

                postOrderToServer(orderModel);
            }
        });
    }

    private String getSelectedPaymentMethod() {
        RadioGroup paymentRadioGroup = findViewById(R.id.paymentRadioGroup);
        int selectedId = paymentRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        return selectedRadioButton.getText().toString();
    }

    private double calculateTotalPrice(List<ProductModel> cartItems) {
        double totalPrice = 0;
        for (ProductModel item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    private void postOrderToServer(OrderModel orderModel) {
        OrderApi orderApi = ApiClient.getClient().create(OrderApi.class);

        Call<OrderModel> call = orderApi.postOrder(orderModel);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                if (response.isSuccessful()) {


                    // Handle successful order posting, if needed
                    Toast.makeText(CheckOutActivity.this,"Order Place Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CheckOutActivity.this, "Failed to post order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
