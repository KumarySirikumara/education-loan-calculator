package com.example.educationloancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int loan_period;
    int loan_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Kumary", 20000));
        data.add(new ValueDataEntry("Uthpala", 12000));
        data.add(new ValueDataEntry("Yugma", 18000));
        data.add(new ValueDataEntry("Kasun", 3000));

        pie.data(data);

        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);
    }
}