package com.example.bmi_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    DecimalFormat formatter = new DecimalFormat("#,###.##");

    private TextView height;
    private TextView weight;
    private TextView result;
    private TextView bmi;
    private EditText HEIGHT;
    private EditText WEIGHT;
    private TextView BMI_RESULT;
    private TextView CALCULATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadBmiResult();

        setContentView(R.layout.activity_main);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        result = findViewById(R.id.resultOutput);
        bmi = findViewById(R.id.bmi_result);
        HEIGHT = findViewById(R.id.cm);
        WEIGHT = findViewById(R.id.kg);
        BMI_RESULT = findViewById(R.id.bmi_result);
        CALCULATE = findViewById(R.id.calculate);

        EditText HEIGHT_Edit = (EditText) findViewById(R.id.cm);
        EditText WEIGHT_Edit = (EditText) findViewById(R.id.kg);

        HEIGHT_Edit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        WEIGHT_Edit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        final Button calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bmi_result = "";
                String weight_kg = WEIGHT.getText().toString();
                String height_cm = HEIGHT.getText().toString();

                System.out.println("weight: " + weight_kg);
                System.out.println("height: " + height_cm);
                float weight_input = Float.parseFloat((weight_kg));
                float height_input = Float.parseFloat((height_cm));
                double bmi_input = (weight_input / height_input / height_input) * 10000;

                if(bmi_input <= 18.4) {
                    bmi_result = "Underweight";
                } else if(bmi_input >= 18.5 && bmi_input <= 22.90) {
                    bmi_result = "Normal";
                } else if(bmi_input >= 23 && bmi_input <= 24.90) {
                    bmi_result = "Overweight";
                } else  if(bmi_input >= 25 && bmi_input <= 29.90) {
                    bmi_result = "Obese class 2";
                } else {
                    bmi_result = "Obese class 3";
                }

                String bmi_string = bmi_input + "";
                bmi_string = formatter.format(Double.parseDouble(bmi_string));
                bmi.setText(bmi_string);
                result.setText(bmi_result);

                saveBMIResult(weight_input, bmi_input, bmi_result);
            } // end onClick
        });

        ImageView buttonHistory = (ImageView) findViewById(R.id.history);

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BMIHistory.class);
                startActivity(intent);
            }
        });
    } // end onCreate

    private String loadBmiResult() {
        SharedPreferences sp = getSharedPreferences("bmi", Context.MODE_PRIVATE);
        return  sp.getString("bmi", "not found");
    }

    private void saveBMIResult(double weight, double bmi, String result) {

        NumberFormat fmt = new DecimalFormat("#,##0.00");

        SharedPreferences sp = getSharedPreferences("bmi", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
        String date = sdf.format(new Date());

        String oldBmiResult = loadBmiResult();
        String bmiResult = date + "|" + fmt.format(weight) + "|" + fmt.format(bmi) + "|" + result;

        if(! oldBmiResult.equals("not found")) {
            bmiResult = oldBmiResult + ";" + bmiResult;
        }

        spEditor.putString("bmi", bmiResult);
        spEditor.commit();
    }
} // end MainActivity

class DecimalDigitsInputFilter implements InputFilter {
    private Pattern mPattern;

    DecimalDigitsInputFilter(int digits, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digits - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) +
                "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);

        if(!matcher.matches())
            return "";
        return null;
    }
}

