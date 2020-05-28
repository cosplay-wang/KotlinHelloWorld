package com.cosplay.kotlin.hw.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.cosplay.kotlin.hw.R;

public class NavigationActivity extends AppCompatActivity {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        navController = Navigation.findNavController(this,R.id.navigation_frag);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return  navController.navigateUp();
    }
}
