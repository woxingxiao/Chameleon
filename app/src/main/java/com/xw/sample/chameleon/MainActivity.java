package com.xw.sample.chameleon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xw.repo.chameleon.ChameleonFrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ChameleonFrameLayout mChameleonLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChameleonLayout = (ChameleonFrameLayout) findViewById(R.id.layout);
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

        if (mChameleonLayout != null) {
            mChameleonLayout.setTargetColorString("#e99d3c", 3000);
            mChameleonLayout.startChangingColor();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                mChameleonLayout.setTargetColorRes(R.color.blue);
                break;
            case R.id.button1:
                mChameleonLayout.setTargetColorRes(R.color.red);
                break;
            case R.id.button2:
                mChameleonLayout.setTargetColorRes(R.color.yellow);
                break;
            case R.id.button3:
                mChameleonLayout.setTargetColorRes(R.color.colorPrimary);
                break;
        }
        mChameleonLayout.startChangingColor();
    }
}
