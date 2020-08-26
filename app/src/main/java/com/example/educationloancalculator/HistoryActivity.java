package com.example.educationloancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.educationloancalculator.CustomAdapter.CustomHistoryAdapter;
import com.example.educationloancalculator.Database.DBHelper;
import com.example.educationloancalculator.HistoryRepository.HistoryRepository;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    //Global Variables
    ListView listView;
    ArrayList<HistoryRepository> historyRepositories;
    ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //initiate list view
        listView = (ListView)findViewById(R.id.historyList);
        Log.d("databaseOperation", "Database operation end!");

        //Data retrieval
        DBHelper dbHelper = new DBHelper(this);
        historyRepositories = dbHelper.readLatestFiveRecords();

        //limit values
        ArrayList<HistoryRepository> limitedValues = new ArrayList<>();
        if (historyRepositories.size() >= 5 ){
            for(int i = 0; i < 5; i++){
                limitedValues.add(historyRepositories.get(i));
            }
        }else {
            limitedValues.addAll(historyRepositories);
        }

        //set Adapter
        listView.setAdapter(new CustomHistoryAdapter(this, limitedValues));

        //initiate button references
        backButton = (ImageButton)findViewById(R.id.backButton);

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
                Intent home = new Intent(this, HomeActivity.class);
                startActivity(home);
                break;
        }
    }
}