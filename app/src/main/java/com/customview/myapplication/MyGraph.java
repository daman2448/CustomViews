package com.customview.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyGraph extends View {

  ArrayList<String> weekList = new ArrayList<>(7);
  private int graphHeight;
  private int graphWidth;
  private Paint paint = new Paint();
  float[] dataPoints = {2000, 4000, 3150, 5000, 1000, 6200, 3302};
  int textHeight;
  int averageTextHeight;
  int marginGraphAndAverageText;
  int cheapestDataPoint;
  public MyGraph(Context context) {
    super(context);
  }

  public MyGraph(Context context, AttributeSet attrs) {
    super(context, attrs);
    //setMinimumHeight(300);
    weekList.add("SUN ");
    weekList.add("MON ");
    weekList.add("TUE ");
    weekList.add("WED ");
    weekList.add("THU ");
    weekList.add("FRI ");
    weekList.add("SAT ");
    graphHeight = getHeight();
    graphWidth = getWidth();
    cheapestDataPoint = getCheapestDataPoint(dataPoints);
  }


  private float getMax(float[] array) {
    float max = array[0];
    for (int i = 1; i < array.length; i++) {
      if (array[i] > max) {
        max = array[i];
      }
    }
    return max;
  }

  @Override
  protected void onDraw(Canvas canvas) {

    if (textHeight == 0) {
      textHeight = getHeight() / 6;
    }
    if (averageTextHeight == 0) {
      averageTextHeight = getHeight() / 6;
    }
    if (marginGraphAndAverageText == 0) {
      marginGraphAndAverageText = getHeight() / 6 ;
    }
    if (graphHeight == 0) {
      graphHeight = getHeight() - getPaddingBottom() - getPaddingTop() - textHeight - averageTextHeight;
      graphWidth = getWidth() - getPaddingLeft() - getPaddingRight();
    }
    drawBackground(canvas);
    drawLineChart(canvas);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {

    Log.d("Daman", event.toString());
    if(event.getAction() == MotionEvent.ACTION_DOWN){
      return true;
    }
    return super.onTouchEvent(event);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
  }

  private void drawBackground(Canvas canvas) {

    Paint fillBackgroundPaint = new Paint();
    fillBackgroundPaint.setStyle(Paint.Style.FILL);
    fillBackgroundPaint.setColor(0xFFF3F3F3);
    canvas.drawRect(getPaddingLeft(),getPaddingTop(),getWidth() -getPaddingRight(),getHeight() - getPaddingBottom(),fillBackgroundPaint);

    Paint paint = new Paint();
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(Color.DKGRAY);
    paint.setTextAlign(Paint.Align.CENTER);
    paint.setTextSize(textHeight / 2);
    canvas.drawLine(getPaddingLeft(), textHeight - textHeight / 3 + getPaddingTop(), getWidth() - getPaddingRight(), textHeight - textHeight / 3 + getPaddingTop(), paint);
    //canvas.drawLine(getPaddingLeft(),getPaddingTop(), getWidth() -getPaddingRight(), getPaddingTop(),paint);
    // paint.setStrokeWidth(2);
    for (int i = 0; i < dataPoints.length; i++) {
      if (i != 0) {
        canvas.drawLine(getXPos(i), textHeight - textHeight / 3 + getPaddingTop(), getXPos(i), getHeight() - getPaddingBottom(), paint);
      }
      if (i == 3) {
        Paint currentDarkBackground = new Paint();
        currentDarkBackground.setColor(0xFFDCDCDC);
        canvas.drawRect(getXPos(i),textHeight - textHeight / 3 + getPaddingTop(), getXPos(i+1),getHeight() - getPaddingBottom(),currentDarkBackground);
        Paint currentDatePaint = new Paint();
        currentDatePaint.setStyle(Paint.Style.STROKE);
        currentDatePaint.setColor(Color.WHITE);
        currentDatePaint.setTextAlign(Paint.Align.CENTER);
        currentDatePaint.setTextSize(textHeight / 2);
        Paint currentDateBackgroundPaint = new Paint();
        currentDateBackgroundPaint.setColor(0xFF2E69B3);
        currentDateBackgroundPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(getXPos(i), getPaddingTop(), getXPos(i + 1), textHeight - textHeight / 3 + getPaddingTop(), currentDateBackgroundPaint);
        canvas.drawText(weekList.get(i), (getXPos(i) + getXPos(i + 1)) / 2, getPaddingTop() + textHeight / 2, currentDatePaint);
      } else {
        Paint otherDatePaint = new Paint();
        canvas.drawText(weekList.get(i), (getXPos(i) + getXPos(i + 1)) / 2, getPaddingTop() + textHeight / 2, paint);
      }
      //drawCurrentDateCircle(0,canvas,(getXPos(i) + getXPos(i + 1)) / 2, getPaddingTop() + textHeight/2);
    }
    canvas.drawLine(getPaddingLeft(), getHeight() - getPaddingBottom(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), paint);
    //  canvas.drawLine(getPaddingLeft(), graphHeight + getPaddingTop() + textHeight, getWidth() - getPaddingRight(), graphHeight+ getPaddingTop() + textHeight, paint);
    paint.setTextAlign(Paint.Align.RIGHT);
    //paint.setTextAlign(Align.);
    paint.setTextSize(averageTextHeight / 2);
    canvas.drawText("Average " + getAverageRate(dataPoints), getWidth() - getPaddingRight(), graphHeight + textHeight + getPaddingTop() + averageTextHeight / 2, paint);

  }

  private String getAverageRate(float[] datapoints) {
    float average = 0;
    for (int i = 0; i < datapoints.length; i++) {
      average += datapoints[i];
    }
    average /= datapoints.length;
    DecimalFormat df = new DecimalFormat("###.##");
    return String.valueOf(df.format(average));
  }

  private int getCheapestDataPoint(float[] datapoints) {
    int min = 0;
    float cheapest = datapoints[0];
    for (int i = 1; i < datapoints.length; i++) {
      if (datapoints[i] < cheapest) {
        cheapest = datapoints[i];
        min = i;
      }
    }
    return min;
  }

  private void drawCurrentDateCircle(Canvas canvas, float x, float y) {
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(0xFF2E69B3);
    canvas.drawCircle(x, y, 10, paint);
  }
  private void drawNormalDateCircle(Canvas canvas, float x, float y) {
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(3);
    paint.setColor(0xFF2E69B3);
    canvas.drawCircle(x, y, 10, paint);
  }

  private void drawCheapestDateCircle(Canvas canvas, float x, float y) {
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(0xFFF26722);
    canvas.drawCircle(x, y, 10, paint);
  }

  private void drawLineChart(Canvas canvas) {


    Path path = new Path();
    path.moveTo((getXPos(0) + getXPos(1)) / 2, getYPos(dataPoints[0]));
    drawNormalDateCircle(canvas, (getXPos(0) + getXPos(1)) / 2, getYPos(dataPoints[0]));
    for (int i = 1; i < dataPoints.length; i++) {
      path.lineTo((getXPos(i) + getXPos(i + 1)) / 2, getYPos(dataPoints[i]));
    }

    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(3);
    paint.setColor(0xFF2E69B3);
    paint.setAntiAlias(true);
    canvas.drawPath(path, paint);
    if(cheapestDataPoint == 0){
      drawCheapestPrice(canvas,0);
    }else {
      drawNormalPrice(canvas,0);
    }
    for (int i = 1; i < dataPoints.length; i++) {
      if (i == cheapestDataPoint) {
        drawCheapestDateCircle(canvas, (getXPos(i) + getXPos(i + 1)) / 2, getYPos(dataPoints[i]));
        drawCheapestPrice(canvas, i);
      } else if (i == 3) {
        drawCurrentDateCircle(canvas,(getXPos(i) + getXPos(i + 1)) / 2, getYPos(dataPoints[i]));
        drawNormalPrice(canvas, i);

      } else {
        drawNormalDateCircle(canvas, (getXPos(i) + getXPos(i + 1)) / 2, getYPos(dataPoints[i]));
        drawNormalPrice(canvas, i);
      }
    }
    paint.setShadowLayer(0, 0, 0, 0);
  }

  private void drawNormalPrice(Canvas canvas, int i) {
    float xPos = (getXPos(i) + getXPos(i + 1)) / 2;
    float yPos = getYPos(dataPoints[i]);

    Paint normalTextPaint = new Paint();
    normalTextPaint.setStyle(Paint.Style.STROKE);
    normalTextPaint.setColor(Color.DKGRAY);
    normalTextPaint.setTextAlign(Paint.Align.CENTER);
    normalTextPaint.setTextSize(marginGraphAndAverageText / 2);
    DecimalFormat decimalFormat = new DecimalFormat("###");
    canvas.drawText(String.valueOf(decimalFormat.format(dataPoints[i])), xPos, yPos + 20 + marginGraphAndAverageText / 2, normalTextPaint);
  }

  private void drawCheapestPrice(Canvas canvas, int i) {
    float xPos = (getXPos(i) + getXPos(i + 1)) / 2;
    float yPos = getYPos(dataPoints[i]);
    Paint cheapestDatePricePaint = new Paint();
    cheapestDatePricePaint.setStyle(Paint.Style.STROKE);
    cheapestDatePricePaint.setColor(Color.WHITE);
    cheapestDatePricePaint.setTextAlign(Paint.Align.CENTER);
    cheapestDatePricePaint.setTextSize(marginGraphAndAverageText / 2);
    Paint cheapestDatePriceBackground = new Paint();
    cheapestDatePriceBackground.setColor(0xFFF26722);
    cheapestDatePriceBackground.setStyle(Paint.Style.FILL);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      canvas.drawRoundRect(getXPos(i), yPos + 20, getXPos(i + 1), yPos + marginGraphAndAverageText, 4, 4, cheapestDatePriceBackground);
    }else {
      canvas.drawRect(getXPos(i), yPos + 20, getXPos(i + 1), yPos + marginGraphAndAverageText, cheapestDatePriceBackground);

    }
    canvas.drawText(String.valueOf(dataPoints[i]), xPos, yPos + 20 + marginGraphAndAverageText/2, cheapestDatePricePaint);

  }

  private float getYPos(float value) {
    float height = graphHeight - marginGraphAndAverageText;
    float maxValue = getMax(dataPoints);

    // scale it to the view size
    value = (value / maxValue) * height;

    // invert it so that higher values have lower y
    value = height - value;

    // offset it to adjust for padding
    value += getPaddingTop() + textHeight * 3 / 2;

    return value;
  }

  private float getXPos(float value) {
    float width = graphWidth;
    float maxValue = dataPoints.length;

    // scale it to the view size
    value = (value / maxValue) * width;

    // offset it to adjust for padding
    value += getPaddingLeft();

    return value;
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    Log.d("width", "" + w);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
  }
}
