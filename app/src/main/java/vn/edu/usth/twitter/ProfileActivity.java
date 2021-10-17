package vn.edu.usth.twitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
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
    public boolean onSupportNavigateUp() {
        onBackPressed(); //got previous activity when back button of action clicked
        return super.onSupportNavigateUp();
    }

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        tabLayout = findViewById(R.id.tabs_layout);
        viewPager2 = findViewById(R.id.view_pager);
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Tweets"));
        tabLayout.addTab(tabLayout.newTab().setText("ReTweets"));
        tabLayout.addTab(tabLayout.newTab().setText("Liked"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        //config action bar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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