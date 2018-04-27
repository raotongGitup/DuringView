package com.example.dhf.duringview;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dhf.duringview.satelliteSpiderView.PointF;
import com.example.dhf.duringview.satelliteSpiderView.ScroolView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 绘制折线图
 */
public class Main4Activity extends AppCompatActivity {
    private ScroolView scroolView;
    private List<PointF> pointFS = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        scroolView = ((ScroolView) findViewById(R.id.scrollView_zx));
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            PointF f = new PointF();
            f.x = i + "";
            f.y = random.nextInt(100);
            pointFS.add(f);
        }
        scroolView.setPointFS(pointFS);
    }
}
