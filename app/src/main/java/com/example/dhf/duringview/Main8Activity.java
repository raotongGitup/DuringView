package com.example.dhf.duringview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.dhf.duringview.zhibodianzan.HungryView;
import com.example.dhf.duringview.zhibodianzan.LoveLayout;

public class Main8Activity extends AppCompatActivity {

    private Button button;
    private LoveLayout loveLayout;
    private HungryView hungryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        button = ((Button) findViewById(R.id.button));
        hungryView = ((HungryView) findViewById(R.id.hungryview));
//        loveLayout = ((LoveLayout) findViewById(R.id.lovelayout));
        button.setOnClickListener((v) ->{
//            loveLayout.addloveview();
            hungryView.intiView();
        });
    }
}
