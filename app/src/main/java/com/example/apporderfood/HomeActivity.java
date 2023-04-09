package com.example.apporderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        Intent i = getIntent();
        String data = i.getStringExtra("userName");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new HomeFragment())
                .commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected_Fragment = new HomeFragment();

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selected_Fragment = new HomeFragment();
                        break;
                    case R.id.navigation_shopping_cart:
                        selected_Fragment = new ShoppingCartFragment();
                        break;
                    case R.id.navigation_search:
                        selected_Fragment = new SearchFragment();
                        break;
                    case R.id.navigation_user:
                        selected_Fragment = new UserFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("userName", data);
                        selected_Fragment.setArguments(bundle);
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, selected_Fragment)
                        .commit();
                return false;
            }
        });

    }

}

