package com.kmr.rmn.custombottomnavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurvedNavigationView navigationView = findViewById(R.id.bottomNav);
        navigationView.inflateMenu(R.menu.bottom_menu);
    }
}