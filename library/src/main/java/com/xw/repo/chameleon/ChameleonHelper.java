package com.xw.repo.chameleon;

import android.graphics.Color;

/**
 * ChameleonHelper
 * Created by woxingxiao on 2016-04-26.
 */
public class ChameleonHelper {

    private int[] currentColorRGB = new int[3];
    private int[] targetColorRGB = new int[3];
    private int mFraction = 1;
    private boolean isColorChanging = true;

    public void setCurrentColor(int color) {
        getRGBColor(currentColorRGB, color);
        calculateFraction();
    }

    public void setTargetColor(int color) {
        getRGBColor(targetColorRGB, color);
        calculateFraction();
    }

    public int getColor() {
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

        isColorChanging = currentColorRGB[0] != targetColorRGB[0] || currentColorRGB[1] !=
                targetColorRGB[1] || currentColorRGB[2] != targetColorRGB[2];

        return Color.rgb(currentColorRGB[0], currentColorRGB[1], currentColorRGB[2]);
    }

    public int getFraction() {
        return mFraction;
    }

    public boolean isColorChanging() {
        return isColorChanging;
    }

    private void getRGBColor(int[] colorArray, int color) {
        colorArray[0] = color >> 16 & 0xff;
        colorArray[1] = color >> 8 & 0xff;
        colorArray[2] = color & 0xff;
    }

    private void calculateFraction() {
        int a = Math.abs(targetColorRGB[0] - currentColorRGB[0]);
        int b = Math.abs(targetColorRGB[1] - currentColorRGB[1]);
        int c = Math.abs(targetColorRGB[2] - currentColorRGB[2]);
        mFraction = Math.max(Math.max(a, b), c);
        isColorChanging = mFraction != 0;
    }
}
