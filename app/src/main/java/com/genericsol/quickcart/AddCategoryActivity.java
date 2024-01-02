package com.genericsol.quickcart;

// AddCategoryActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.genericsol.quickcart.apiClient.ApiClient;
import com.genericsol.quickcart.model.CategoryApi;
import com.genericsol.quickcart.model.CategoryModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText editTextCategoryName;
    private EditText editTextImageLink;
    private Button buttonAddCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        editTextImageLink = findViewById(R.id.editTextImageLink);
        buttonAddCategory = findViewById(R.id.buttonAddCategory);

        // Handle the button click for adding a new category
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the values from the EditText fields
                String categoryName = editTextCategoryName.getText().toString();
                String imageLink = editTextImageLink.getText().toString();

                // Create a Retrofit instance
                CategoryApi categoryApi = ApiClient.getClient().create(CategoryApi.class);

                // Create a CategoryModel object with the entered values
                CategoryModel newCategory = new CategoryModel(null, categoryName, imageLink, 0);

                // Make the add category API call
                Call<CategoryModel> call = categoryApi.createCategory(newCategory);
                call.enqueue(new Callback<CategoryModel>() {
                    @Override
                    public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response (e.g., show a Toast)
                            Toast.makeText(AddCategoryActivity.this, "Category Added!", Toast.LENGTH_SHORT).show();
                            // Optionally, you can finish the activity or navigate back to the previous screen
                            finish();
                        } else {
                            // Handle unsuccessful response (e.g., show an error message)
                            Toast.makeText(AddCategoryActivity.this, "Failed to add category", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryModel> call, Throwable t) {
                        // Handle network failure or other errors
                        Toast.makeText(AddCategoryActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
