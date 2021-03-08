package com.github.kongpf8848.viewworld.views;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.github.kongpf8848.viewworld.R;

public class ArcProgressView extends View {

    private int arcBackgroundColor;    // 圆弧背景颜色
    private int arcProgressColor;      // 圆弧进度颜色
    private int arcSubTitleColor;      // 副标题颜色
    private float arcStrokeWidth;      // 圆弧线的厚度
    private float arcTitleTextSize;    // 标题文字大小
    private float arcSubTitleTextSize; // 副标题文字大小
    private float arcProgress;    //   进度
    private int arcTitleNumber;   // 值
    private Paint paint;
    private float centerX;
    private float centerY;
    private float radius;   // 半径
    private RectF rectF;

    private int startAngle = 135;
    private int sweepAngle = 270;
    private String subTitle = "1月份";
    private SpannableString spannableString;

    private TextPaint textPaint;
    private RelativeSizeSpan relativeSizeSpan;
    private DynamicLayout dynamicLayout;
    private String text = "11分";
    private StyleSpan styleSpan;


    private float curProgress;    // 当前进度
    private int curNumber;

    public ArcProgressView(Context context) {
        this(context, null);
    }

    public ArcProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
        init(context);
    }

    private void readAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ArcProgressView);
        arcBackgroundColor = typedArray.getColor(R.styleable.ArcProgressView_arcBackgroundColor, 0x1c979797);
        arcProgressColor = typedArray.getColor(R.styleable.ArcProgressView_arcProgressColor, 0xff3372FF);
        arcSubTitleColor = typedArray.getColor(R.styleable.ArcProgressView_arcSubTitleColor, 0x66000000);
        arcStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.ArcProgressView_arcStrokeWidth, dp2px(5));
        arcTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.ArcProgressView_arcTitleTextSize, dp2px(30));
        arcSubTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.ArcProgressView_arcSubTitleTextSize, dp2px(14));
        arcProgress = typedArray.getFloat(R.styleable.ArcProgressView_arcProgress, 1.0f);
        arcTitleNumber = typedArray.getInt(R.styleable.ArcProgressView_arcTitleNumber, 100);
        typedArray.recycle();
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);

        relativeSizeSpan = new RelativeSizeSpan(0.6f);
        styleSpan = new StyleSpan(android.graphics.Typeface.BOLD);

        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setColor(arcProgressColor);
        textPaint.setTextSize(sp2px(22));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2f;
        centerY = h / 2f;
        radius = (Math.min(w, h) - arcStrokeWidth) / 2f;
        rectF = new RectF(-radius, -radius, radius, radius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredSize(widthMeasureSpec, dp2px(100));
        int height = getMeasuredSize(heightMeasureSpec, dp2px(100));
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制圆弧和进度
        drawArc(canvas);
        // 绘制文字 title
        drawTitleText(canvas);
        // 绘制文字副标题
        drawSubTitle(canvas);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    private void startAnimation() {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(0f, arcProgress);
        ValueAnimator numberAnimator = ValueAnimator.ofInt(0, arcTitleNumber);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        numberAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curNumber = (int) animation.getAnimatedValue();
                text = curNumber + "分";
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(progressAnimator, numberAnimator);
        animatorSet.setDuration(700);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }


    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(centerX, centerY);
        paint.setColor(arcBackgroundColor);
        paint.setStrokeWidth(arcStrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
        paint.setColor(arcProgressColor);
        canvas.drawArc(rectF, startAngle, sweepAngle * curProgress, false, paint);
        canvas.restore();
    }


    private void drawTitleText(Canvas canvas) {
        canvas.save();
        textPaint.setTextSize(arcTitleTextSize);
        float textWidth1 = textPaint.measureText(text.substring(0,text.length()-1));
        textPaint.setTextSize(arcTitleTextSize*0.6f);
        float textWidth2 = textPaint.measureText(text.substring(text.length()-1));
        float textWidth=textWidth1+textWidth2;
        textPaint.setTextSize(arcTitleTextSize);
        float textHeight = -textPaint.ascent() + textPaint.descent();  // 文字高度
        canvas.translate(centerX - textWidth/2, centerY - textHeight * 2 / 3f);
        spannableString = SpannableString.valueOf(text);
        spannableString.setSpan(styleSpan, 0, text.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan, text.length() - 1, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dynamicLayout = new DynamicLayout(spannableString, textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL, 0, 0, false);
        dynamicLayout.draw(canvas);
        canvas.restore();
    }


    private void drawSubTitle(Canvas canvas) {
        canvas.save();
        canvas.translate(centerX, centerY+100);
        paint.setTextSize(arcSubTitleTextSize);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(arcSubTitleColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(subTitle, 0, 0, paint);
        canvas.restore();
    }


    /**
     * 对外提供方法，设置进度
     *
     * @param percent
     */
    public void setArcProgress(float percent) {
        this.curProgress = percent;
        invalidate();
    }


    private int getMeasuredSize(int measureSpec, int defvalue) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            return size;
        }
        return Math.min(size, defvalue);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}


