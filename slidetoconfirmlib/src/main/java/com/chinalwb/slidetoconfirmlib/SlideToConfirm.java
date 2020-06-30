package com.chinalwb.slidetoconfirmlib;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class SlideToConfirm extends RelativeLayout {

    private Context mContext;

    private final int DEFAULT_BORDER_WIDTH = 2;
    private final int DEFAULT_BORDER_RADIUS = 8;
    private final int DEFAULT_SLIDER_BACKGROUND_COLOR = Color.TRANSPARENT;
    private final int DEFAULT_SLIDER_COLOR = Color.parseColor("#484EAA");
    private final int DEFAULT_SLIDER_WIDTH = (int) Util.dp2px(60);
    private final String DEFAULT_SLIDER_LOTTIE = "slide_right.json";
    private final int DEFAULT_RESET_DURATION = 300;
    private final int DEFAULT_VIBRATION_DURATION = 50;

    private final String DEFAULT_ENGAGED_TEXT = "Slide to confirm";
    private final int DEFAULT_ENGAGED_TEXT_SIZE = 17;
    private final int DEFAULT_ENGAGED_TEXT_COLOR = DEFAULT_SLIDER_COLOR;

    private final String DEFAULT_COMPLETED_TEXT = "Confirmed";
    private final int DEFAULT_COMPLETED_TEXT_SIZE = 17;
    private final int DEFAULT_COMPLETED_TEXT_COLOR = Color.WHITE;

    // Border
    private float mBorderWidth;
    private float mBorderRadius;
    private float[] mBorderCornerRadii = {mBorderRadius, mBorderRadius, mBorderRadius, mBorderRadius};

    // Slider anchor
    private int mSliderBackgroundColor;
    private int mSliderColor;
    private int mSliderWidth;
    private int mSliderImageWidth; // Not in real use for now, simply equals mSliderWidth
    private String mSliderLottie;
    private int mSliderImageResId;
    private int mVibrationDuration;
    private int mSliderResetDuration;
    private float mSliderThreshold;


    // Engaged text
    private String mEngagedText;
    private float mEngagedTextSize;
    private int mEngagedTextColor;
    private Typeface mEngagedTextTypeFace;

    // Confirmed
    private String mCompletedText;
    private float mCompletedTextSize;
    private int mCompletedTextColor;
    private Typeface mCompletedTextTypeFace;



    private TextView mEngagedTextView;
    private TextView mCTA;
    private RelativeLayout mSwipedView;
    private View mSlider;

    private int mTotalWidth;

    private int mDownX;
    private int mDeltaX;

    private boolean mResetting;
    private boolean mStartDrag;
    private boolean mUnlocked;

    private ISlideListener slideListener;

    public SlideToConfirm(Context context) {
        super(context);
    }

    public SlideToConfirm(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideToConfirm(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideToConfirm, defStyleAttr, 0);
        mSliderLottie = ta.getString(R.styleable.SlideToConfirm_slider_lottie);
        if (TextUtils.isEmpty(mSliderLottie)) {
            mSliderLottie = DEFAULT_SLIDER_LOTTIE;
        }
        int sliderImageResId = ta.getResourceId(R.styleable.SlideToConfirm_slider_image, 0);
        if (sliderImageResId != 0) {
            mSliderImageResId = sliderImageResId;
        }

        mSliderBackgroundColor = ta.getColor(R.styleable.SlideToConfirm_slider_background_color, DEFAULT_SLIDER_BACKGROUND_COLOR);
        mSliderColor = ta.getColor(R.styleable.SlideToConfirm_slider_color, DEFAULT_SLIDER_COLOR);
        mSliderWidth = (int) ta.getDimension(R.styleable.SlideToConfirm_slider_width, DEFAULT_SLIDER_WIDTH);
        mSliderWidth = mSliderWidth >= DEFAULT_SLIDER_WIDTH ? mSliderWidth : DEFAULT_SLIDER_WIDTH;
        mSliderImageWidth = mSliderWidth;
        mSliderResetDuration = ta.getInteger(R.styleable.SlideToConfirm_slider_reset_duration, DEFAULT_RESET_DURATION);
        if (mSliderResetDuration < 0) {
            mSliderResetDuration = DEFAULT_RESET_DURATION;
        }
        mVibrationDuration = ta.getInteger(R.styleable.SlideToConfirm_slider_vibration_duration, DEFAULT_VIBRATION_DURATION);
        mSliderThreshold = ta.getDimension(R.styleable.SlideToConfirm_slider_threshold, 0);
        if (mSliderThreshold < 0) {
            mSliderThreshold = 0;
        }

        mBorderWidth = ta.getDimension(R.styleable.SlideToConfirm_border_width, DEFAULT_BORDER_WIDTH);
        mBorderRadius = ta.getDimension(R.styleable.SlideToConfirm_border_radius, DEFAULT_BORDER_RADIUS);
        for (int i = 0; i < 4; i++) {
            mBorderCornerRadii[i] = mBorderRadius;
        }

        mEngagedText = ta.getString(R.styleable.SlideToConfirm_engage_text);
        if (mEngagedText == null) {
            mEngagedText = DEFAULT_ENGAGED_TEXT;
        }
        mEngagedTextColor = ta.getColor(R.styleable.SlideToConfirm_engage_text_color, DEFAULT_ENGAGED_TEXT_COLOR);
        mEngagedTextSize = ta.getDimension(R.styleable.SlideToConfirm_engage_text_size, DEFAULT_ENGAGED_TEXT_SIZE);
        int typefaceResId = ta.getResourceId(R.styleable.SlideToConfirm_engaged_text_font, -1);
        if (typefaceResId != -1) {
            try {
                mEngagedTextTypeFace = ResourcesCompat.getFont(context, typefaceResId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mCompletedText = ta.getString(R.styleable.SlideToConfirm_completed_text);
        if (mCompletedText == null) {
            mCompletedText = DEFAULT_COMPLETED_TEXT;
        }
        mCompletedTextColor = ta.getColor(R.styleable.SlideToConfirm_completed_text_color, DEFAULT_COMPLETED_TEXT_COLOR);
        mCompletedTextSize = ta.getDimension(R.styleable.SlideToConfirm_completed_text_size, DEFAULT_COMPLETED_TEXT_SIZE);
        typefaceResId = ta.getResourceId(R.styleable.SlideToConfirm_completed_text_font, -1);
        if (typefaceResId != -1) {
            try {
                mCompletedTextTypeFace = ResourcesCompat.getFont(context, typefaceResId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ta.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                init();
                return true;
            }
        });
        handler.sendEmptyMessage(0);
    }

    private void addSwipedView() {
        mSwipedView = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(mSliderWidth, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mSwipedView.setLayoutParams(layoutParams);

        // set bg
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(mSliderColor);
        float[] cornerRadii = new float[8];
        int index = 0;
        for (float r : mBorderCornerRadii) {
            cornerRadii[index++] = r;
            cornerRadii[index++] = r;
        }
        bg.setCornerRadii(cornerRadii);
        mSwipedView.setBackground(bg);

        // CTA text view
        mCTA = new TextView(mContext);
        RelativeLayout.LayoutParams ctaLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mCTA.setLayoutParams(ctaLayoutParams);
        mCTA.setGravity(Gravity.CENTER);
        mCTA.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCompletedTextSize);
        mCTA.setTextColor(mCompletedTextColor);
        if (mCompletedTextTypeFace != null) {
            mCTA.setTypeface(mCompletedTextTypeFace);
        }
        mSwipedView.addView(mCTA);

        // slider
        mSlider = getSlider();
        mSwipedView.addView(mSlider);

        this.addView(mSwipedView);
    }

    private View getSlider() {
        ImageView sliderView;
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(mSliderImageWidth, LayoutParams.MATCH_PARENT);
        if (mSliderImageResId != 0) {
            sliderView = new ImageView(mContext);
            sliderView.setImageResource(mSliderImageResId);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        } else {
            sliderView = new LottieAnimationView(mContext);
            ((LottieAnimationView) sliderView).setAnimation(mSliderLottie);
            ((LottieAnimationView) sliderView).setRepeatCount(LottieDrawable.INFINITE);
            ((LottieAnimationView) sliderView).playAnimation();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        sliderView.setLayoutParams(layoutParams);
        return sliderView;
    }

    private void addEngagedTextView() {
        mEngagedTextView = new TextView(mContext);
        int width = getMeasuredWidth() - mSliderWidth;
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(width, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mEngagedTextView.setLayoutParams(layoutParams);

        mEngagedTextView.setGravity(Gravity.CENTER);
        mEngagedTextView.setText(mEngagedText);
        mEngagedTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mEngagedTextSize);
        mEngagedTextView.setTextColor(mEngagedTextColor);
        if (mEngagedTextTypeFace != null) {
            mEngagedTextView.setTypeface(mEngagedTextTypeFace);
        }

        this.addView(mEngagedTextView);
    }

    private void init() {
        mContext = this.getContext();
        mTotalWidth = getMeasuredWidth();
        addEngagedTextView();
        addSwipedView();

        // bg
        this.setBackgroundResource(R.drawable.stc_bg);
        GradientDrawable bg = (GradientDrawable) this.getBackground();
        bg.setStroke((int) mBorderWidth, mSliderColor);
        bg.setColor(mSliderBackgroundColor);


        // bg corners
        float[] cornerRadii = new float[8];
        int index = 0;
        for (float r : mBorderCornerRadii) {
            cornerRadii[index++] = r;
            cornerRadii[index++] = r;
        }
        bg.setCornerRadii(cornerRadii);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mUnlocked || mResetting) {
            return false;
        }
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN
                && inSliderArea(ev)) {
            mDownX = (int) ev.getX();
            mStartDrag = true;
            Log.w("XX", "onInterceptTouchEvent: true");
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mStartDrag || mUnlocked) {
            return true;
        }

        boolean reachEnd = false;

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            requestDisallowInterceptTouchEvent(true);
            notifySliderStart();
        } else if (action == MotionEvent.ACTION_MOVE) {
            int moveX = (int) ev.getX();
            mDeltaX += moveX - mDownX;
            Log.w("XX", "deltaX == " + mDeltaX);
            mDownX = moveX;
            int left = mDeltaX;

            notifySliderMove();

            if (left + mSliderWidth >= mTotalWidth) {
                left = mTotalWidth - mSliderWidth;
            } else if (left < 0) {
                left = 0;
            }

            RelativeLayout.LayoutParams swipedLayoutParams = (RelativeLayout.LayoutParams) mSwipedView.getLayoutParams();
            swipedLayoutParams.width = left + mSliderWidth;
            mSwipedView.setLayoutParams(swipedLayoutParams);
        } else if (action == MotionEvent.ACTION_UP) {
            if (mDeltaX + mSliderWidth >= mTotalWidth - mSliderThreshold) {
                reachEnd = true;
            }
            if (reachEnd) {
                setUnlockedStatus();
                return true;
            }

            resetSlider();
        }

        performClick();

        return true;
    }

    private void resetSlider() {
        mDownX = 0;
        mDeltaX = 0;
        mStartDrag = false;
        mResetting = true;
        final float translationXNow = mSwipedView.getWidth() - mSliderWidth;
        mSlider.animate().translationX(0)
                .setDuration(mSliderResetDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mResetting = false;
                    }
                })
                .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Object animatedValue = animation.getAnimatedValue();
                        if (animatedValue instanceof Float) {
                            float percentage = (Float) animatedValue;
                            float width = translationXNow * (1 - percentage) + mSliderWidth;
                            RelativeLayout.LayoutParams swipedLayoutParams = (RelativeLayout.LayoutParams) mCTA.getLayoutParams();
                            swipedLayoutParams.width = (int) width;
                            mSwipedView.setLayoutParams(swipedLayoutParams);
                        }
                    }
                })
                .start();

         notifySliderCancel();
    }

    private void setUnlockedStatus() {
        RelativeLayout.LayoutParams swipedLayoutParams = (RelativeLayout.LayoutParams) mSwipedView.getLayoutParams();
        swipedLayoutParams.width = mTotalWidth;
        mSwipedView.setLayoutParams(swipedLayoutParams);

        mUnlocked = true;
        handleVibration();
        mEngagedTextView.setVisibility(View.GONE);
        mSlider.setVisibility(View.GONE);
        mCTA.setText(mCompletedText);
        mCTA.setVisibility(View.VISIBLE);

        notifySliderDone();
    }

    public void reset() {
        mDownX = 0;
        mDeltaX = 0;
        mUnlocked = false;
        mStartDrag = false;

        RelativeLayout.LayoutParams swipedLayoutParams = (RelativeLayout.LayoutParams) mSwipedView.getLayoutParams();
        swipedLayoutParams.width = mSliderWidth;
        mSwipedView.setLayoutParams(swipedLayoutParams);

        mEngagedTextView.setVisibility(View.VISIBLE);
        mSlider.setVisibility(View.VISIBLE);
        mCTA.setVisibility(View.GONE);
    }

    public void setEngageText(String engageText) {
        if (TextUtils.isEmpty(engageText)) {
            return;
        }
        mEngagedText = engageText;
        if (mEngagedTextView != null) {
            mEngagedTextView.setText(engageText);
        }
    }

    public void setCompletedText(String completedText) {
        if (TextUtils.isEmpty(completedText)) {
            return;
        }
        mCompletedText = completedText;
        if (mCTA != null) {
            mCTA.setText(completedText);
        }
    }

    private void handleVibration() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.VIBRATE) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Vibrator vibrator = (android.os.Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                    VibrationEffect.createOneShot(mVibrationDuration, VibrationEffect.DEFAULT_AMPLITUDE)
            );
        } else {
            vibrator.vibrate(mVibrationDuration);
        }
    }

    private boolean inSliderArea(MotionEvent ev) {
        float x = ev.getX();
        return 0 < x && x < mSliderWidth;
    }


    /* -------------------------------- *
     * Slider callbacks
     * -------------------------------- */

    private void notifySliderStart() {
        if (slideListener != null) {
            slideListener.onSlideStart();
        }
    }

    private void notifySliderMove() {
        float percent = (float) mDeltaX / (float) mTotalWidth;
        if (null != slideListener) {
            slideListener.onSlideMove(percent);
        }
    }

    private void notifySliderCancel() {
        if (slideListener != null) {
            slideListener.onSlideCancel();
        }
    }

    private void notifySliderDone() {
        if (slideListener != null) {
            slideListener.onSlideDone();
        }
    }

    public ISlideListener getSlideListener() {
        return slideListener;
    }

    public void setSlideListener(ISlideListener slideListener) {
        this.slideListener = slideListener;
    }
}
