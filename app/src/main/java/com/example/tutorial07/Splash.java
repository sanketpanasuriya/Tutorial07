package com.example.tutorial07;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    Animation ZoomOut, SlideUp;
    TextView txtWelcome;
    ImageView imgAndroid;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgAndroid = (ImageView) findViewById(R.id.imgAndroid);
        txtWelcome = (TextView) findViewById(R.id.lblWelcome);
        preferences= getSharedPreferences("Login", MODE_PRIVATE);
        //imgAndroid.setVisibility(View.INVISIBLE);
        //txtWelcome.setVisibility(View.INVISIBLE);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinck);
                txtWelcome.setVisibility(View.VISIBLE);
                txtWelcome.startAnimation(ZoomOut);
            }
        }, 200);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                imgAndroid.setVisibility(View.VISIBLE);
                imgAndroid.startAnimation(SlideUp);
            }
        }, 200);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (preferences.contains("isLogin")) {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}