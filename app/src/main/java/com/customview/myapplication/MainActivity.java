package com.customview.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import me.nlmartian.silkcal.DatePickerController;
import me.nlmartian.silkcal.DayPickerView;
import me.nlmartian.silkcal.SimpleMonthAdapter;

public class MainActivity extends AppCompatActivity implements DatePickerController{
  private DayPickerView calendarView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.lineRecyclerView);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    myRecyclerView.setLayoutManager(linearLayoutManager);

    myRecyclerView.setAdapter(new FarziAdapter(this));

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public int getMaxYear() {
    return 0;
  }

  @Override
  public void onDayOfMonthSelected(int year, int month, int day) {

  }

  @Override
  public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

  }

  public static class FarziAdapter extends RecyclerView.Adapter {

    Context context;
    float maxValue;
    float[] dataPoints = {2000, 4000, 3150, 5000, 1000, 6200, 3302, 4002, 4440};

    public FarziAdapter(Context context) {
      this.context = context;
      maxValue = getMax(dataPoints);

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.graphitem,null);
      return new farziViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      farziViewHolder myfarziViewHolder = (farziViewHolder)holder;
      if(position == 0) {
        myfarziViewHolder.myGraphItem.setPoints(-1, dataPoints[position], calculateYNext(position), getMax(dataPoints),position);
      }else if(position == dataPoints.length -1){
        myfarziViewHolder.myGraphItem.setPoints(calculateYPrevious(position),dataPoints[position],-1, getMax(dataPoints),position);
      }else {
        myfarziViewHolder.myGraphItem.setPoints(calculateYPrevious(position),dataPoints[position],calculateYNext(position), getMax(dataPoints),position);
      }
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

    private float calculateYPrevious(int position) {
//       y = mx + c;
//       m = (dataPoints[position] - dataPoints[position - 1])  because x2-x1 = 1
//          if previous x is 0 and new x is 1 then c = dataPoints[position -1]
//          so at x = 1/2 y is m*1/2 + c
//          hence, yprevious is (dataPoints[position] -dataPoints[position -1])/2 + dataPoints[position -1]
      return (dataPoints[position]/2 + dataPoints[position -1]/2);
    }

    private float calculateYNext(int position) {
      // y = mx + c;
      // m = dataPoints[position +1] - datapoints[position] because x2-x1 =1
      // if this x is 0 and next x is 1 then c = datapoints[position]
      // so at x = 1/2 y = m*1/2 + c
      //hence yNext = (datapoints[position+1] -datapoints[position])/2 + datapoints[position]
      //
      return (dataPoints[position+1]/2  + dataPoints[position]/2);
    }

    @Override
    public int getItemCount() {
      return dataPoints.length;
    }

    public static class farziViewHolder extends RecyclerView.ViewHolder {
      MyGraphItem myGraphItem;
      public farziViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        myGraphItem = (MyGraphItem) itemView.findViewById(R.id.mygraphitem);
      }
    }
  }
}
