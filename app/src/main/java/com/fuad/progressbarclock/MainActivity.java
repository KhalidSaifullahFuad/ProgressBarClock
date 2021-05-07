package com.fuad.progressbarclock;

import android.content.res.Configuration;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

// App Name : Progress Bar Clock
// Coder    : Khalid Saifullah Fuad
// Date     : 01-May-2021

public class MainActivity extends AppCompatActivity {

    private ProgressBar hourProgressBar, minuteProgressBar, secondProgressBar;
    private TextView tvHour, tvMinute, tvSecond;
    private RelativeLayout hourLayout, secondLayout;
    private SoundPool soundPool;

    private int tickSound;

    private boolean is12HourClock = true, isSoundOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        hourLayout = findViewById(R.id.hour_layout);
        secondLayout = findViewById(R.id.second_layout);
        hourProgressBar = findViewById(R.id.hour_progress_bar);
        minuteProgressBar = findViewById(R.id.minute_progress_bar);
        secondProgressBar = findViewById(R.id.second_progress_bar);
        tvHour = findViewById(R.id.hour);
        tvMinute = findViewById(R.id.minute);
        tvSecond = findViewById(R.id.second);

        hourLayout.setOnClickListener(view -> is12HourClock = !is12HourClock);
        secondLayout.setOnClickListener(view -> isSoundOn = !isSoundOn);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }

        tickSound = soundPool.load(this,R.raw.tick,1);

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

                if (is12HourClock) {
                    hour = calendar.get(Calendar.HOUR) == 0 ? 12 : calendar.get(Calendar.HOUR);
                    hourProgress = (int) ((hour / 12.0) * 100);
                }

                tvHour.setText(String.format("%s", String.format(Locale.getDefault(), "%02d", hour)));
                tvMinute.setText(String.format("%s", String.format(Locale.getDefault(), "%02d", minute)));
                tvSecond.setText(String.format("%s", String.format(Locale.getDefault(), "%02d", second)));

                hourProgressBar.setProgress(hourProgress);
                minuteProgressBar.setProgress(minuteProgress);
                secondProgressBar.setProgress(secondProgress);

                if(soundPool!=null && isSoundOn) {
                    soundPool.play(tickSound, 1, 1, 0, 0, 1);
                }
                handler.postDelayed(this, 1000);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}