package com.fuad.progressbarclock;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

// App Name : Progress Bar Clock
// Coder    : Khalid Saifullah Fuad
// Date     : 01-May-2021

public class MainActivity extends AppCompatActivity {

    private ProgressBar hourProgressBar, minuteProgressBar, secondProgressBar;
    private TextView tvHour, tvMinute, tvSecond;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        hourProgressBar = findViewById(R.id.hour_progress_bar);
        minuteProgressBar = findViewById(R.id.minute_progress_bar);
        secondProgressBar = findViewById(R.id.second_progress_bar);
        tvHour = findViewById(R.id.hour);
        tvMinute = findViewById(R.id.minute);
        tvSecond = findViewById(R.id.second);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                int hourProgress = (int) ((hour / 24.0) * 100);
                int minuteProgress = (int) ((minute / 60.0) * 100);
                int secondProgress = (int) ((second / 60.0) * 100);

                if (flag) {
                    hour = calendar.get(Calendar.HOUR) == 0 ? 12 : calendar.get(Calendar.HOUR);
                    hourProgress = (int) ((hour / 12.0) * 100);
                }

                tvHour.setText(String.format("%s", String.format(Locale.getDefault(), "%02d", hour)));
                tvMinute.setText(String.format("%s", String.format(Locale.getDefault(), "%02d", minute)));
                tvSecond.setText(String.format("%s", String.format(Locale.getDefault(), "%02d", second)));

                hourProgressBar.setProgress(hourProgress);
                minuteProgressBar.setProgress(minuteProgress);
                secondProgressBar.setProgress(secondProgress);

                handler.postDelayed(this, 500);

            }
        });


        findViewById(R.id.hour_layout).setOnClickListener(view -> flag = !flag);
    }
}