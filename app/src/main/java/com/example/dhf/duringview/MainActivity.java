package com.example.dhf.duringview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.dhf.duringview.wavesView.LD_WaveView;
/**
 * 安卓中波浪图的画法
 */
public class MainActivity extends AppCompatActivity {


    private LD_WaveView ldWaveView;
    private int progrees = 0;//进度private
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progrees == 100) progrees = 0;
            ldWaveView.setmProgress(progrees++);
            handler.sendEmptyMessageDelayed(0, 100);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ldWaveView = ((LD_WaveView) findViewById(R.id.ld_waveview));
        ldWaveView.setCircle(true);
        ldWaveView.setmProgress(0);
        handler.sendEmptyMessageDelayed(0, 10);
    }
}
