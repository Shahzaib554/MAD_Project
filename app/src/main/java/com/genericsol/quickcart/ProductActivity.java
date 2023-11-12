package com.genericsol.quickcart;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genericsol.quickcart.adapter.ProductAdapter;
import com.genericsol.quickcart.apiClient.ApiClient;
import com.genericsol.quickcart.model.ProductModel;
import com.genericsol.quickcart.model.ProductApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {
    private List<ProductModel> products;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        RecyclerView recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        // Retrieve the category ID passed from the Category activity
        String categoryId = getIntent().getStringExtra("category_id");

        ProductApi productApi = ApiClient.getClient().create(ProductApi.class);

        Call<List<ProductModel>> call = productApi.getProductsByCategory(categoryId);
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    products = response.body();
                    productAdapter = new ProductAdapter(ProductActivity.this, products, ProductActivity.this); // Pass this as the OnItemClickListener
                    recyclerView.setAdapter(productAdapter);
                } else {
                    Toast.makeText(ProductActivity.this, "Error fetching products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(ProductModel product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("selected_product", product);
        startActivity(intent);
    }
}
