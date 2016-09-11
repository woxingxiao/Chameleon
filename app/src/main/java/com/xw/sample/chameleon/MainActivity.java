package com.xw.sample.chameleon;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.xw.repo.chameleon.Chameleon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout mFrameLayout;
    private Chameleon mChameleon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout = (FrameLayout) findViewById(R.id.layout);
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

        mChameleon = new Chameleon()
                .fromColor(((ColorDrawable) mFrameLayout.getBackground()).getColor())
                .toColor(Color.parseColor("#f9bb05"))
                .duration(2000)
                .interpolator(new AccelerateInterpolator())
                .setOnColorChangedListener(new Chameleon.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int color, boolean isChanging) {
                        mFrameLayout.setBackgroundColor(color);
                    }
                });
        mChameleon.startChangingColor();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                mChameleon.toColor(ContextCompat.getColor(this, R.color.blue));
                break;
            case R.id.button1:
                mChameleon.toColor(ContextCompat.getColor(this, R.color.red));
                break;
            case R.id.button2:
                mChameleon.toColor(ContextCompat.getColor(this, R.color.yellow));
                break;
            case R.id.button3:
                mChameleon.toColor(ContextCompat.getColor(this, R.color.colorPrimary));
                break;
        }

        mChameleon.startChangingColor();
    }
}
