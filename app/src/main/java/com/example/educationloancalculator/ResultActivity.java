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
import android.widget.TextView;

import com.github.mikephil.charting.formatter.PercentFormatter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    //Global Variables
    ImageButton backButton, homeButton;
    PieChart pieChart;
    TextView interest, loan, payment, paymentPer, interestRate, ratePer, loanTerm, termPeriod;
    //colors of pie chart
    int[] colorArray = new int[]{Color.parseColor("#ff7000"), Color.parseColor("#ffc000")};
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
        //assigning passed values to local variables
        float loanAmount = Float.parseFloat(extras.getString("lAmount"));
        float iRate = Float.parseFloat(extras.getString("iRate")) / 100;
        int lTerm = Integer.parseInt(extras.getString("lTerm"));
        //get lTerm before it gets converted into months
        int tempLTerm = lTerm;
        //term period -> YEARS or MONTHS
        String rPeriod = extras.getString("rPeriod");
        //rate per -> YEAR or MONTH
        String iRateTerm = extras.getString("iRateTerm");
        //Variable keep info about initiated activity
        activityFrom = extras.getString("activityFrom");
        Log.d("commonLog", "Values are Assigned");
        //end of assigning variables

        //calculation
        //interest term convert to months
        lTerm = getLoanTerm(rPeriod, lTerm);
        Log.d("commonLog", "Calculation Started");
        //get Interest Rate according to rate term given
        float interestPerMonth =  getInterestRatePerMonth(loanAmount, iRate, iRateTerm);
        final float paymentPerMonth = interestPerMonth + (loanAmount / lTerm);
        float totalPayment = paymentPerMonth * lTerm;
        float totalInterest = totalPayment - loanAmount;
        Log.d("commonLog", "Calculation Complete");
        //calculation ends

        //initiate button references
        backButton = (ImageButton)findViewById(R.id.backButton);
        backButton = (ImageButton)findViewById(R.id.homeButton);

        //initiate text fields
        interest = (TextView)findViewById(R.id.totalInterest);
        loan = (TextView)findViewById(R.id.loanAmount);
        payment = (TextView)findViewById(R.id.totalPayment);
        paymentPer = (TextView)findViewById(R.id.paymentPer);
        interestRate = (TextView)findViewById(R.id.interestRate);
        ratePer = (TextView)findViewById(R.id.ratePer);
        loanTerm = (TextView)findViewById(R.id.loanTerm);
        termPeriod = (TextView)findViewById(R.id.termPeriod);
        //end of initiating text fields

        //Display Results
        //initial value paymentPer which is calculated for a month
        paymentPer.setText(String.format("%.2f", paymentPerMonth));
        interest.setText(String.format("%.2f", totalInterest));
        loan.setText(String.format("%.2f", loanAmount));
        payment.setText(String.format("%.2f", totalPayment));
        interestRate.setText(String.format("%.2f", (iRate * 100)) + "%");
        ratePer.setText(iRateTerm + "LY");
        loanTerm.setText(Integer.toString(tempLTerm));
        termPeriod.setText(tempLTerm > 1 ? rPeriod : rPeriod.equalsIgnoreCase("YEARS") ? "YEAR" : "MONTH");

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
        //get generated loan term periods
        String[] periods = generateTermPeriods(lTerm);
        //set loan term periods
        paymentPerSpinner.setItems(periods);

        //spinner onchange listener
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
        homeButton.setOnClickListener(this);
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
            case R.id.homeButton :
                //Log message
                Log.d("onClickListeners", "home clicked");
                //back button on click
                Intent home = new Intent(this, HomeActivity.class);
                startActivity(home);
                break;
        }
    }

    public int getLoanTerm(String ratePeriod, int loanTerm){
        //interest term convert to months
        if(ratePeriod.equalsIgnoreCase("YEARS")){
            loanTerm = loanTerm * 12;
        }

        return loanTerm;
    }

    public float getInterestRatePerMonth(float loanAmount, float iRate, String iRateTerm){
        float interestPerMonth = loanAmount * iRate;

        //If annual interest is given
        if(iRateTerm.equalsIgnoreCase("YEAR")){
            interestPerMonth = (loanAmount * iRate) / 12;
        }

        return interestPerMonth;
    }

    public String[] generateTermPeriods(int lTerm){
        if(lTerm >= 12){
            return new String[]{"MONTH", "3 MONTHS", "6 MONTHS", "YEAR"};
        }else if(lTerm < 12 && lTerm >= 6){
            return new String[]{"MONTH", "3 MONTHS", "6 MONTHS"};
        }else if(lTerm < 6 && lTerm >= 3){
            return new String[]{"MONTH", "3 MONTHS"};
        }else if(lTerm < 3 && lTerm >= 1){
            return new String[]{"MONTH"};
        }else{
            return new String[]{"MONTH", "3 MONTHS", "6 MONTHS", "YEAR"};
        }
    }
}