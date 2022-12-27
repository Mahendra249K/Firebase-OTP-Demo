package com.mahendrapractice.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SpashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                boolean isUsetLogin=preferences.getBoolean("isUserLogin", false);


                if (isUsetLogin) {

                    Intent intent=new Intent(SpashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SpashScreen.this, sendOTPActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME);
    }
}