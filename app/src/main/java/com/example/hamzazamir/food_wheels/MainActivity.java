package com.example.hamzazamir.food_wheels;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{
    private static int Splash_timeout = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){
           @Override
            public void run(){
               startActivity(new Intent(MainActivity.this, LogIn.class));
               finish();
           }
        },Splash_timeout);

    }

}
