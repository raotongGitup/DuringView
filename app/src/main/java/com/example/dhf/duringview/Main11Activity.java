package com.example.dhf.duringview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dhf.duringview.movementSteps.StepsArcView;

public class Main11Activity extends AppCompatActivity {

    private StepsArcView stepsArcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        stepsArcView = ((StepsArcView) findViewById(R.id.stepsArcview));
        stepsArcView.setData(2000);
    }
}
