package com.example.dhf.duringview;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dhf.duringview.satelliteSpiderView.PointF;
import com.example.dhf.duringview.satelliteSpiderView.ScroolView;
import com.example.dhf.duringview.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 绘制折线图
 */
public class Main4Activity extends AppCompatActivity {

    private ScroolView scroolView;
    private List<PointF> pointFS = new ArrayList<>();
    private ImageView imageView;
    //定义三级缓存工具类
    ImageLoader loader;
    private Button bu;
    private Button byId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        loader = new ImageLoader(this, "test3");//创建文件夹
        imageView = ((ImageView) findViewById(R.id.imageview));
        bu = ((Button) findViewById(R.id.buton2));
        byId = ((Button) findViewById(R.id.buton3));
        byId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String  path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "test3";
                loader.deleteFolderFile(path,true);

            }
        });
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + "点击imageview");
                loader.loadImage("http://p3.so.qhmsg.com/bdr/326__/t018da60b972e086a1d.jpg", new ImageLoader.ImageLoadListener() {
                    @Override
                    public void loadImage(Bitmap bmp) {
                        imageView.setImageBitmap(bmp);
                    }
                });
            }
        });

//        scroolView = ((ScroolView) findViewById(R.id.scrollView_zx));
//        Random random = new Random();
//        for (int i = 0; i < 30; i++) {
//            PointF f = new PointF();
//            f.x = i + "";
//            f.y = random.nextInt(100);
//            pointFS.add(f);
//        }
//        scroolView.setPointFS(pointFS);

    }
}
