package com.github.kongpf8848.viewworld.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.kongpf8848.viewworld.R;

@SuppressLint("AppCompatCustomView")
public class StrokeTextView extends TextView {

    private TextView outlineTextView = null;
    private float strokeWidth;
    private int strokeColor;

    public StrokeTextView(Context context) {
        this(context,null);
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        outlineTextView = new TextView(context,attrs,defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
        strokeWidth = typedArray.getDimension(R.styleable.StrokeTextView_stroke_width, 5);
        strokeColor = typedArray.getColor(R.styleable.StrokeTextView_stroke_color,Color.RED);
        typedArray.recycle();

        TextPaint tp = outlineTextView.getPaint();
        tp.setStyle(Paint.Style.STROKE);
        tp.setStrokeWidth(strokeWidth);
        outlineTextView.setTextColor(strokeColor);
        outlineTextView.setGravity(getGravity());
    }

    @Override
    public void setLayoutParams (ViewGroup.LayoutParams params){
        super.setLayoutParams(params);
        outlineTextView.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        CharSequence outlineText = outlineTextView.getText();
        if(outlineText== null || !outlineText.equals(this.getText())){
            outlineTextView.setText(getText());
            this.postInvalidate();
        }
        outlineTextView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed, left, top, right, bottom);
        outlineTextView.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        outlineTextView.draw(canvas);
        super.onDraw(canvas);
    }

}
