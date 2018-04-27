package com.example.dhf.duringview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dhf.duringview.viewCircle.ElementBean;
import com.example.dhf.duringview.viewCircle.RadarView;

import java.util.ArrayList;
import java.util.List;

/**
 * 蜘蛛卫星图
 */
public class Main3Activity extends AppCompatActivity {
    private List<ElementBean> mElementBeanList = new ArrayList<>();
    private RadarView radarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        radarView = ((RadarView) findViewById(R.id.radarview));

        mElementBeanList.add(new ElementBean("打野",20));
        mElementBeanList.add(new ElementBean("上单",70));
        mElementBeanList.add(new ElementBean("中路",90));
        mElementBeanList.add(new ElementBean("下路",30));
        mElementBeanList.add(new ElementBean("辅助",50));
        radarView.initData(mElementBeanList);
        radarView.initMAX(100);
    }
}
