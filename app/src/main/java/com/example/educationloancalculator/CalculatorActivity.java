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

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                spinnerSelection = item;
                Snackbar.make(view, "Selected : " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        iRateSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                iRateSpinnerSelection = item;
                Snackbar.make(view, "Selected : " + item, Snackbar.LENGTH_LONG).show();
            }
        });

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
                    historyRepository.setRatePer(iRateSpinnerSelection);
                    historyRepository.setlTerm(Integer.parseInt(loanTerm.getText().toString()));
                    historyRepository.setTermPeriod(spinnerSelection);

                    //adding data to the database
                    DBHelper dbHelper = new DBHelper(this);
                    boolean result = dbHelper.addHistory(historyRepository);

                    Log.d("databaseOperation", "Database operation end!");

                    ArrayList<HistoryRepository> historyRepositories = dbHelper.readLatestFiveRecords();
//                    for (HistoryRepository historyR : historyRepositories){
//                        Log.d("databaseData", Float.toString(historyR.getlAmount()));
//                        Log.d("databaseData", historyR.getSysDate());
//                    }

                    //loader visibility to true
                    progressBar.setVisibility(View.VISIBLE);

                    if(result){
                        //hide the loader
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(this, "History saved!", Toast.LENGTH_LONG).show();
                        //pass values to the output activity
                        Intent resultActivity = new Intent(this, ResultActivity.class);
                        resultActivity.putExtra("lAmount", loanAmount.getText().toString());
                        resultActivity.putExtra("iRate", interestRate.getText().toString());
                        resultActivity.putExtra("lTerm", loanTerm.getText().toString());
                        resultActivity.putExtra("rPeriod", spinnerSelection);
                        resultActivity.putExtra("iRateTerm", iRateSpinnerSelection);
                        resultActivity.putExtra("activityFrom", "Calculator");
                        startActivity(resultActivity);
                    }else{
                        //hide the loader
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "Error in saving data!", Toast.LENGTH_LONG).show();
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
}