package com.genericsol.quickcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    Button btnlogout;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   btnlogout = findViewById(R.id.logout);
   mAuth = FirebaseAuth.getInstance();
   btnlogout.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           mAuth.signOut();
           Intent intent = new Intent( MainActivity.this, login_form.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);
       }
   });


    }
}