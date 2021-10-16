package vn.edu.usth.twitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import vn.edu.usth.twitter.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    //ViewBinding
    private ActivitySignUpBinding binding;
    private String email ="", password = "";
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //config progress dialog
        progressBar = new ProgressBar(this);

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }

            private void validateData() {
                //getData
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
                    firebaseSignUp();
                }
            }

            private void firebaseSignUp() {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                String email = firebaseUser.getEmail();
                                Toast.makeText(SignUpActivity.this, "Account Created\n" + email, Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}