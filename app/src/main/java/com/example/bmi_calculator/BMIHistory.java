package com.example.bmi_calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BMIHistory extends AppCompatActivity {
    private LinearLayout container;
    private TextView date;
    private TextView time;
    private TextView weight;
    private TextView height;
    private TextView bmi;
    private TextView result;
    private TextView bmiHistory;
    private ListView listView;
    private TextView result_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmihistory);

        loadBmiResult();

        container = (LinearLayout) findViewById(R.id.container);
        date = (TextView) findViewById(R.id.date_output);
        time = (TextView) findViewById(R.id.time_output);
        weight = (TextView) findViewById(R.id.kg);
        height = (TextView) findViewById(R.id.cm);
        bmi = (TextView) findViewById(R.id.bmi_result);
        result = (TextView) findViewById(R.id.resultOutput);
        bmiHistory = (TextView) findViewById(R.id.bmi_history);
        result_history = (TextView) findViewById(R.id.result_history);

        result_history.setText(buildBmiHistory());
    }

    private String buildBmiHistory() {
       String bmiResult = loadBmiResult();

       if(bmiResult.equals("not found")) {
           return "";
       }

       return bmiResult.replace("|", "         ").replace(";", "\n");
    }

    private String loadBmiResult() {
        SharedPreferences sp = getSharedPreferences("bmi", Context.MODE_PRIVATE);
//        System.out.println("bmi => " + sp.getString("bmi", "not found"));
        return  sp.getString("bmi", "not found");
    }

}
