package com.xw.repo.chameleon;

import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Color;

/**
 * Chameleon 变色龙
 * <p/>
 * 一个实现在两个色值之间平滑过渡的工具
 * <p/>
 * Created by woxingxiao on 2016-09-11.
 */
public class Chameleon {

    private long mDuration = 1000;
    private TimeInterpolator mInterpolator;
    private OnColorChangedListener mListener;

    private int[] nowColorRGB = new int[3];
    private int[] toColorRGB = new int[3];
    private int which;

    public Chameleon fromColor(int color) {
        getRGBValue(nowColorRGB, color);
        return this;
    }

    public Chameleon toColor(int color) {
        getRGBValue(toColorRGB, color);
        return this;
    }

    public Chameleon duration(long duration) {
        mDuration = duration < 0L ? 0L : duration;
        return this;
    }

    public Chameleon interpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
        return this;
    }

    public Chameleon setOnColorChangedListener(OnColorChangedListener listener) {
        mListener = listener;
        return this;
    }

    public boolean isColorChangingOver() {
        return nowColorRGB[0] == toColorRGB[0] && nowColorRGB[1] == toColorRGB[1] && nowColorRGB[2] == toColorRGB[2];
    }

    public void startChangingColor() {
        if (isColorChangingOver()) {
            return;
        }

        int fromR = nowColorRGB[0];
        int fromG = nowColorRGB[1];
        int fromB = nowColorRGB[2];
        int toR = toColorRGB[0];
        int toG = toColorRGB[1];
        int toB = toColorRGB[2];

        if (Math.abs(fromR - toR) >= Math.max(Math.abs(fromG - toG), Math.abs(fromB - toB))) {
            which = 1;
        } else if (Math.abs(fromG - toG) >= Math.max(Math.abs(fromR - toR), Math.abs(fromB - toB))) {
            which = 2;
        } else {
            which = 3;
        }

        ValueAnimator animatorR = ValueAnimator.ofInt(fromR, toR);
        animatorR.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                nowColorRGB[0] = (int) animation.getAnimatedValue();

                if (which == 1 && mListener != null) {
                    mListener.onColorChanged(Color.rgb(nowColorRGB[0], nowColorRGB[1], nowColorRGB[2]), isColorChangingOver());
                }
            }
        });
        ValueAnimator animatorG = ValueAnimator.ofInt(fromG, toG);
        animatorG.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                nowColorRGB[1] = (int) animation.getAnimatedValue();

                if (which == 2 && mListener != null) {
                    mListener.onColorChanged(Color.rgb(nowColorRGB[0], nowColorRGB[1], nowColorRGB[2]), isColorChangingOver());
                }
            }
        });
        ValueAnimator animatorB = ValueAnimator.ofInt(fromB, toB);
        animatorB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                nowColorRGB[2] = (int) animation.getAnimatedValue();

                if (which == 3 && mListener != null) {
                    mListener.onColorChanged(Color.rgb(nowColorRGB[0], nowColorRGB[1], nowColorRGB[2]), isColorChangingOver());
                }
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mDuration);
        if (mInterpolator != null) {
            animatorSet.setInterpolator(mInterpolator);
        }
        animatorSet.playTogether(animatorR, animatorG, animatorB);
        animatorSet.start();
    }

    private void getRGBValue(int[] colorArray, int color) {
        colorArray[0] = color >> 16 & 0xff;
        colorArray[1] = color >> 8 & 0xff;
        colorArray[2] = color & 0xff;
    }

    public interface OnColorChangedListener {

        void onColorChanged(int color, boolean isChanging);

    }
}
