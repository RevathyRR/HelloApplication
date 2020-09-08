package com.example.helloapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent sign = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(sign);
                finish();
                //Do something after 100ms
            }
        }, 3000);
        setContentView(R.layout.activity_splash);

    }
}