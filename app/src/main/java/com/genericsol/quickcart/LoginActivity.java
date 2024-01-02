package com.genericsol.quickcart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private List<UserModel> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.Email);
        etPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnlogin);
        tvSignUp = findViewById(R.id.SignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Fetch users when the activity is created
        fetchUsers();
    }

    private void performLogin() {
        String enteredEmail = etEmail.getText().toString().trim();
        String enteredPassword = etPassword.getText().toString().trim();

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check user credentials using the fetched data
        checkUserCredentials(enteredEmail, enteredPassword);
    }

    private void fetchUsers() {
        UserApi userService = ApiClient.getClient().create(UserApi.class);

        Call<List<UserModel>> call = userService.getUsers();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    users = response.body();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserCredentials(String enteredEmail, String enteredPassword) {
        if (users == null) {
            Toast.makeText(LoginActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
            return;
        }

        for (UserModel user : users) {
            if (user.getEmail().equals(enteredEmail) && user.getPasswordHash().equals(enteredPassword)) {
                if (enteredEmail.equals("admin@gmail.com")) {
                    navigateToMainActivity();
                } else {
                    navigateToCategory();
                }
                return;
            }
        }

        Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity if needed
    }

    private void navigateToCategory() {
        Intent intent = new Intent(LoginActivity.this, Category.class);
        startActivity(intent);
        finish(); // Finish the current activity if needed
    }
}
