package com.example.educationloancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    //Global Variables
    ImageButton backButton;
    PieChart pieChart;
    float totalPayment = 0;
    TextView interest, loan, payment, paymentPer;
    //colors of pie chart
    int[] colorArray = new int[]{Color.parseColor("#f7931e"), Color.parseColor("#ffd45c")};
    String activityFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);

        //get passed values
        Log.d("commonLog", "Getting passed values");
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        float loanAmount = Float.parseFloat(extras.getString("lAmount"));
        float iRate = Float.parseFloat(extras.getString("iRate")) / 100;
        int lTerm = Integer.parseInt(extras.getString("lTerm"));
        String rPeriod = extras.getString("rPeriod");
        String iRateTerm = extras.getString("iRateTerm");
        activityFrom = extras.getString("activityFrom");
        Log.d("commonLog", "Values are Assigned");

        //calculation
        //interest term convert to months
        if(rPeriod.equalsIgnoreCase("YEARS")){
            lTerm = lTerm * 12;
        }

        Log.d("commonLog", "Calculation Started");
        float interestPerMonth = loanAmount * iRate;

        //If annual interest is given
        if(iRateTerm.equalsIgnoreCase("YEAR")){
            interestPerMonth = (loanAmount * iRate) / 12;
        }
        final float paymentPerMonth = interestPerMonth + (loanAmount / lTerm);
        float totalPayment = paymentPerMonth * lTerm;
        float totalInterest = totalPayment - loanAmount;
        Log.d("commonLog", "Calculation Complete");

        //initiate button references
        backButton = (ImageButton)findViewById(R.id.backButton);

        //initiate text fields
        interest = (TextView)findViewById(R.id.totalInterest);
        loan = (TextView)findViewById(R.id.loanAmount);
        payment = (TextView)findViewById(R.id.totalPayment);
        paymentPer = (TextView)findViewById(R.id.paymentPer);

        //initial value paymentPer
        paymentPer.setText(String.format("%.2f", paymentPerMonth));

        //Display Results
        interest.setText(String.format("%.2f", totalInterest));
        loan.setText(String.format("%.2f", loanAmount));
        payment.setText(String.format("%.2f", totalPayment));

        //Pie chart configurations
        pieChart = findViewById(R.id.resultChart);

        ArrayList<PieEntry> dataValues = new ArrayList<>();
        dataValues.add(new PieEntry(loanAmount, "Loan"));
        dataValues.add(new PieEntry(totalInterest, "Interest"));

        //add data values to pie chart
        PieDataSet pieDataSet = new PieDataSet(dataValues, "");
        //set pie chart colors
        pieDataSet.setColors(colorArray);

        //pie chart data view configurations
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(10);
        pieData.setValueTextColor(Color.parseColor("#ffffff"));

        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        //Entry labels disabled
        //pieChart.setDrawEntryLabels(false);
        //Pie chart animation

        pieChart.animateXY(1000, 1000);

        //Spinner for loan term selection
        MaterialSpinner paymentPerSpinner = (MaterialSpinner) findViewById(R.id.paymentPerSpinner);
        String[] periods = new String[]{"MONTH", "3 MONTHS", "6 MONTHS", "YEAR"};

        if(lTerm >= 12){
            periods = new String[]{"MONTH", "3 MONTHS", "6 MONTHS", "YEAR"};
        }else if(lTerm < 12 && lTerm > 6){
            periods = new String[]{"MONTH", "3 MONTHS", "6 MONTHS", "YEAR"};
        }else if(lTerm == 6){
            periods = new String[]{"MONTH", "3 MONTHS", "6 MONTHS"};
        }else if(lTerm < 6 && lTerm > 3){
            periods = new String[]{"MONTH", "3 MONTHS", "6 MONTHS"};
        }else if(lTerm == 3){
            periods = new String[]{"MONTH", "3 MONTHS"};
        }else if(lTerm < 3 && lTerm > 1){
            periods = new String[]{"MONTH", "3 MONTHS"};
        }else{
            periods = new String[]{"MONTH"};
        }

        paymentPerSpinner.setItems(periods);

        paymentPerSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item.equalsIgnoreCase("MONTH")){
                    paymentPer.setText(String.format("%.2f", paymentPerMonth));
                }else if(item.equalsIgnoreCase("3 MONTHS")){
                    paymentPer.setText(String.format("%.2f", (paymentPerMonth * 3)));
                }else if(item.equalsIgnoreCase("6 MONTHS")){
                    paymentPer.setText(String.format("%.2f", (paymentPerMonth * 6)));
                }else if(item.equalsIgnoreCase("YEAR")){
                    paymentPer.setText(String.format("%.2f", (paymentPerMonth * 12)));
                }
            }
        });

        //on click listeners
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backButton :
                //Log message
                Log.d("onClickListeners", "back clicked");
                //back button on click
                if(activityFrom.equalsIgnoreCase("Calculator")){
                    Intent calculator = new Intent(this, CalculatorActivity.class);
                    startActivity(calculator);
                }else{
                    Intent history = new Intent(this, HistoryActivity.class);
                    startActivity(history);
                }
                break;
        }
    }

    public float calculateInterestPower(float iRate, int lTerm){
        float interestPower = 1;

        for(int i = 0; i < lTerm ; i++ ){
            interestPower *= (iRate + 1);
        }

        return interestPower;
    }
}