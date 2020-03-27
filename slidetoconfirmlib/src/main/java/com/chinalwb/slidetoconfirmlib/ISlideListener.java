package com.chinalwb.slidetoconfirmlib;

public interface ISlideListener {

    void onSlideStart();

    void onSlideMove(float percent);

    void onSlideCancel();

    void onSlideDone();
}
