package com.example.educationloancalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class InterestCalculatorMain extends AppCompatActivity implements View.OnClickListener{
    //declare global variables
    CardView calNavigator, historyNavigator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_interest_calculator_main);

        //initiate buttons
        calNavigator = (CardView) findViewById(R.id.calculatorNavigatorBtn);
        historyNavigator = (CardView) findViewById(R.id.historyNavigatorBtn);

        //set onclick listeners
        calNavigator.setOnClickListener(this);
        historyNavigator.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calculatorNavigatorBtn :
                //log message
                Log.d("onClickListeners", "Calculator navigation clicked");
                Toast.makeText(this, "Calculator Button Clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.historyNavigatorBtn :
                //log message
                Log.d("onClickListeners", "History navigation clicked");
                Toast.makeText(this, "History Button Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}

