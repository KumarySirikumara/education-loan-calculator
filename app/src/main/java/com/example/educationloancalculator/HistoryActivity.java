package com.example.educationloancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.educationloancalculator.CustomAdapter.CustomHistoryAdapter;
import com.example.educationloancalculator.Database.DBHelper;
import com.example.educationloancalculator.HistoryRepository.HistoryRepository;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    //Global Variables
    ListView listView;
    ArrayList<HistoryRepository> historyRepositories;
    ImageButton backButton;

    //progress bar
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history);

        //set progress bar
        progressBar = (ProgressBar)findViewById(R.id.loader_history);
        //loader wave
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        //initiate list view
        listView = (ListView)findViewById(R.id.historyList);
        Log.d("databaseOperation", "Database operation end!");

        //Set Progress bar to visible
        progressBar.setVisibility(View.VISIBLE);
        //Data retrieval
        DBHelper dbHelper = new DBHelper(this);
        historyRepositories = dbHelper.readLatestFiveRecords();

        //set Adapter
        listView.setAdapter(new CustomHistoryAdapter(this, historyRepositories));
        //Set progress bar to invisible
        progressBar.setVisibility(View.INVISIBLE);
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