package com.example.mymail.Login;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymail.R;

import static com.example.mymail.ShareFunction.setFragment;

public class RegisterActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        frameLayout = findViewById(R.id.frameLayout);
        setFragment(new SignInFragment(),frameLayout,this,true);
    }

}
