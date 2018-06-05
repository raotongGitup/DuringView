package com.example.dhf.duringview.zhibodianzan;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dhf.duringview.R;

import static android.content.ContentValues.TAG;

/**
 * Created by ${raotong} on 2018/5/10.
 */

public class HungryView extends RelativeLayout {

    private Drawable drawable;
    private RelativeLayout.LayoutParams params;
    private int mwith;
    private int mhight;
    private ImageView imageView;
    private Context context;
    private PointF[] floats;


    public HungryView(Context context) {
        super(context);
        this.context = context;
    }

    public HungryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //intiView();
    }

    public void intiView() {
        drawable = getResources().getDrawable(R.drawable.check_background_round);
        int with = drawable.getIntrinsicWidth();
        int hight = drawable.getIntrinsicHeight();
        params = new RelativeLayout.LayoutParams(with, hight);
        params.addRule(CENTER_HORIZONTAL, TRUE);
        params.addRule(CENTER_VERTICAL, TRUE);
        imageView = new ImageView(context);
        imageView.setImageDrawable(drawable);
        imageView.setLayoutParams(params);
        addView(imageView);
        final AnimatorSet set = getAnimatorSet(imageView);
        set.setDuration(2000);
        set.start();

    }

    public HungryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mwith = widthMeasureSpec;
//        mhight = heightMeasureSpec;

        initData();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mwith=w;
        mhight=h;
        initData();
    }

    private void initData() {
        floats = new PointF[3];
        floats[0] = new PointF();
        floats[0].x = mwith/4;
        floats[0].y = mhight/2;
        floats[1] = new PointF();
        floats[1].x = mwith/2;
        floats[1].y = mhight/2;
        floats[2] = new PointF();
        floats[2].x = mwith/2;
        floats[2].y = mhight/5*4;
        Log.e(TAG, "getAnimatorSet: "+mwith/4+"content"+mwith);
    }


    private AnimatorSet getAnimatorSet(ImageView imageView) {



        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", 0, -mwith/4);
        animator.setDuration(1000);
        BasEvaluator evaluator = new BasEvaluator(floats[1]);

        ValueAnimator valueAnim = ValueAnimator.ofObject(evaluator, floats[0], floats[2]);

        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                PointF p = (PointF) valueAnimator.getAnimatedValue();
                imageView.setX(p.x);
                imageView.setY(p.y);
                imageView.setScaleX(1 + valueAnimator.getAnimatedFraction()); // 大小
                imageView.setScaleY(1 +valueAnimator.getAnimatedFraction()); // 大小

                imageView.setAlpha(1-valueAnimator.getAnimatedFraction());
//                if(valueAnimator.getAnimatedFraction()==1){
//                    imageView.setVisibility(GONE);
//                }
            }
        });


        valueAnim.setDuration(1000);
        valueAnim.setTarget(imageView);
        valueAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator, valueAnim);

        return set;
    }

    class BasEvaluator implements TypeEvaluator<PointF> {
        private PointF p1;

        public BasEvaluator(PointF p1) {
            this.p1 = p1;
          //  Log.e(TAG, "BasEvaluator: " + p1);
        }

        @Override
        public PointF evaluate(float v, PointF pointF, PointF t1) {
            float t = v;
            //  B(t)=(1-t)^2*p0+2t(1-t)p1+t^2*p2
            PointF pointF1 = new PointF();
            pointF1.x = (1 - t) * (1 - t) * pointF.x + 2 * t * (1 - t) * p1.x + t * t * t1.x;
            pointF1.y = (1 - t) * (1 - t) * pointF.y + 2 * t * (1 - t) * p1.y + t * t * t1.y;

           // Log.e(TAG, "evaluate: " + pointF1.x + "数据" + pointF1.y);

            return pointF1;
        }
    }
}

