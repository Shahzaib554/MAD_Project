package com.genericsol.quickcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.genericsol.quickcart.apiClient.ApiClient;
import com.genericsol.quickcart.model.UserApi;
import com.genericsol.quickcart.model.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etPhone;
    private Button btnSignUp;
    private UserApi userService;

    private TextView tvAlreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.inputName);
        etEmail = findViewById(R.id.inputEmail);
        etPassword = findViewById(R.id.inputPassword);
        etPhone = findViewById(R.id.inputPhoneNumber);
        btnSignUp = findViewById(R.id.btnRegister);
        tvAlreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);

        userService = ApiClient.getClient().create(UserApi.class);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();

                // Validate input
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Call the signup API
                    signUp(username, email, password, phone);
                }
            }
        });

        // Handle "Already have an account?" click
        tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Login activity
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void signUp(String username, String email, String password, String phone) {
        Call<UserModel> call = userService.createUser(new UserModel(username, email, password, phone));

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    // Handle success, e.g., show a success message or navigate to another activity
                    Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();

                    // Navigate to CategoryActivity
                    Intent intent = new Intent(SignUpActivity.this, Category.class);
                    startActivity(intent);
                    finish(); // Optional: Close the current activity to prevent going back to it with the back button
                } else {
                    // Handle error, e.g., show an error message
                    Toast.makeText(SignUpActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                // Handle failure, e.g., show a network error message
                Toast.makeText(SignUpActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
