package com.example.ideationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        replace(new Home_fragment());

        bottom_nav = findViewById(R.id.nav);

        bottom_nav.setItemHorizontalTranslationEnabled(true);
        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.home) replace(new Home_fragment());
                else if (item.getItemId()==R.id.feed) replace(new Feed_fragment());
                else if (item.getItemId()==R.id.bookmark) replace(new Bookmark_fragment());
                else if (item.getItemId()==R.id.idea_about) replace(new About_fragment());
                else replace(new Profile_fragment());
                return true;
            }
        });

    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frame_layout,fragment).commit();
    }
}