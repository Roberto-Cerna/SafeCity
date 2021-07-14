package com.example.safecity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplanScreenSafecity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splan_screen_safecity);
        getSupportActionBar().hide();

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),com.example.safecity.ui.login.LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(r,2000);

    }
}