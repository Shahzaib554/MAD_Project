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
import com.genericsol.quickcart.model.UserModel;
import com.genericsol.quickcart.model.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignUp;
    private UserApi userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.Email);
        etPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnlogin);
        tvSignUp = findViewById(R.id.SignUp);

        // Set onClickListener for the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to initiate login process
                performLogin();
            }
        });

        // Set onClickListener for the sign-up text
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the sign-up activity or perform any other relevant action
                // For example:
                 Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                 startActivity(intent);
                 finish();
            }
        });
    }

    private void performLogin() {
        String enteredEmail = etEmail.getText().toString().trim();
        String enteredPassword = etPassword.getText().toString().trim();

        // Check if email and password are not empty
        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create API service
        UserApi userService = ApiClient.getClient().create(UserApi.class);

        // Make API request to get users
        Call<List<UserModel>> call = userService.getUsers();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    List<UserModel> users = response.body();

                    // Check user credentials
                    if (checkUserCredentials(enteredEmail, enteredPassword, users)) {
                        // Navigate to CategoryActivity
                        Intent intent = new Intent(LoginActivity.this, Category.class);
                        startActivity(intent);
                        finish(); // Finish the current activity if needed
                    } else {
                        // Display an error message or handle incorrect credentials
                        Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(LoginActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                // Handle failure
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkUserCredentials(String enteredEmail, String enteredPassword, List<UserModel> users) {
        for (UserModel user : users) {
            if (user.getEmail().equals(enteredEmail) && user.getPasswordHash().equals(enteredPassword)) {
                return true; // Credentials are correct
            }
        }
        return false; // Credentials are incorrect
    }
}
