package com.genericsol.quickcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class login_form extends AppCompatActivity {

    EditText inputemail, inputpassword;
    TextView forget;
    Button btnlogin;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        forget = findViewById(R.id.forgetPassword);
        inputpassword = findViewById(R.id.inputPassword);
        inputemail = findViewById(R.id.Email);
        btnlogin = findViewById(R.id.btnlogin);
        mAuth=FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(login_form.this);
//        This is for signup intent
        TextView btn = findViewById(R.id.SignUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_form.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });

    }
    public void checkCrededentials() {
        String email = inputemail.getText().toString();
        String password = inputpassword.getText().toString();
        if (email.isEmpty() || !email.contains("@")) {
            showError(inputemail, "Email is not valid");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(inputpassword, "Password must be 7 characters");
        }
        else{
//            For Loading the pop-up
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please wait while check your credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
//            For firebase Authentication
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   mLoadingBar.dismiss();
                   Intent intent = new Intent(login_form.this, MainActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
               }
               else{
                   Toast.makeText(login_form.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
               }
                }
            });

        }
    }

        private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void forgetPassword(){
        // Get the user's email from the email input field
        String email = inputemail.getText().toString().trim();

        if (email.isEmpty()){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }else {


// Call the sendPasswordResetEmail method with the user's email
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Show a success message to the user
                                Toast.makeText(login_form.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            } else {
                                // Show an error message to the user
                                Toast.makeText(login_form.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}