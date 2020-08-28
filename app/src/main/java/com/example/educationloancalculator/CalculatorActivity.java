package com.example.educationloancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.educationloancalculator.Database.DBHelper;
import com.example.educationloancalculator.HistoryRepository.HistoryRepository;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    //Declare Global Variables
    TextInputLayout loanAmountLayout, interestRateLayout, loanTermLayout;
    ImageButton backButton;
    Button submit;
    TextInputEditText loanAmount, interestRate, loanTerm;
    String spinnerSelection = "YEARS";
    String iRateSpinnerSelection = "YEAR";

    //progress bar
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove app bar and status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calculator);

        //set progress bar
        progressBar = (ProgressBar)findViewById(R.id.loader_calculator);
        //loader wave
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        //Spinner for loan term selection
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        final MaterialSpinner iRateSpinner = (MaterialSpinner) findViewById(R.id.iRateSpinner);
        spinner.setItems("YEARS", "MONTHS");
        iRateSpinner.setItems("YEAR", "MONTH");

        //Spinner onChange listener starts
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //selects Term Period
                spinnerSelection = item;
                Snackbar.make(view, "Selected : " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        iRateSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Selects Rate per
                iRateSpinnerSelection = item;
                Snackbar.make(view, "Selected : " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        //Spinner onChange listener ends

        //Initiate EditText Layouts
        loanAmountLayout = (TextInputLayout) findViewById(R.id.loanAmountEditTextLayout);
        interestRateLayout = (TextInputLayout) findViewById(R.id.interestRateEditTextLayout);
        loanTermLayout = (TextInputLayout) findViewById(R.id.loanTermEditTextLayout);

        //initiate EditText fields
        loanAmount = (TextInputEditText) findViewById(R.id.loanAmountEditText);
        interestRate = (TextInputEditText) findViewById(R.id.interestRateEditText);
        loanTerm = (TextInputEditText) findViewById(R.id.loanTermEditText);

        //initiate button references
        backButton = (ImageButton)findViewById(R.id.backButton);
        submit = (Button)findViewById(R.id.calculateSubmit);

        //on click listeners
        backButton.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backButton :
                //Log message
                Log.d("onClickListeners", "back clicked");
                //back button on click
                Intent home = new Intent(this, HomeActivity.class);
                startActivity(home);
                break;
            case R.id.calculateSubmit :
                //Log message
                Log.d("onClickListeners", "submit clicked");
                //submit button on click
                //validate input values whether empty
                if(!inputIsEmpty()){
                    Log.d("databaseOperation", "Database operation started!");
                    //add data to history repository
                    HistoryRepository historyRepository = new HistoryRepository();
                    historyRepository.setlAmount(Float.parseFloat(loanAmount.getText().toString()));
                    historyRepository.setiRate(Float.parseFloat(interestRate.getText().toString()));
                    historyRepository.setlTerm(Integer.parseInt(loanTerm.getText().toString()));
                    //rate per -> YEAR or MONTH
                    historyRepository.setRatePer(iRateSpinnerSelection);
                    //term period -> YEARS or MONTHS
                    historyRepository.setTermPeriod(spinnerSelection);
                    //validate inputs
                    if(validateInputs(historyRepository)) {
                        //adding data to the database
                        DBHelper dbHelper = new DBHelper(this);
                        boolean result = dbHelper.addHistory(historyRepository);

                        Log.d("databaseOperation", "Database operation end!");

                        //loader visibility to true
                        progressBar.setVisibility(View.VISIBLE);

                        if (result) {
                            //hide the loader
                            progressBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(this, "History saved!", Toast.LENGTH_LONG).show();
                            //pass values to the output activity
                            Intent resultActivity = new Intent(this, ResultActivity.class);
                            resultActivity.putExtra("lAmount", Float.toString(historyRepository.getlAmount()));
                            resultActivity.putExtra("iRate", Float.toString(historyRepository.getiRate()));
                            resultActivity.putExtra("lTerm", Integer.toString(historyRepository.getlTerm()));
                            //term period -> YEARS or MONTHS
                            resultActivity.putExtra("rPeriod", historyRepository.getTermPeriod());
                            //rate per -> YEAR or MONTH
                            resultActivity.putExtra("iRateTerm", historyRepository.getRatePer());
                            resultActivity.putExtra("activityFrom", "Calculator");
                            startActivity(resultActivity);
                        } else {
                            //end else add data to SQLite DB
                            //hide the loader
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(this, "Error in saving data!", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        //end else validate inputs
                        //hide the loader
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
                break;
        }
    }

    public boolean inputIsEmpty(){

        //set all errors to null
        loanAmountLayout.setError(null);
        interestRateLayout.setError(null);
        loanTermLayout.setError(null);

        String loanAmountVal = loanAmount.getText().toString();
        String interestRateVal = interestRate.getText().toString();
        String loanTermVal = loanTerm.getText().toString();

        if(loanAmountVal.trim().isEmpty() || interestRateVal.trim().isEmpty() || loanTermVal.trim().isEmpty()){
            //check empty field and set error to the relevant text field
            if(loanAmountVal.trim().isEmpty()){
                loanAmountLayout.setError("Cannot be empty!");
            }

            if(interestRateVal.trim().isEmpty()){
                interestRateLayout.setError("Cannot be empty!");
            }

            if(loanTermVal.trim().isEmpty()){
                loanTermLayout.setError("Cannot be empty!");
            }

            Toast.makeText(this, "Please fill all Fields!", Toast.LENGTH_LONG).show();

            return true;
        }
        return false;
    }

    public boolean validateInputs(HistoryRepository historyRepository){
        //set all errors to null
        loanAmountLayout.setError(null);
        interestRateLayout.setError(null);
        loanTermLayout.setError(null);

        if(historyRepository.getiRate() <= 0 && historyRepository.getlTerm() <= 0 && historyRepository.getlAmount() <= 0){
            //set errors for all input indicators
            loanAmountLayout.setError("Cannot be empty!");
            interestRateLayout.setError("Cannot be empty!");
            loanTermLayout.setError("Cannot be empty!");
            Toast.makeText(this, "Loan amount, Interest rate and loan term should be valid numbers!", Toast.LENGTH_LONG).show();
            return false;
        }else if(historyRepository.getlAmount() <= 0){
            //input invalid indicator for loan amount
            loanAmountLayout.setError("Cannot be empty!");
            Toast.makeText(this, "Loan Amount should be a valid number!", Toast.LENGTH_LONG).show();
            return false;
        }else if(historyRepository.getiRate() <= 0){
            //input invalid indicator for interest rate
            interestRateLayout.setError("Cannot be empty!");
            Toast.makeText(this, "Interest Rate should be a valid number!", Toast.LENGTH_LONG).show();
            return false;
        }else if(historyRepository.getlTerm() <= 0) {
            //input invalid indicator for loan term
            loanTermLayout.setError("Cannot be empty!");
            Toast.makeText(this, "Loan Term should be a valid number!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}