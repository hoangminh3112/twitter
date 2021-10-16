package vn.edu.usth.twitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.edu.usth.twitter.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    //ViewBinding
    private ActivityLoginBinding binding;

    //action bar
    private ActionBar actionBar;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    private String email =  "", password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //config action bar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Log In");

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        //go to sign up
        binding.dontHaveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate data
                validateData();
            }
        });

    }

    private void validateData() {
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();

        //validated
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.setError("Invalid Email");
        }
        else if (TextUtils.isEmpty(password)) {
            binding.passwordEt.setError("Enter Password");
        }
        else if (password.length()<6){
            binding.emailEt.setError("Password must atleast 6 characters long");
        }
        else {
            //valid
            firebaseLogIn();
        }

    }

    private void firebaseLogIn() {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser firebaseUser  = firebaseAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();
                        Toast.makeText(LoginActivity.this, "Logged In Success \n" + email, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this , ProfileActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}