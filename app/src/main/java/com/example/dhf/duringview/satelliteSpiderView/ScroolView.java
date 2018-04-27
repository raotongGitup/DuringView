package com.example.dhf.duringview.satelliteSpiderView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DHF on 2018/4/23.
 * 绘制折线的画法
 */
public class ScroolView extends View {
    /**
     * x轴之间的间隔,y轴之间的间隔
     */
    private float with = dpToPx(50);

    /**
     * y轴高度
     *
     */
    private int hight;
    Paint paint;
    Paint paintCire;
    TextPaint textPaint;
    private int max = 100;
    private int main = 0;
    Paint yPaint;
    private TextPaint nameTitlePaint;


    private Context context;
    private List<PointF> pointFS = new ArrayList<>();
    private List<PointF> circl = new ArrayList<>();

    /*y轴上的标题*/
    private String ytitle = "km/s";
    private String ytitlecolor = "#ffff00";
    private int ytitleSize = 18;
    /*x轴上的标题*/
    private String xtitle = "月份";
    private String xtitleColor = "#ffff00";
    private int xtitleSize = 18;
    /*y轴值*/
    private String yzhiTitleColor = "#0000ff";
    private int yzhiSize = 18;
    /*x轴值*/
    private String xzhiTitleColor = "#00ff00";
    private int xzhiSize = 18;
    /*xy轴坐标轴的颜色*/
    private String xyColor = "#ff0000";
    /*xy背景十字绘画颜色*/
    private String xyBgColor = "#ff0000";
    /*y轴的轴线个数*/
    private int ynumber = 5;
    /*xy数值点圆的内外圆颜色*/
    private String outCircleColor = "#ffff00";
    private String inCirclColor = "#ff0000";
    private boolean Solidround = false;
    /*是否显示坐标数据*/
    private boolean pointsdata = false;
    /*坐标点值的颜色*/
    private String pointColor = "#ffffff";
    /*没个x轴之间的间隔数*/
    private int xinterval;

    private Paint colorPaint;
    private Path colorPath;


    public ScroolView(Context context) {
        super(context);
        this.context = context;
    }

    public ScroolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    private void initData() {

        yPaint = new Paint();
        yPaint.setColor(Color.parseColor(xyBgColor));
        yPaint.setAntiAlias(true);
        yPaint.setStrokeWidth(spToPx(1));
        yPaint.setStyle(Paint.Style.STROKE);


        paint = new Paint();
        paint.setColor(Color.parseColor(xyColor));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(spToPx(1));
        paint.setStyle(Paint.Style.STROKE);

        paintCire = new Paint();
        paintCire.setStrokeWidth(2);
        paintCire.setColor(Color.GREEN);

        paintCire.setStyle(Paint.Style.FILL);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(ytitleSize);

        nameTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        nameTitlePaint.setColor(Color.WHITE);
        nameTitlePaint.setTextSize(27);
        xinterval = (max - main) / ynumber;


        colorPaint = new Paint();
        colorPaint.setStyle(Paint.Style.FILL);
        colorPath = new Path();


    }

    public ScroolView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        hight = 0;
        //获得宽度MODE
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        //获得宽度的值
        if (modeW == MeasureSpec.AT_MOST) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (modeW == MeasureSpec.EXACTLY) {
            width = widthMeasureSpec;
        }
        if (modeW == MeasureSpec.UNSPECIFIED) {

            width = (int) (pointFS.size() * with + 2 * with);

        }
        //获得高度MODE
        int modeH = MeasureSpec.getMode(hight);
        //获得高度的值
        if (modeH == MeasureSpec.AT_MOST) {
            hight = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (modeH == MeasureSpec.EXACTLY) {
            hight = heightMeasureSpec;
        }
        if (modeH == MeasureSpec.UNSPECIFIED) {
            hight = 400;
        }
        //设置宽度和高度
        setMeasuredDimension(width, hight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pointFS.size() > 0) {
            textX(canvas);
            textline(canvas);
            textCircle(canvas);
            Pointcoordinate(canvas);
        }

    }

