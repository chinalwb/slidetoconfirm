package com.chinalwb.slidetoconfirm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.chinalwb.slidetoconfirmlib.ISlideListener;
import com.chinalwb.slidetoconfirmlib.SlideToConfirm;

public class MainActivity extends AppCompatActivity {

    public static final String START_WORKING = "STEP #1";
    public static final String STOP_WORKING = "STEP #2";
    private String engageTextNow = START_WORKING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultSample();
        sampleForTwoSteps();
    }

    private void defaultSample() {
        final SlideToConfirm slideToConfirm = findViewById(R.id.slide_to_confirm_1);
        slideToConfirm.setSlideListener(new ISlideListener() {
            @Override
            public void onSlideStart() {
                Log.w("XX", "on start !! ");
            }

            @Override
            public void onSlideMove(float percent) {
                Log.w("XX", "on move !! == " + percent);
            }

            @Override
            public void onSlideCancel() {
                Log.w("XX", "on cancel !! ");
            }

            @Override
            public void onSlideDone() {
                Log.w("XX", "on Done!!");

                slideToConfirm.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slideToConfirm.reset();
                    }
                }, 500);
            }
        });
    }

    private void sampleForTwoSteps() {
        final SlideToConfirm slideToConfirm = findViewById(R.id.slide_to_confirm_2);
        slideToConfirm.setEngageText(engageTextNow);
        slideToConfirm.setSlideListener(new ISlideListener() {
            @Override
            public void onSlideStart() {
                Log.w("XX", "on start !! ");
            }

            @Override
            public void onSlideMove(float percent) {
                Log.w("XX", "on move !! == " + percent);
            }

            @Override
            public void onSlideCancel() {
                Log.w("XX", "on cancel !! ");
            }

            @Override
            public void onSlideDone() {
                Log.w("XX", "on Done!!");
                if (engageTextNow.equals(START_WORKING)) {
                    slideToConfirm.setCompletedText("Processing...");
                } else {
                    slideToConfirm.setCompletedText("Finishing...");
                }

                slideToConfirm.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (engageTextNow.equals(START_WORKING)) {
                            engageTextNow = STOP_WORKING;
                        } else {
                            engageTextNow = START_WORKING;
                        }
                        slideToConfirm.setEngageText(engageTextNow);
                        slideToConfirm.reset();
                    }
                }, 500);
            }
        });
    }
}
