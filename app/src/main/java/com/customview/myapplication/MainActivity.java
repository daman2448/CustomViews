package com.customview.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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
    /*calendarView = (DayPickerView) findViewById(R.id.calendar_view);
    calendarView.setController(this);*/
    ListView listView = (ListView) findViewById(R.id.listView);
    ArrayList<Integer> items = new ArrayList<>();
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    items.add(1);
    ArrayAdapter<Integer> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
    LayoutInflater inflater = getLayoutInflater();
    View v = inflater.inflate(R.layout.mygraph, null);
    listView.addHeaderView(v);
    listView.setAdapter(itemsAdapter);
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
}
