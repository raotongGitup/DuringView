package com.example.duringlibrary.movementSteps;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${raotong} on 2018/6/27.
 */

public class StepsArcView extends View {
    private Context context;

    /*画弧线的宽度*/
    private float boradwith = 38f;

    /*外圆画笔*/
    private Paint outcirclePaint;
    /*内圆画笔*/
    private Paint inCirclePaint;
    /*画字所用的画笔*/
    private TextPaint textPaint;
    /*外圆弧线的颜色* */
    private String outCircleColor = "#dddddd";
    /*内圆弧线的颜色*/
    private String incircleColor = "#FFCE44";
    /*绘制字体的大小*/
    private float textsize = 30f;

    private float with;
    private float hight;

    /*绘制中心的数字*/
    private String stringText = "1000";

    /*动画所用的时间*/
    private int animationduing = 3000;

    /*步数所扫过的角度*/
    private float currentAngleLength = 100;


    public StepsArcView(Context context) {
        super(context);
        this.context = context;
    }

    public StepsArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init();
    }

    public StepsArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /* 初始化画笔*/
    private void init() {
        outcirclePaint = new Paint();
        outcirclePaint.setStyle(Paint.Style.STROKE);
        outcirclePaint.setAntiAlias(true);
        outcirclePaint.setStrokeWidth(boradwith);
        outcirclePaint.setColor(Color.parseColor(outCircleColor));
        outcirclePaint.setStrokeCap(Paint.Cap.ROUND);
        outcirclePaint.setStrokeJoin(Paint.Join.ROUND);


        inCirclePaint = new Paint();
        inCirclePaint.setStyle(Paint.Style.STROKE);
        inCirclePaint.setAntiAlias(true);
        inCirclePaint.setStrokeWidth(boradwith);
        inCirclePaint.setColor(Color.parseColor(incircleColor));
        inCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        inCirclePaint.setStrokeJoin(Paint.Join.ROUND);


        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(dpTopx(context, textsize));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        with = w;
        hight = h;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
      /*画外圆*/
        drawOutcircle(canvas);
        /*画内圆*/
        drawIncircle(canvas);
        /*画文字*/
        drawText(canvas);

    }


    private void drawText(Canvas canvas) {

        canvas.drawText(stringText, with / 2, hight / 2, textPaint);

    }

    private void drawIncircle(Canvas canvas) {
        RectF rectF = new RectF(50, 50, with - 50, hight - 50);
        canvas.drawArc(rectF, 135, currentAngleLength, false, inCirclePaint);

    }

    private void drawOutcircle(Canvas canvas) {
        RectF rectF = new RectF(50, 50, with - 50, hight - 50);
        canvas.drawArc(rectF, 135, 270, false, outcirclePaint);


    }

    private void setAnimation(float last, float current, int length) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngleLength);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngleLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }

    public void setData(int data) {
        if (data >= 20000) {
            currentAngleLength = 270;
        } else {

            float i = (float) data / 20000;
            currentAngleLength = (float) i * 270;
        }
        stringText = data + "";
        setAnimation(0, currentAngleLength, animationduing);
    }


    /*px和dp的转换*/
    public int dpTopx(Context context, float dpvolur) {
        float scicle = context.getResources().getDisplayMetrics().density;
        return (int) (dpvolur * scicle + 0.5f);
    }


}
