package com.example.educationloancalculator.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.educationloancalculator.HistoryRepository.HistoryRepository;
import com.example.educationloancalculator.R;
import com.example.educationloancalculator.ResultActivity;

import java.util.ArrayList;

public class CustomHistoryAdapter extends ArrayAdapter {
    final Activity activity;
    final ArrayList<HistoryRepository> historyRepositories;

    public CustomHistoryAdapter(Activity activity, ArrayList<HistoryRepository> historyRepositories) {
        super(activity, R.layout.history_list_layout, historyRepositories);
        this.activity = activity;
        this.historyRepositories = historyRepositories;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @Nullable ViewGroup parent){

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.history_list_layout, null, false);

        TextView loanAmount = convertView.findViewById(R.id.loanAmount);
        TextView date = convertView.findViewById(R.id.date);
        CardView cardView = convertView.findViewById(R.id.card_container);

        loanAmount.setText(Float.toString(historyRepositories.get(position).getlAmount()));
        date.setText(historyRepositories.get(position).getSysDate());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(activity, ResultActivity.class);
                resultActivity.putExtra("lAmount", Float.toString(historyRepositories.get(position).getlAmount()));
                resultActivity.putExtra("iRate", Float.toString(historyRepositories.get(position).getiRate()));
                resultActivity.putExtra("lTerm", Integer.toString(historyRepositories.get(position).getlTerm()));
                resultActivity.putExtra("rPeriod", historyRepositories.get(position).getTermPeriod());
                resultActivity.putExtra("iRateTerm", historyRepositories.get(position).getRatePer());
                resultActivity.putExtra("activityFrom", "History");
                activity.startActivity(resultActivity);

            }
        });

        return convertView;
    }
}
