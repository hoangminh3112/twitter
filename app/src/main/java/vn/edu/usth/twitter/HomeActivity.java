package vn.edu.usth.twitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.edu.usth.twitter.databinding.ActivityHomeBinding;
import vn.edu.usth.twitter.databinding.ActivityLoginBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private ActionBar actionBar;
    private FirebaseAuth firebaseAuth;

    private String email =  "", password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
         bottomNavigationView.setSelectedItemId(R.id.home);
         bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()) {
                     case R.id.dashboard:
                         startActivity(new Intent(getApplicationContext()
                         , ProfileActivity.class));
                         overridePendingTransition(0, 0);
                         return true;
                     case R.id.home:
                         return true;

                 }
                 return false;
             }
         });

//        binding.userInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//            }
//        });

        actionBar = getSupportActionBar();
        actionBar.setTitle("Home");

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
    }
    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            //user not logged in
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            //logged in, get info
            String email = firebaseUser.getEmail();
            Toast.makeText(HomeActivity.this, "Account Created\n" + email, Toast.LENGTH_SHORT).show();
        }
    }
}