package com.example.rentpe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.rentpe.fragments.AllHomesFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AllHomesFragment()).addToBackStack(null).commit();
    }
}