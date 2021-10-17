package vn.edu.usth.twitter;

import java.io.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.profile);

    }
    ProfileActivity profileActivity = new ProfileActivity();
    TweetsFragment tweetsFragment = new TweetsFragment();
    ReTweetsFragment reTweetsFragment = new ReTweetsFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileActivity).commit();
                return true;

            case R.id.tweet:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, tweetsFragment).commit();
                return true;

            case R.id.retweet:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, reTweetsFragment).commit();
                return true;
        }
        return false;
    }
}

