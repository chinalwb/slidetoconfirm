package com.chinalwb.slidetoconfirmlib;

import android.content.res.Resources;
import android.util.TypedValue;

public class Util {
    public static float dp2px(int dp) {
        return Resources.getSystem().getDisplayMetrics().density * dp;
    }


    public static int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spVal,
                Resources.getSystem().getDisplayMetrics());
    }
}
