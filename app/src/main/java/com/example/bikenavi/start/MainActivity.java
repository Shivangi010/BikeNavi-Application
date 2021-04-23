package com.example.bikenavi.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bikenavi.R;
import com.example.bikenavi.authentication.LoginActivity;
import com.example.bikenavi.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "MainActivity";

    // variables
    private FirebaseAuth mfAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfAuth = FirebaseAuth.getInstance();

        if (mfAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}