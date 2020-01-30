package com.example.sportivo.time_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.sportivo.R;
import com.example.sportivo.ReservationDataStorage;

public class TimeSelect extends AppCompatActivity {

    Button confirmTime;
    TextView plus, minus;
    EditText timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select);

        confirmTime = (Button) findViewById(R.id.confirmTime_btn);
        plus = (TextView) findViewById(R.id.plus);
        minus = (TextView) findViewById(R.id.minus);
        timePicker = (EditText) findViewById(R.id.timePicker);

        //timePicker.setIs24HourView(true);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToHour();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractFromHour();
            }
        });

        confirmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void subtractFromHour() {
        int hour = Integer.parseInt(timePicker.getText().toString());

        if(hour > 1){
            timePicker.setText(String.valueOf(--hour));
            ReservationDataStorage.setTime(getApplicationContext(), hour, ReservationDataStorage.getMinute(), ReservationDataStorage.getSecond());
        }
    }

    private void addToHour() {
        int hour = Integer.parseInt(timePicker.getText().toString());

        if(hour < 22){
            timePicker.setText(String.valueOf(++hour));
            ReservationDataStorage.setTime(getApplicationContext(), hour, ReservationDataStorage.getMinute(), ReservationDataStorage.getSecond());
        }
    }
}
