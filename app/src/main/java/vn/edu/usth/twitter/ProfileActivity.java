package vn.edu.usth.twitter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.edu.usth.twitter.databinding.ActivityLoginBinding;
import vn.edu.usth.twitter.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    //view binding
    private ActivityProfileBinding binding;

    //action bar
    private ActionBar actionBar;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    private String email =  "", password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //config action bar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Log In");

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //logout
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            //user not logged in
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else {
            //logged in, get info
            String email = firebaseUser.getEmail();
            Toast.makeText(ProfileActivity.this, "Account Created\n" + email, Toast.LENGTH_SHORT).show();
            binding.emailTv.setText(email);
        }
    }
}