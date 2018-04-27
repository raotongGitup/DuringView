package com.example.dhf.duringview.satelliteSpiderView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by ${raotong} on 2018/4/20.
 * <p>
 * 绘制曲线的画法
 */

public class BasehorizontaiScyollview extends View {

    float with;
    float hight;

    float with_x = 0;
    float hifh_y = 0;
    private Context context;

    private List<PointF> pointFS = new ArrayList<>();
    private List<Float> cuicus_x = new ArrayList<>();
    private List<Float> cuicus_y = new ArrayList<>();
    private static final int STEPS = 10;
    Paint paint;
    TextPaint textPaint;
    Paint cunpaint;
    List<Cubic> calculate_y;
    List<Cubic> calculate_x;

    /**
     * 渐变色路径
     */
    Paint colorpaint;
    Path colorpath;
    private String[] str = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月"};

    public BasehorizontaiScyollview(Context context) {
        super(context);
    }

    public BasehorizontaiScyollview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(spToPx(2));
        paint.setStyle(Paint.Style.STROKE);


        cunpaint = new Paint();
        cunpaint.setColor(Color.RED);
        cunpaint.setAntiAlias(true);
        cunpaint.setStrokeWidth(spToPx(2));
        cunpaint.setStyle(Paint.Style.STROKE);
        cunpaint.setStrokeJoin(Paint.Join.ROUND);


        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(spToPx(5));
        /**
         * 渐变色
         * */
        colorpaint = new Paint();
        //colorpaint.setStyle(Paint.Style.FILL);
        colorpath = new Path();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            PointF pointF = new PointF();
            pointF.x = random.nextInt(90);
            pointF.y = random.nextInt(90);
            pointFS.add(pointF);
        }

    }

    public BasehorizontaiScyollview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        with = w;
        hight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = 0;
