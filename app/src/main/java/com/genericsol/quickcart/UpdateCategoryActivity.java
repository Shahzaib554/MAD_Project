package com.genericsol.quickcart;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.genericsol.quickcart.R;
import com.genericsol.quickcart.apiClient.ApiClient;
import com.genericsol.quickcart.model.CategoryApi;
import com.genericsol.quickcart.model.CategoryModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCategoryActivity extends AppCompatActivity {

    private EditText editTextCategoryName;
    private EditText editTextImageLink;
    private Button buttonUpdateCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        editTextImageLink = findViewById(R.id.editTextImageLink);
        buttonUpdateCategory = findViewById(R.id.buttonUpdateCategory);

        // Retrieve category details from the intent extras
        String categoryId = getIntent().getStringExtra("categoryId");
        String categoryName = getIntent().getStringExtra("categoryName");
        String categoryImageLink = getIntent().getStringExtra("categoryImageLink");

        // Set the initial values in the EditText fields
        editTextCategoryName.setText(categoryName);
        editTextImageLink.setText(categoryImageLink);

        // Handle the button click for updating the category
        buttonUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the updated values from the EditText fields
                String updatedCategoryName = editTextCategoryName.getText().toString();
                String updatedImageLink = editTextImageLink.getText().toString();

                // Create a Retrofit instance
                CategoryApi categoryApi = ApiClient.getClient().create(CategoryApi.class);

                // Create a CategoryModel object with the updated values
                CategoryModel updatedCategory = new CategoryModel(categoryId, updatedCategoryName, updatedImageLink, 0);

                // Make the update API call
                Call<CategoryModel> call = categoryApi.updateCategory(categoryId, updatedCategory);
                call.enqueue(new Callback<CategoryModel>() {
                    @Override
                    public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response (e.g., show a Toast)
                            Toast.makeText(UpdateCategoryActivity.this, "Category Updated!", Toast.LENGTH_SHORT).show();
                            // Optionally, you can finish the activity or navigate back to the previous screen
                            finish();
                        } else {
                            // Handle unsuccessful response (e.g., show an error message)
                            Toast.makeText(UpdateCategoryActivity.this, "Failed to update category", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryModel> call, Throwable t) {
                        // Handle network failure or other errors
                        Toast.makeText(UpdateCategoryActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
