package com.genericsol.quickcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Arrays;

public class login_form extends AppCompatActivity {

    EditText inputemail, inputpassword;
    TextView forget;
    Button btnlogin, btnGoogle, btnFacebook;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

//    For Facebook

    CallbackManager callbackManager;

    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

// For Facebook

        btnFacebook = findViewById(R.id.btnFacebook);


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null && accessToken.isExpired()==false){
            startActivity(new Intent(login_form.this,MainActivity.class));
            finish();
        }

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity(new Intent(login_form.this,MainActivity.class));
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions( login_form.this, Arrays.asList("public_profile"));
            }
        });



// For google Sign-in Option

        btnGoogle = findViewById(R.id.btnGoogle);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            navigateToSecondActivity();
        }

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });





//        Find the Elements with its id's
        forget = findViewById(R.id.forgetPassword);
        inputpassword = findViewById(R.id.inputPassword);
        inputemail = findViewById(R.id.Email);
        btnlogin = findViewById(R.id.btnlogin);
        btnGoogle = findViewById(R.id.btnGoogle);

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
        mLoadingBar.dismiss();
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

//    Google Signin method

    private static final int RC_GOOGLE_SIGN_IN = 123;
    private static final String TAG = "LoginActivity";

    private void signin() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // Signed in successfully, show authenticated UI.
                navigateToSecondActivity();
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            }
        } else {
            // Handle Facebook callback
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

//@Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//    try {
//        task.getResult(ApiException.class);
//        navigateToSecondActivity();
//    } catch (ApiException e) {
////        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
//
//    }
//}
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Handle Google sign-in result
//        if (requestCode == RC_GOOGLE_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                // Navigate to the next activity
//                navigateToSecondActivity();
//            } catch (ApiException e) {
//                // Handle error
//                Log.e(TAG, "Google sign-in failed", e);
//                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        // Handle Facebook login result
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//


//        Navigating to main activity method

    private void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(login_form.this, MainActivity.class);
        startActivity(intent);


}

// FaceBook




}