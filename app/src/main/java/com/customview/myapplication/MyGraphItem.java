package com.customview.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class MyGraphItem extends View {

  Context context;
  float yPrevious;
  float yCurrent;
  float yNext;
  private int graphHeight;
  private float maxValue;
  private int position;

  public MyGraphItem(Context context, float yPrevious, float yCurrent, float yNext, float maxValue, int position) {
    super(context);
    this.context = context;
    this.yPrevious = yPrevious;
    this.yCurrent = yCurrent;
    this.yNext = yNext;
    this.maxValue = maxValue;
    this.position = position;
  }

  public MyGraphItem(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MyGraphItem(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (graphHeight == 0) {
      graphHeight = getHeight();
    }
    drawPath(canvas);
    drawPriceCircle(canvas);
  }

  private void drawPath(Canvas canvas) {
    Path path = new Path();
    if (yPrevious == -1) {
      path.moveTo(getWidth() / 2, getYPos(yCurrent));
      path.lineTo(getWidth(), getYPos(yNext));
    } else if(yNext == -1){
      path.moveTo(0, getYPos(yPrevious));
      path.lineTo(getWidth() / 2, getYPos(yCurrent));
    }else {
      path.moveTo(0, getYPos(yPrevious));
      path.lineTo(getWidth() / 2, getYPos(yCurrent));
      path.lineTo(getWidth(), getYPos(yNext));
    }
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(3);
    paint.setColor(0xFF2E69B3);
    paint.setAntiAlias(true);
    canvas.drawPath(path, paint);
  }

  private void drawPriceCircle(Canvas canvas) {
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(3);
    paint.setColor(0xFF2E69B3);
    canvas.drawCircle(getWidth() / 2, getYPos(yCurrent), 10, paint);
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(0xFFFFFFFF);
    canvas.drawCircle(getWidth() / 2, getYPos(yCurrent), 10, paint);
  }

  private float getYPos(float value) {
    float height = graphHeight;
    // scale it to the view size
    value = (value / maxValue) * height;

    // invert it so that higher values have lower y
    value = height - value;

    // offset it to adjust for padding
    value += getPaddingTop();

    return value;
  }

  public void setPoints(float yPrevious, float yCurrent, float yNext, float max, int position) {
    this.yPrevious = yPrevious;
    this.yCurrent = yCurrent;
    this.yNext = yNext;
    this.maxValue  = max;
    this.position = position;
    invalidate();
  }
}
