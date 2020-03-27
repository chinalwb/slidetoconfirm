//package com.chinalwb.slidetoaction;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.ValueAnimator;
//import android.content.Context;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//
//public class SlidingToConfirmView extends FrameLayout {
//
//    public static final float THRESHOLD = Util.dp2px(5);
//    private TextView mCTA;
//    private View mSlider;
//
//    private int mTotalWidth;
//    private int mSliderWidth;
//
//    private int mDownX;
//    private int mDeltaX;
//
//    private boolean mStartDrag;
//    private boolean mUnlocked;
//
//
//    public SlidingToConfirmView(Context context) {
//        super(context);
//    }
//
//    public SlidingToConfirmView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public SlidingToConfirmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        init();
//    }
//
//    private void init() {
//        mSlider = findViewById(R.id.stc_slider);
//        mCTA = findViewById(R.id.stc_cta);
//
//        mSliderWidth = mSlider.getMeasuredWidth();
//        Log.w("XX", "mSliderWidth == " + mSliderWidth);
//        mTotalWidth = getMeasuredWidth();
//        Log.w("XX", "mTotalWidth == " + mTotalWidth);
//
//        mCTA.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (mUnlocked) {
//            return false;
//        }
//        int action = ev.getAction();
//        if (action == MotionEvent.ACTION_DOWN
//                && inSliderArea(ev)) {
//            mDownX = (int) ev.getX();
//            mStartDrag = true;
//            Log.w("XX", "onInterceptTouchEvent: true");
//            return true;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
//
//
//    @Override
//    public boolean performClick() {
//        if (mUnlocked) {
//            return mCTA.performClick();
//        }
//        return super.performClick();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (!mStartDrag || mUnlocked) {
//            return true;
//        }
//        boolean reachEnd = false;
//
//        int action = ev.getAction();
//        if (action == MotionEvent.ACTION_MOVE) {
//            int moveX = (int) ev.getX();
//            mDeltaX += moveX - mDownX;
//            Log.w("XX", "deltaX == " + mDeltaX);
//            mDownX = moveX;
//            int left = mDeltaX;
//            if (left + mSliderWidth >= mTotalWidth) {
//                left = mTotalWidth - mSliderWidth;
//                reachEnd = true;
//            } else if (left < 0) {
//                left = 0;
//            } else {
//                mSlider.setBackgroundResource(R.drawable.bg_sliding_slider_dragging);
//            }
//            mSlider.setTranslationX(left);
//            if (reachEnd) {
//                mSlider.setBackgroundResource(R.drawable.bg_sliding_slider_end);
//            } else if (left == 0) {
//                mSlider.setBackgroundResource(R.drawable.bg_sliding_slider_default);
//            }
//
//            FrameLayout.LayoutParams ctaLayoutParams = (LayoutParams) mCTA.getLayoutParams();
//            ctaLayoutParams.width = left;
//            mCTA.setLayoutParams(ctaLayoutParams);
//        } else if (action == MotionEvent.ACTION_UP) {
//            if (mDeltaX + mSliderWidth >= mTotalWidth - THRESHOLD) {
//                reachEnd = true;
//            }
//            if (reachEnd) {
//                setUnlockedStatus();
//                return true;
//            }
//
//            resetStatus();
//        }
//
//        return true;
//    }
//
//    private void resetStatus() {
//        mDownX = 0;
//        mDeltaX = 0;
//        mStartDrag = false;
//        final float translationXNow = mSlider.getTranslationX();
//        mSlider.animate().translationX(0)
//                .setDuration(300)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        mSlider.setBackgroundResource(R.drawable.bg_sliding_slider_default);
//                    }
//                })
//                .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        Object animatedValue = animation.getAnimatedValue();
//                        if (animatedValue instanceof Float) {
//                            float percentage = (Float) animatedValue;
//                            float width = translationXNow * (1 - percentage);
//                            LayoutParams ctaLayoutParams = (LayoutParams) mCTA.getLayoutParams();
//                            ctaLayoutParams.width = (int) width + 1;
//                            mCTA.setLayoutParams(ctaLayoutParams);
//                        }
//                    }
//                })
//                .start();
//    }
//
//    private void setUnlockedStatus() {
//        mUnlocked = true;
//        LayoutParams ctaLayoutParams = (LayoutParams) mCTA.getLayoutParams();
//        ctaLayoutParams.width = mTotalWidth;
//        mCTA.setLayoutParams(ctaLayoutParams);
//
//        mSlider.setVisibility(View.GONE);
//        mCTA.setText("Confirmed and saved");
//        mCTA.setBackgroundResource(R.drawable.bg_sliding_cta_end);
//
//        mCTA.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCTA.performClick();
//            }
//        }, 800);
//    }
//
//    private boolean inSliderArea(MotionEvent ev) {
//        float x = ev.getX();
//        float y = ev.getY();
//        Log.w("XX", "inSliderArea: [x,y] = [" + x + "," + y + "]");
//        return 0 < x && x < mSliderWidth;
//    }
//}
