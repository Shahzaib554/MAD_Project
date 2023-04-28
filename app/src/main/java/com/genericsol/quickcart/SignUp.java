package com.genericsol.quickcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class SignUp extends AppCompatActivity {

    EditText userName, inputEmail, inputPassword, re_EnterPassword;
    Button btnRegistration;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        re_EnterPassword = findViewById(R.id.inputConfirmPassword);
        btnRegistration = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar= new ProgressDialog(SignUp.this);
        TextView btn = findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, login_form.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
        });

    }

    public void checkCrededentials() {
        String name = userName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = re_EnterPassword.getText().toString();

        if (name.isEmpty() || name.length() < 7) {
            showError(userName, "Your user name is not valid!");
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Email is not valid");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(inputPassword, "Password must be 7 characters");
        } else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            showError(re_EnterPassword, "Password not match!");
        } else {
//            This is for the loading pop
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please wait, white check your credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

//            This is for firebase code to registration

         mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){
                     Toast.makeText(SignUp.this, "Sussfully Registration", Toast.LENGTH_SHORT).show();
                     mLoadingBar.dismiss();
                     Intent intent = new Intent(SignUp.this, MainActivity.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                 }
                 else{
                     Toast.makeText(SignUp.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                 }
             }
         });

        }

    }


    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}