    /**
     * 显示每个点的数据
     */
    private void Pointcoordinate(Canvas canvas) {
        if (pointsdata) {
            for (int i = 0; i < circl.size(); i++) {
                float x = i * with + dpToPx(20);
                float y = hight - dpToPx(20) - (((hight - dpToPx(50) - dpToPx(20)) / (max - main) * pointFS.get(i).y));
                canvas.drawText(pointFS.get(i).y + "", x, y - dpToPx(10), nameTitlePaint);

            }
        }
    }

    /*画坐标相交原点*/
    private void textCircle(Canvas canvas) {
        for (int i = 0; i < circl.size(); i++) {
            float x = i * with + dpToPx(20);
            float y = hight - dpToPx(20) - (((hight - dpToPx(50) - dpToPx(20)) / (max - main)) * pointFS.get(i).y);
            paintCire.setColor(Color.parseColor(outCircleColor));
            canvas.drawCircle(x, y, 8, paintCire);
            if (Solidround) {
                paintCire.setColor(Color.parseColor(inCirclColor));
                canvas.drawCircle(x, y, 5, paintCire);
            }

        }

    }

    /**
     * 画x轴及坐标
     */
    private void textX(Canvas canvas) {
        canvas.save();
        /**
         * 绘制xy轴
         * */
        Path path = new Path();
        path.moveTo(dpToPx(20), dpToPx(40));
        path.lineTo(dpToPx(20), hight - dpToPx(20));
        path.lineTo(with * pointFS.size() + with, hight - dpToPx(20));
        canvas.drawPath(path, paint);
         /*x轴箭头*/
        Path path1 = new Path();
        path1.moveTo(dpToPx(15), dpToPx(50));
        path1.lineTo(dpToPx(20), dpToPx(40));
        path1.lineTo(dpToPx(25), dpToPx(50));
        canvas.drawPath(path1, paint);
         /*y轴箭头*/
        Path path2 = new Path();
        path2.moveTo(with * pointFS.size() + with - 10, hight - dpToPx(15));
        path2.lineTo(with * pointFS.size() + with, hight - dpToPx(20));
        path2.lineTo(with * pointFS.size() + with - 10, hight - dpToPx(25));
        canvas.drawPath(path2, paint);
        /*y轴元素代表内容
        * */
        textPaint.setColor(Color.parseColor(ytitlecolor));
        textPaint.setTextSize(ytitleSize);
        canvas.drawText(ytitle, dpToPx(15), dpToPx(20), textPaint);
        /*x 轴元素代表内容*/
        textPaint.setColor(Color.parseColor(xtitleColor));
        textPaint.setTextSize(xtitleSize);
        canvas.drawText(xtitle, with * pointFS.size() + with + dpToPx(10), hight - dpToPx(20), textPaint);
        /*xy轴的值*/
        textPaint.setColor(Color.parseColor(yzhiTitleColor));
        textPaint.setTextSize(yzhiSize);
        float yhight = (hight - dpToPx(70)) / 5;
        for (int i = 0; i < ynumber; i++) {
            if (i == 0) {
                canvas.drawText(xinterval + "", dpToPx(7), hight - dpToPx(20), textPaint);

            } else {
                canvas.drawText(xinterval * (i + 1) + "", dpToPx(7), hight - dpToPx(20) - yhight * i, textPaint);
                canvas.drawLine(dpToPx(20), hight - dpToPx(20) - yhight * i, with * pointFS.size(), hight - dpToPx(20) - yhight * i, yPaint);
            }
        }
        textPaint.setColor(Color.parseColor(xzhiTitleColor));
        textPaint.setTextSize(xzhiSize);
        for (int i = 0; i < pointFS.size(); i++) {
            if (i == 0) {
                canvas.drawText(pointFS.get(i).x + "", dpToPx(20) - dpToPx(5), hight - dpToPx(10), textPaint);
            } else {
                canvas.drawText(pointFS.get(i).x + "", dpToPx(20) + i * with - dpToPx(5), hight - dpToPx(10), textPaint);
                canvas.drawLine(dpToPx(20) + i * with, hight - dpToPx(20), dpToPx(20) + i * with, dpToPx(70), yPaint);
            }
        }
/**
 * xy轴背景线
 * */
        canvas.restore();
    }

