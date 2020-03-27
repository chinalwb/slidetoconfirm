package com.chinalwb.slidetoconfirm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.chinalwb.slidetoconfirmlib.ISlideListener;
import com.chinalwb.slidetoconfirmlib.SlideToConfirm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlideToConfirm slideToConfirm = findViewById(R.id.slide_to_confirm);
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
            }
        });
    }
}
