package com.genericsol.quickcart;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genericsol.quickcart.adapter.CategoryAdapter;
import com.genericsol.quickcart.apiClient.ApiClient;
import com.genericsol.quickcart.model.CategoryApi;
import com.genericsol.quickcart.model.CategoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoriesFragment extends Fragment implements CategoryAdapter.OnItemClickListener {

    private List<CategoryModel> categories;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Create Retrofit instance
        Retrofit retrofit = ApiClient.getClient();

        // Create CategoryApi service
        CategoryApi categoryApi = retrofit.create(CategoryApi.class);

        // Make the API call to get categories
        Call<List<CategoryModel>> call = categoryApi.getCategories();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful()) {
                    categories = response.body();

                    // Create the adapter and set the item click listener
                    categoryAdapter = new CategoryAdapter(requireContext(), categories, CategoriesFragment.this);
                    recyclerView.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(requireContext(), "Error fetching categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Toast.makeText(requireContext(), "Network error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Find the FloatingActionButton in the layout
        FloatingActionButton fabAddCategory = rootView.findViewById(R.id.fab);

        // Set a click listener for the FAB button
        fabAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the FAB button click (e.g., open a screen to add a new category)
                openAddCategoryScreen();
            }
        });

        return rootView;
    }

    @Override
    public void onItemClick(CategoryModel category) {
        // Handle the category item click here
        // You can add your specific logic here, like opening an edit screen for the selected category
        Toast.makeText(requireContext(), "Clicked category: " + category.getName(), Toast.LENGTH_SHORT).show();
        openUpdateCategoryScreen(category);
    }

    // Add this method to your AdminCategoryAdapter
    private void openUpdateCategoryScreen(CategoryModel category) {
        // Create an Intent to navigate to the UpdateCategoryActivity
        Intent intent = new Intent(requireActivity(), UpdateCategoryActivity.class);

        // Pass the selected category details to the update screen
        intent.putExtra("categoryId", category.getId());
        intent.putExtra("categoryName", category.getName());
        intent.putExtra("categoryImageLink", category.getImageLink());

        // Start the activity
        requireActivity().startActivity(intent);
    }

    // Add this method to handle the FAB button click
    private void openAddCategoryScreen() {
        // Create an Intent to navigate to the AddCategoryActivity (or any other screen to add a new category)
        Intent intent = new Intent(requireActivity(), AddCategoryActivity.class);

        // Start the activity
        requireActivity().startActivity(intent);
    }
}
