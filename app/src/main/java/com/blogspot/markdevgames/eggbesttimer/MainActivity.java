package com.blogspot.markdevgames.eggbesttimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView timeView;
    Button buttonGo;
    CountDownTimer countDownTimer;
    boolean isCounterActive = false;
    MediaPlayer shortTickPlayer;

    public void updateTimer(int secondsLeft) {
        //setting time, important
        int minute = secondsLeft / 60;
        int second = secondsLeft - (minute * 60);
        String secondString = Integer.toString(second);
        if (second <= 9) {
            secondString = "0" + secondString;
        }
        timeView.setText(minute + ":" + secondString);
    }

    public void resetTimer() {
        seekBar.setEnabled(true);
        timeView.setText("1:30");
        seekBar.setProgress(90);
        countDownTimer.cancel();
        buttonGo.setText("GO!");
        isCounterActive = false;
    }

    public void onClickGo(View view) {
        if(isCounterActive) {
            resetTimer();
        } else {
            isCounterActive = true;
            seekBar.setEnabled(false);
            buttonGo.setText("STOP");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                    shortTickPlayer.start();
                }

                @Override
                public void onFinish() {
                    //Log.i("Finish!", "all good");
                    //play music
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.rooster);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();




        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        timeView = findViewById(R.id.timeView);
        buttonGo = findViewById(R.id.buttonGo);
        shortTickPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tickshort);

        seekBar.setMax(600);
        seekBar.setProgress(90);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
