package com.xw.sample.chameleon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xw.repo.chameleon.ChameleonLinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChameleonLinearLayout layout = (ChameleonLinearLayout) findViewById(R.id.layout);

        if (layout != null) {
            layout.setTargetColorString("#F44336");
            layout.startChangingColor();
        }
    }
}
