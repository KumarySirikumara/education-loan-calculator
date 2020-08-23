package com.example.educationloancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    //Splash screen off time variable
    private static int SPLASH_SCREEN_OFF = 3000;

    //variables of animations
    Animation topAnim, bottomAnim;

    //variables of intents
    ImageView logo;
    TextView appName, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove app bar and status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        //Animate references
        topAnim = AnimationUtils.loadAnimation(this, R.anim.welcome_animation_top);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.welcome_animation_bottom);

        //Hooks
        logo = (ImageView)findViewById(R.id.welcomeImage);
        appName = (TextView) findViewById(R.id.appName);
        slogan = (TextView) findViewById(R.id.slogan);

        //Animate
        logo.setAnimation(topAnim);
        appName.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        //runnable handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //calling home
                Intent home = new Intent(WelcomeActivity.this,  InterestCalculatorMain.class);
                startActivity(home);
                finish();
            }
        }, SPLASH_SCREEN_OFF);
    }
}