package com.example.dhf.duringview.zhibodianzan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dhf.duringview.R;

import java.util.Random;

/**
 * Created by ${raotong} on 2018/5/3.
 */

public class LoveLayout extends RelativeLayout {

    private Context context;
    private LayoutParams params;
    private Drawable[] icons = new Drawable[4];
    private Interpolator[] interpolators = new Interpolator[4];
    private int mWidth;
    private int mHeight;

    public LoveLayout(Context context) {
        super(context);
        this.context = context;
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initview();

    }

    private void initview() {
        icons[0] = getResources().getDrawable(R.drawable.btn_background_round);
        icons[1] = getResources().getDrawable(R.drawable.check_background_round);
        icons[2] = getResources().getDrawable(R.drawable.btn_background_round);
        icons[3] = getResources().getDrawable(R.drawable.check_background_round);

        interpolators[0] = new AccelerateDecelerateInterpolator();
        interpolators[1] = new AccelerateInterpolator();
        interpolators[2] = new DecelerateInterpolator();
        interpolators[3] = new LinearInterpolator();

        int with = icons[0].getIntrinsicWidth();
        int hight = icons[0].getIntrinsicHeight();
        params = new LayoutParams(with, hight);
        params.addRule(CENTER_HORIZONTAL, TRUE);
        params.addRule(ALIGN_PARENT_BOTTOM, TRUE);


    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

    }

    public void addloveview() {
        final ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setImageDrawable(icons[new Random().nextInt(4)]);
        addView(imageView);

        final AnimatorSet set = getAnimatorSet(imageView);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(imageView);
            }
        });
    }

    private AnimatorSet getAnimatorSet(ImageView iv) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 0.3f, 1f);
        ObjectAnimator scalex = ObjectAnimator.ofFloat(iv, "scaleX", 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.2f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, scalex, scaleY);
        set.setDuration(100);
        ValueAnimator bzier = getBzierAnimator(iv);
        AnimatorSet set2 = new AnimatorSet();
        set2.playSequentially(set, bzier);
        set2.setTarget(iv);

        return set2;
    }

    public ValueAnimator getBzierAnimator(final ImageView iv) {
        PointF[] PointFs = getPointFs(iv);
        BasEvaluator evaluator = new BasEvaluator(PointFs[1], PointFs[2]);
        ValueAnimator valueAnim = ValueAnimator.ofObject(evaluator, PointFs[0], PointFs[3]);
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                PointF p = (PointF) animation.getAnimatedValue();
                iv.setX(p.x);
                iv.setY(p.y);
                iv.setAlpha(1 - animation.getAnimatedFraction()); // 透明度
            }
        });
        valueAnim.setTarget(iv);
        valueAnim.setDuration(3000);
        valueAnim.setInterpolator(interpolators[new Random().nextInt(4)]);
        return valueAnim;
    }

    private PointF[] getPointFs(ImageView iv) {
        // TODO Auto-generated method stub
        PointF[] PointFs = new PointF[4];
        PointFs[0] = new PointF(); // p0
        PointFs[0].x = (mWidth - params.width) / 2;
        PointFs[0].y = mHeight - params.height;

        PointFs[1] = new PointF(); // p1
        PointFs[1].x = new Random().nextInt(mWidth);
        PointFs[1].y = new Random().nextInt(mHeight / 2) + mHeight / 2 + params.height;

        PointFs[2] = new PointF(); // p2
        PointFs[2].x = new Random().nextInt(mWidth);
        PointFs[2].y = new Random().nextInt(mHeight / 2);




        PointFs[3] = new PointF(); // p3
        PointFs[3].x = mWidth / 2;
        PointFs[3].y = 0;


        return PointFs;
    }


    public class BasEvaluator implements TypeEvaluator<PointF> {
        private PointF p1;
        private PointF p2;

        public BasEvaluator(PointF f1, PointF f2) {
            this.p1 = f1;
            this.p2 = f2;
        }

        @Override
        public PointF evaluate(float fraction, PointF p0, PointF p3) {

            PointF pointF1 = new PointF();
//              贝塞尔曲线公式 p0*(1-t)^3 + 3p1*t*(1-t)^2 + 3p2*t^2*(1-t) + p3*t^3

            pointF1.x = p0.x * (1 - fraction) * (1 - fraction) * (1 - fraction)
                    + 3 * p1.x * fraction * (1 - fraction) * (1 - fraction)
                    + 3 * p2.x * fraction * fraction * (1 - fraction)
                    + p3.x * fraction * fraction * fraction;
            pointF1.y = p0.y * (1 - fraction) * (1 - fraction) * (1 - fraction)
                    + 3 * p1.y * fraction * (1 - fraction) * (1 - fraction)
                    + 3 * p2.y * fraction * fraction * (1 - fraction)
                    + p3.y * fraction * fraction * fraction;


            return pointF1;
        }
    }


}