    /**
     * 划折线
     */
    private void textline(Canvas canvas) {
        canvas.save();


        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(3);
        Path path = new Path();
        for (int i = 0; i < pointFS.size(); i++) {
            float x = i * with + dpToPx(20);
            float y = hight - dpToPx(20) - (((hight - dpToPx(50) - dpToPx(20)) / (max - main)) * pointFS.get(i).y);
            if (i == 0) {
                path.moveTo(x, y);
                colorPath.moveTo(x, y);
            } else {
                path.lineTo(x, y);
                colorPath.lineTo(x, y);


            }
            circl.add(new PointF(x + "", y));
            canvas.drawCircle(x, y, 8, paintCire);
        }
        /**
         *
         * 渐变色的运用
         * */
        Shader mShader = new LinearGradient(0, 0, 0, getHeight() - dpToPx(20), new int[]{
                Color.parseColor("#B9E731"), Color.WHITE}, null, Shader.TileMode.CLAMP);
        colorPaint.setShader(mShader);
        canvas.drawPath(path, paint);
        canvas.drawPath(colorPath, colorPaint);
    }

    //设置scrollerView的滚动条的位置，通过位置计算当前的时段
    public void setScrollOffset(int offset, int maxScrollOffset) {

        invalidate();
    }

    float wit = 0;
    float high = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                wit = event.getX();
                high = event.getY();


                return true;
        }
        return false;
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


    public void setMax(int max) {
        this.max = max;
    }

    public void setMain(int main) {
        this.main = main;
    }

    public void setPointFS(List<PointF> pointFS) {
        this.pointFS = pointFS;
    }

    public void setYtitle(String ytitle) {
        this.ytitle = ytitle;
    }

    public void setYtitlecolor(String ytitlecolor) {
        this.ytitlecolor = ytitlecolor;
    }

    public void setYtitleSize(int ytitleSize) {
        this.ytitleSize = ytitleSize;
    }

    public void setXtitle(String xtitle) {
        this.xtitle = xtitle;
    }

    public void setXtitleColor(String xtitleColor) {
        this.xtitleColor = xtitleColor;
    }

    public void setXtitleSize(int xtitleSize) {
        this.xtitleSize = xtitleSize;
    }

    public void setYzhiTitleColor(String yzhiTitleColor) {
        this.yzhiTitleColor = yzhiTitleColor;
    }

    public void setYzhiSize(int yzhiSize) {
        this.yzhiSize = yzhiSize;
    }

    public void setXzhiTitleColor(String xzhiTitleColor) {
        this.xzhiTitleColor = xzhiTitleColor;
    }

    public void setXzhiSize(int xzhiSize) {
        this.xzhiSize = xzhiSize;
    }

    public void setXyColor(String xyColor) {
        this.xyColor = xyColor;
    }

    public void setXyBgColor(String xyBgColor) {
        this.xyBgColor = xyBgColor;
    }

    public void setYnumber(int ynumber) {
        this.ynumber = ynumber;
    }

    public void setOutCircleColor(String outCircleColor) {
        this.outCircleColor = outCircleColor;
    }

    public void setInCirclColor(String inCirclColor) {
        this.inCirclColor = inCirclColor;
    }

    public void setSolidround(boolean solidround) {
        Solidround = solidround;
    }

    public void setPointsdata(boolean pointsdata) {
        this.pointsdata = pointsdata;
    }

    public void setPointColor(String pointColor) {
        this.pointColor = pointColor;
    }
}


