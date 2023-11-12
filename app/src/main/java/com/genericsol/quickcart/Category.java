package com.genericsol.quickcart;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genericsol.quickcart.adapter.CategoryAdapter;
import com.genericsol.quickcart.apiClient.ApiClient;
import com.genericsol.quickcart.model.CategoryApi;
import com.genericsol.quickcart.model.CategoryModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;

public class Category extends AppCompatActivity implements CategoryAdapter.OnItemClickListener {
    private List<CategoryModel> categories;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RecyclerView recyclerView = findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Use the ApiClient class to get the Retrofit instance
        CategoryApi categoryApi = ApiClient.getClient().create(CategoryApi.class);


        Call<List<CategoryModel>> call = categoryApi.getCategories();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful()) {
                    categories = response.body();

                    // Create the adapter and set the item click listener
                    categoryAdapter = new CategoryAdapter(Category.this, categories, Category.this);
                    recyclerView.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(Category.this, "Error fetching categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Toast.makeText(Category.this, "Network error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(CategoryModel category) {
        // Handle the category item click here
        // Start the ProductActivity with the selected category ID
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("category_id", category.getId());
        startActivity(intent);
    }
}
