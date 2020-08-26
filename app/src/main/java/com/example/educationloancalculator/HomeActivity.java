package com.example.educationloancalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    //declare global variables
    CardView calNavigator, historyNavigator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove app bar and status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

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
                Intent calculator = new Intent(this, CalculatorActivity.class);
                startActivity(calculator);
                break;
            case R.id.historyNavigatorBtn :
                //log message
                Log.d("onClickListeners", "History navigation clicked");
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;
        }
    }
}

