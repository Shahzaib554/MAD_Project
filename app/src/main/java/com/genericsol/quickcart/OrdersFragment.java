package com.genericsol.quickcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genericsol.quickcart.apiClient.ApiClient;
import com.genericsol.quickcart.model.OrderApi;
import com.genericsol.quickcart.model.OrderModel;
import com.genericsol.quickcart.adapter.OrderAdapter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private OrderApi orderApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        recyclerView = view.findViewById(R.id.recyclerView); // Replace with your actual RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Use your ApiClient to get a Retrofit instance
        Retrofit retrofit = ApiClient.getClient();

        // Create an instance of the OrderApi interface
        orderApi = retrofit.create(OrderApi.class);

        // Fetch orders from the API and set them to the adapter
        getOrdersFromApi();

        return view;
    }

    private void getOrdersFromApi() {
        Call<List<OrderModel>> call = orderApi.getOrderList();
        call.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderModel> orders = response.body();
                    orderAdapter = new OrderAdapter(orders);
                    recyclerView.setAdapter(orderAdapter);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
