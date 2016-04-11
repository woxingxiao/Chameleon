package com.xw.repo.chameleon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * ChameleonRelativieLayout
 * Created by woxingxiao on 2016-04-10.
 */
public class ChameleonRelativieLayout extends RelativeLayout {

    private static final int MSG_WHAT = 0x410;

    private int[] currentColorRGB = new int[3];
    private int[] targetColorRGB = new int[3];
    private long mDuration = 3000;
    private int mFraction;

    public ChameleonRelativieLayout(Context context) {
        this(context, null);
    }

    public ChameleonRelativieLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Drawable background = getBackground();
            if (background instanceof ColorDrawable) {
                int color = ((ColorDrawable) background).getColor();
                getRGBColor(currentColorRGB, color);
            }
        } else {
            currentColorRGB[0] = 0xff;
            currentColorRGB[1] = 0xff;
            currentColorRGB[2] = 0xff;
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_WHAT && mFraction != 0) {
                if (currentColorRGB[0] < targetColorRGB[0]) {
                    currentColorRGB[0]++;
                } else if (currentColorRGB[0] > targetColorRGB[0]) {
                    currentColorRGB[0]--;
                }
                if (currentColorRGB[1] < targetColorRGB[1]) {
                    currentColorRGB[1]++;
                } else if (currentColorRGB[1] > targetColorRGB[1]) {
                    currentColorRGB[1]--;
                }
                if (currentColorRGB[2] < targetColorRGB[2]) {
                    currentColorRGB[2]++;
                } else if (currentColorRGB[2] > targetColorRGB[2]) {
                    currentColorRGB[2]--;
                }
                if (currentColorRGB[0] != targetColorRGB[0] || currentColorRGB[1] !=
                        targetColorRGB[1] || currentColorRGB[2] != targetColorRGB[2]) {
                    setBackgroundColor(Color.rgb(currentColorRGB[0], currentColorRGB[1], currentColorRGB[2]));
                    mHandler.sendEmptyMessageDelayed(MSG_WHAT, mDuration / mFraction);
                }
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * @param color 初始颜色int值，如 0xffffff
     */
    public void setInitColor(int color) {
        getRGBColor(currentColorRGB, color);
        getFraction();
    }

    /**
     * @param color 目标颜色int值，如 0xffffff
     */
    public void setTargetColor(int color) {
        getRGBColor(targetColorRGB, color);
        getFraction();
    }

    /**
     * @param color    目标颜色int值，如 0xffffff
     * @param duration 变换时长，单位毫秒
     */
    public void setTargetColor(int color, long duration) {
        getRGBColor(targetColorRGB, color);
        this.mDuration = duration;
        getFraction();
    }

    /**
     * @param colorRes 初始颜色资源id，如 R.color.red
     */
    public void setInitColorRes(int colorRes) {
        int color = getResources().getColor(colorRes);
        getRGBColor(currentColorRGB, color);
        getFraction();
    }

    /**
     * @param colorRes 目标颜色资源id，如 R.color.red
     */
    public void setTargetColorRes(int colorRes) {
        int color = getResources().getColor(colorRes);
        getRGBColor(targetColorRGB, color);
        getFraction();
    }

    /**
     * @param colorRes 目标颜色资源id，如 R.color.red
     * @param duration 变换时长，单位毫秒
     */
    public void setTargetColorRes(int colorRes, long duration) {
        int color = getResources().getColor(colorRes);
        this.mDuration = duration;
        getRGBColor(targetColorRGB, color);
        getFraction();
    }

    /**
     * @param colorString 目标颜色字符串表示方式，如 “#ffffff”
     */
    public void setInitColorString(String colorString) {
        getRGBColor(currentColorRGB, Color.parseColor(colorString));
        getFraction();
    }

    /**
     * @param colorString 目标颜色字符串表示方式，如 “#ffffff”
     */
    public void setTargetColorString(String colorString) {
        getRGBColor(targetColorRGB, Color.parseColor(colorString));
        getFraction();
    }

    /**
     * @param colorString 目标颜色字符串表示方式，如 “#ffffff”
     * @param duration    变换时长，单位毫秒
     */
    public void setTargetColorString(String colorString, long duration) {
        getRGBColor(targetColorRGB, Color.parseColor(colorString));
        this.mDuration = duration;
        getFraction();
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public void startChangingColor() {
        mHandler.sendEmptyMessage(MSG_WHAT);
    }

    private void getRGBColor(int[] colorArray, int color) {
        colorArray[0] = color >> 16 & 0xff;
        colorArray[1] = color >> 8 & 0xff;
        colorArray[2] = color & 0xff;
    }

    private void getFraction() {
        int a = Math.abs(targetColorRGB[0] - currentColorRGB[0]);
        int b = Math.abs(targetColorRGB[1] - currentColorRGB[1]);
        int c = Math.abs(targetColorRGB[2] - currentColorRGB[2]);
        mFraction = Math.max(Math.max(a, b), c);
    }
}
