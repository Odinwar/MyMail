package com.example.mymail;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymail.Login.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        SystemClock.sleep(3000);
        Intent loginIntent  = new Intent(SplashActivity.this, RegisterActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser==null)
        {
            Intent loginIntent  = new Intent(SplashActivity.this,RegisterActivity.class);
            startActivity(loginIntent);
        }else
            {
                Intent loginIntent  = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(loginIntent);
            }
        finish();
    }
}
