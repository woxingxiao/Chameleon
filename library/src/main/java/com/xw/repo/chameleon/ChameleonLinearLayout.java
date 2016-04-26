package com.xw.repo.chameleon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * ChameleonLinearLayout
 * Created by woxingxiao on 2016-04-10.
 */
public class ChameleonLinearLayout extends LinearLayout {

    private static final int MSG_WHAT = 0x410;

    private long mDuration = 3000;
    private ChameleonHelper mChameleonHelper;

    public ChameleonLinearLayout(Context context) {
        this(context, null);
    }

    public ChameleonLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mChameleonHelper = new ChameleonHelper();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Drawable background = getBackground();
            if (background instanceof ColorDrawable) {
                int color = ((ColorDrawable) background).getColor();
                mChameleonHelper.setCurrentColor(color);
            }
        } else {
            mChameleonHelper.setCurrentColor(0xffffff);
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_WHAT && mChameleonHelper != null && mChameleonHelper
                    .isColorChanging() && mChameleonHelper.getFraction() != 0) {
                setBackgroundColor(mChameleonHelper.getColor());
                mHandler.sendEmptyMessageDelayed(MSG_WHAT, mDuration / mChameleonHelper.getFraction());
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
        mChameleonHelper.setCurrentColor(color);
    }

    /**
     * @param color 目标颜色int值，如 0xffffff
     */
    public void setTargetColor(int color) {
        mChameleonHelper.setTargetColor(color);
    }

    /**
     * @param colorRes 初始颜色资源id，如 R.color.red
     */
    public void setInitColorRes(int colorRes) {
        mChameleonHelper.setCurrentColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * @param colorRes 目标颜色资源id，如 R.color.red
     */
    public void setTargetColorRes(int colorRes) {
        mChameleonHelper.setTargetColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * @param colorString 目标颜色字符串表示方式，如 “#ffffff”
     */
    public void setInitColorString(String colorString) {
        mChameleonHelper.setCurrentColor(Color.parseColor(colorString));
    }

    /**
     * @param colorString 目标颜色字符串表示方式，如 “#ffffff”
     */
    public void setTargetColorString(String colorString) {
        mChameleonHelper.setTargetColor(Color.parseColor(colorString));
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public void startChangingColor() {
        mHandler.sendEmptyMessage(MSG_WHAT);
    }

}
