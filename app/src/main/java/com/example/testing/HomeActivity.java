package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.testing.fragment.AccountFragment;
import com.example.testing.fragment.ExploreFragment;
import com.example.testing.fragment.HomeFragment;
import com.example.testing.fragment.QAFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;


public class HomeActivity extends QMUIFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(savedInstanceState==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment())
                    .commit();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment =null;
                switch (item.getItemId()){
                    case R.id.homeFragment:
                        fragment = new HomeFragment();
                        break;
                    case R.id.exploreFragment:
                        fragment = new ExploreFragment();
                        break;
                    case R.id.qa_Fragment:
                        fragment = new QAFragment();
                        break;
                    case R.id.accountFragment:
                        fragment = new AccountFragment();
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                return true;
            }
        });
    }


}

