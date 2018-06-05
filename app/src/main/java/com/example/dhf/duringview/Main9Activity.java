package com.example.dhf.duringview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.TextView;

public class Main9Activity extends AppCompatActivity {

    private TextView textView;
    protected static final int UI = 100;
    private String TextDemo = "我是一段测试文字我是一段测试文字我是一段测试文字我是一段测试文字我是一段测试文字我是一段测试文字我是一段测试文字";

    private char[] charArrays;
    private String len = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UI:
                    textView.append(len);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        textView = ((TextView) findViewById(R.id.textview_tv));
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        animationSet.addAnimation(alphaAnimation);
        textView.startAnimation(animationSet);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    charArrays = TextDemo.toCharArray();
                    for (int i = 0; i < charArrays.length; i++) {
                        Thread.sleep(500);
                        len = charArrays[i] + "";
                        handler.sendEmptyMessage(UI);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
