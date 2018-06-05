package com.example.dhf.duringview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 仿照饿了吗实现购物车效果
 */

public class Main10Activity extends AppCompatActivity {

    private Button button;
    private int[] startLocation = new int[2];// 动画开始位置
    private ImageView imageView;
    private ViewGroup anim_mask_layout;
    private Button button2;
    private int[] endlocation = new int[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        button = ((Button) findViewById(R.id.button1));
        button2 = ((Button) findViewById(R.id.buton2));

        button.setOnClickListener((v) -> {
            v.getLocationOnScreen(startLocation);
            imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.check_background_round);

            setAnim(imageView, startLocation);
        });
    }

    private void setAnim(View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);
        /**设置动画的开始位置*/
        final View view = addViewToAnimlayout(v, startLocation);
        button2.getLocationOnScreen(endlocation);// 动画结束位置
        int endx = 0 - startLocation[0] + 40;
        int endy = endlocation[1] - startLocation[1];

        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endx, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endy);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        final AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);

        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
                //    Log.e("动画","asdasdasdasd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                set.cancel();
                animation.cancel();
            }
        });


    }

    /**
     * 创建一个图层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootview = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootview.addView(animLayout);

        return rootview;
    }

    /**
     * 设置view的开始位置
     */
    private View addViewToAnimlayout(final View view, int[] startLocation) {
        int x = startLocation[0];
        int y = startLocation[1];
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;

    }
}
