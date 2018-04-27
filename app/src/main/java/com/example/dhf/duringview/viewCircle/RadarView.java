package com.example.dhf.duringview.viewCircle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.dhf.duringview.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ${raotong} on 2018/4/20.
 * 绘制蜘蛛网卫星图
 */

public class RadarView extends View {

    //元素
    private List<ElementBean> mElementBeanList = new ArrayList<>();

    //中心点X坐标
    private int centerX;

    //中心点Y坐标
    private int centerY;
    //蛛网的最大半径
    private int maxRadius = -1;
    //网线画笔
    private Paint mNetLinePaint;
    //网线颜色
    private int netLineColor;
    //网线宽度
    private int netLineWidth;
    //文本画笔
    private TextPaint mTextPaint;
    //文本颜色
    private int mTextColor;
    //文本字体大小
    private int mTextSize;
    //节点画笔
    private Paint mPointPaint;
    //节点颜色
    private int mPointColor;
    //节点半径
    private int mPointRadius;
    //网间距
    private int dalta;
    //每两条属性线间的弧度
    private float angle;
    //阴影覆盖区域颜色
    private int mCoverColor;


    private int max = 100;


    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadarView);
        maxRadius = (int) ta.getDimension(R.styleable.RadarView_maxRadius, -1);
        netLineColor = ta.getColor(R.styleable.RadarView_netColor, getResources().getColor(R.color.colorPrimary));
        netLineWidth = (int) ta.getDimension(R.styleable.RadarView_netWidth, 1);
        mTextColor = ta.getColor(R.styleable.RadarView_rvTextColor, getResources().getColor(R.color.colorPrimary));
        mTextSize = (int) ta.getDimension(R.styleable.RadarView_rvTextSize, 8);
        mPointColor = ta.getColor(R.styleable.RadarView_netPointColor, getResources().getColor(R.color.colorPrimary));
        mPointRadius = (int) ta.getDimension(R.styleable.RadarView_netPointRadius, 1);
        mCoverColor = ta.getColor(R.styleable.RadarView_coverColor, getResources().getColor(R.color.colorPrimary));
        ta.recycle();


        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mNetLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNetLinePaint.setStyle(Paint.Style.STROKE);
        mNetLinePaint.setColor(netLineColor);
        mNetLinePaint.setStrokeWidth(netLineWidth);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(mPointColor);
    }

    /**
     * 初始化数据源
     *
     * @param data
     */
    public void initData(List<ElementBean> data) {
        if (null == data) {
            throw new IllegalArgumentException("data can't be null");
        }
        mElementBeanList = data;
        angle = (float) (Math.PI * 2 / mElementBeanList.size());
        postInvalidate();
    }

    public void initMAX(int max) {
        this.max = max;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = getWidth() >> 1;
        centerY = getHeight() >> 1;
        int width = Math.min(getWidth(), getHeight());
        maxRadius = (int) (Math.min(width >> 1, maxRadius) * 1);
        //网间距
        dalta = maxRadius / 5;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNet(canvas);
        drawText(canvas);
        drawPoint(canvas);
    }

    /**
     * 绘制节点及阴影区域
     *
     * @param canvas 画布
     */
    private void drawPoint(Canvas canvas) {
        canvas.save();
        int size = mElementBeanList.size();
        Path path = new Path();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(mCoverColor);
        for (int i = 0; i < size; i++) {
            ElementBean elementBean = mElementBeanList.get(i);
            int value = elementBean.getValue();
            float scale = ((float) value / max);
            if (i == 0) {
                float tempX = centerX + maxRadius * scale;
                float tempY = centerY;
                path.moveTo(tempX, tempY);
                canvas.drawCircle(tempX, tempY, mPointRadius, mPointPaint);
            } else {
                float tempX = centerX + (float) (maxRadius * scale * Math.cos(angle * i));
                float tempY = centerY + (float) (maxRadius * scale * Math.sin(angle * i));
                path.lineTo(tempX, tempY);
                canvas.drawCircle(tempX, tempY, mPointRadius, mPointPaint);
            }
        }
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    /**
     * 绘制文本
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        int size = mElementBeanList.size();
        for (int i = 0; i < size; i++) {
            ElementBean elementBean = mElementBeanList.get(i);
            int tempX = (int) Math.rint(centerX + (float) (maxRadius * Math.cos(angle * i)));
            int tempY = (int) Math.rint(centerY + (float) (maxRadius * Math.sin(angle * i)));
            Rect rect = new Rect();
            mTextPaint.getTextBounds(elementBean.getTitle(), 0, elementBean.getTitle().length(), rect);
            if (tempX > centerX) {
                if (tempY < centerY) {
                    canvas.drawText(elementBean.getTitle(), tempX - (rect.width() >> 1), tempY - 6, mTextPaint);
                } else if (tempY > centerY) {
                    canvas.drawText(elementBean.getTitle(), tempX - (rect.width() >> 1), tempY + 6 + rect.height(), mTextPaint);
                } else if (tempY > centerY - 1 && tempY < centerY + 1) {
                    canvas.drawText(elementBean.getTitle(), tempX, tempY + (rect.height() >> 1), mTextPaint);
                }
            } else {
                if (tempY < centerY) {
                    canvas.drawText(elementBean.getTitle(), tempX - (rect.width() >> 1), tempY - 6, mTextPaint);
                } else if (tempY > centerY) {
                    canvas.drawText(elementBean.getTitle(), tempX - (rect.width() >> 1), tempY + 6 + rect.height(), mTextPaint);
                } else if (tempY > centerY - 1 && tempY < centerY + 1) {
                    canvas.drawText(elementBean.getTitle(), tempX - rect.width(), tempY + (rect.height() >> 1), mTextPaint);
                }
            }
        }
    }

    /**
     * 绘制蜘蛛网
     *
     * @param canvas 画布
     */
    private void drawNet(Canvas canvas) {
        Path path = new Path();
        int count = mElementBeanList.size();
        for (int i = 1; i <= 5; i++) {
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + dalta * i, centerY);
                } else {
                    float tempX = (float) ((dalta * i) * Math.cos(angle * j));
                    float tempY = (float) ((dalta * i) * Math.sin(angle * j));
                    path.lineTo(centerX + tempX, centerY + tempY);
                }
            }

            if (i == 1) {
                canvas.drawText("0.0", centerX - 40, centerY + 7, mTextPaint);
            }
            float centerYtext = (float) ((2 * centerY - ((dalta * i) * Math.sin(angle * 1)) - ((dalta * i) * Math.sin(angle * 2))) / 2);

            canvas.drawText(((max / 5) * i) + "", centerX - 40, centerYtext, mTextPaint);

            path.close();
            canvas.drawPath(path, mNetLinePaint);

        }
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float endX = centerX + (float) (maxRadius * Math.cos(angle * i));
            float endY = centerY + (float) (maxRadius * Math.sin(angle * i));
            path.lineTo(endX, endY);
            canvas.drawPath(path, mNetLinePaint);
        }
    }

    /**
     * 测量宽
     *
     * @return 返回测量的宽度
     */
    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                if (-1 != maxRadius) {
                    result = maxRadius;
                }
                result = (int) ((result << 1) * 1.2);
                break;
        }
        return result;
    }

    /**
     * 测量高
     *
     * @return 返回测量的高度
     */
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                if (-1 != maxRadius) {
                    result = maxRadius;
                }
                result = (int) ((result << 1) * 1.2);
                break;
        }
        return result;
    }

}
