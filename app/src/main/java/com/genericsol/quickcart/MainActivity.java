package com.genericsol.quickcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import java.util.ArrayList;

import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;

//dashboad imported files
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {


    Button btnlogout;
    FirebaseAuth mAuth;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

//For Products
    ArrayList<Product> productList = new ArrayList<>();
    Product product1 = new Product(R.drawable.apple_product, "Apple Product", "$1000");
    Product product2 = new Product(R.drawable.laptop, "Laptop", "$800");
    Product product3 = new Product(R.drawable.smart_watch, "Smart Watch", "$600");
    Product product4 = new Product(R.drawable.dsler, "Dslr", "$800");
    Product product5 = new Product(R.drawable.speakers, "Speakers", "$700");
    Product product6 = new Product(R.drawable.headphones, "HeadPhone", "$500");



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




//        Product displaying
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);
        productList.add(product6);


        ProductAdapter adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

//        Toolbar

        Toolbar toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_account:
                    {
                        Toast.makeText(MainActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_settings:
                    {
                        Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_logout:
                    {
                        LoginManager.getInstance().logOut();
                        mAuth.signOut();
                        googleSignOut();
                        finish();
                        Intent intent = new Intent(MainActivity.this, login_form.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });



//        btn

//      btnlogout = findViewById(R.id.btnlogout);
        mAuth = FirebaseAuth.getInstance();


        //   Google Option And Client
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);


   }
void googleSignOut(){
    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            finish();
            startActivity(new Intent(MainActivity.this, login_form.class));
        }
    });

    }

//    Toobar

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}