//       int  hight = 0;
//        //获得宽度MODE
//        int modeW = MeasureSpec.getMode(widthMeasureSpec);
//        //获得宽度的值
//        if (modeW == MeasureSpec.AT_MOST) {
//            width = MeasureSpec.getSize(widthMeasureSpec);
//        }
//        if (modeW == MeasureSpec.EXACTLY) {
//            width = widthMeasureSpec;
//        }
//        if (modeW == MeasureSpec.UNSPECIFIED) {
//           // width = (int) (pointFS.size() * with + with);
//        }
//        //获得高度MODE
//        int modeH = MeasureSpec.getMode(heightMeasureSpec);
//        //获得高度的值
//        if (modeH == MeasureSpec.AT_MOST) {
//            hight = MeasureSpec.getSize(heightMeasureSpec);
//        }
//        if (modeH == MeasureSpec.EXACTLY) {
//            hight = heightMeasureSpec;
//        }
//        if (modeH == MeasureSpec.UNSPECIFIED) {
//            //ScrollView和HorizontalScrollView
//            hight = 400;
//        }
//        //设置宽度和高度
//        setMeasuredDimension(width, hight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 绘制xy轴
         * */
        Path path = new Path();
        path.moveTo(dpToPx(20), dpToPx(20));
        path.lineTo(dpToPx(20), hight - dpToPx(20));
        path.lineTo(with - dpToPx(20), hight - dpToPx(20));
        canvas.drawPath(path, paint);
        canvas.drawLine(dpToPx(10), dpToPx(30), dpToPx(20), dpToPx(20), paint);
        canvas.drawLine(dpToPx(30), dpToPx(30), dpToPx(20), dpToPx(20), paint);
        canvas.drawLine(with - dpToPx(30), hight - dpToPx(30), with - dpToPx(20), hight - dpToPx(20), paint);
        canvas.drawLine(with - dpToPx(30), hight - dpToPx(10), with - dpToPx(20), hight - dpToPx(20), paint);

        /**
         * 绘制xy轴的值
         * */
        drawXy(canvas);
        /**
         * 绘制曲线的方法*/
        drawXyLine(canvas);
        /*
        绘制数据圆点
        * */
        drawXyCircle(canvas);
    }

    private void drawXyCircle(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < calculate_x.size(); i++) {
            canvas.drawCircle(calculate_x.get(i).eval(0), calculate_y.get(i).eval(0), 6, paint);

        }
    }

    private void drawXyLine(Canvas canvas) {
        calculate_x = calculate(cuicus_x);
        calculate_y = calculate(cuicus_y);
        Path path = new Path();
        path.moveTo(calculate_x.get(0).eval(0), calculate_y.get(0).eval(0));
        colorpath.moveTo(calculate_x.get(0).eval(0), hight - dpToPx(20));
        colorpath.lineTo(calculate_x.get(0).eval(0), calculate_y.get(0).eval(0));
        float lastmove = 0;
        for (int i = 0; i < calculate_x.size(); i++) {
            Log.e("hight", calculate_x.get(i) + "  hight " + calculate_y.get(i));
            for (int i1 = 0; i1 < STEPS; i1++) {
                float u = (float) i1 / STEPS;
                if (calculate_y.get(i).eval(u) > (hight - dpToPx(20))) {
                    path.lineTo(calculate_x.get(i).eval(u), hight - dpToPx(20));
                } else {
                    path.lineTo(calculate_x.get(i).eval(u), calculate_y.get(i).eval(u));
                }
                colorpath.lineTo(calculate_x.get(i).eval(u), calculate_y.get(i).eval(u));
                lastmove = calculate_x.get(i).eval(u);
                Log.e("tagSt", calculate_x.get(i).eval(u) + "  hight " + calculate_y.get(i).eval(u));
            }

        }
        colorpath.lineTo(lastmove, hight - dpToPx(20));
        /**
         *
         * 渐变色的运用
         * */
        Shader mShader = new LinearGradient(0, 0, 0, getHeight() - dpToPx(60), new int[]{
                Color.parseColor("#B9E731"), Color.WHITE}, null, Shader.TileMode.CLAMP);
        colorpaint.setShader(mShader);
        canvas.drawPath(colorpath, colorpaint);
        canvas.drawPath(path, cunpaint);

    }

    private void drawXy(Canvas canvas) {
        with_x = (with - dpToPx(40)) / 10;
        hifh_y = (hight - dpToPx(40)) / 5;
        for (int i = 0; i < 10; i++) {
            canvas.drawText(str[i], dpToPx(20) + with_x * (i + 1), hight - dpToPx(5), textPaint);
            canvas.drawLine(dpToPx(20) + with_x * (i + 1), hight - dpToPx(20), dpToPx(20) + with_x * (i + 1), hight - dpToPx(30), paint);
            cuicus_x.add(dpToPx(20) + with_x * (i + 1));
            /**
             * 100是数据的最大值
             *(hight - dpToPx(60)) / 100,100指的是坐标的（max-main）,总体指每个坐标占几个px
             * pointFS.get(i).y-0指的是数据值-数据的最小值
             * */
            float cuicusy = (hight - dpToPx(60)) / 100 * (pointFS.get(i).y - 0);
            Log.e("tag", pointFS.get(i).y + "hight" + hight);
            cuicus_y.add(hight - dpToPx(20) - cuicusy);
        }

        for (int i = 0; i < 5; i++) {
            canvas.drawText(20 * (i + 1) + "'", dpToPx(5), hight - dpToPx(20) - hifh_y * (i + 1), textPaint);
            canvas.drawLine(dpToPx(20), hight - dpToPx(20) - hifh_y * (i + 1), dpToPx(30), hight - dpToPx(20) - hifh_y * (i + 1), paint);

        }

    }

    /**
     * 计算曲线.
     *
     * @param x
     * @return
     */
    private List<Cubic> calculate(List<Float> x) {
        int n = x.size() - 1;
        float[] gamma = new float[n + 1];
        float[] delta = new float[n + 1];
        float[] D = new float[n + 1];
        int i;
        gamma[0] = 1.0f / 2.0f;
        for (i = 1; i < n; i++) {
            gamma[i] = 1 / (4 - gamma[i - 1]);
        }
        gamma[n] = 1 / (2 - gamma[n - 1]);

        delta[0] = 3 * (x.get(1) - x.get(0)) * gamma[0];
        for (i = 1; i < n; i++) {
            delta[i] = (3 * (x.get(i + 1) - x.get(i - 1)) - delta[i - 1])
                    * gamma[i];
        }
        delta[n] = (3 * (x.get(n) - x.get(n - 1)) - delta[n - 1]) * gamma[n];

        D[n] = delta[n];
        for (i = n - 1; i >= 0; i--) {
            D[i] = delta[i] - gamma[i] * D[i + 1];
        }
        List<Cubic> cubics = new LinkedList<Cubic>();
        for (i = 0; i < n; i++) {
            Cubic c = new Cubic(x.get(i), D[i], 3 * (x.get(i + 1) - x.get(i))
                    - 2 * D[i] - D[i + 1], 2 * (x.get(i) - x.get(i + 1)) + D[i]
                    + D[i + 1]);
            cubics.add(c);
        }
        return cubics;
    }

    class Cubic {
        float a, b, c, d;

        public Cubic(float a, float b, float c, float d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        /**
         * evaluate cubic
         */
        public float eval(float u) {
            return (((d * u) + c) * u + b) * u + a;
        }
    }

    /**
     * dp转化成为px
     *
     * @param dp
     * @return
     */
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    /**
     * sp转化为px
     *
     * @param sp
     * @return
     */
    private int spToPx(int sp) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * sp + 0.5f * (sp >= 0 ? 1 : -1));
    }
}
