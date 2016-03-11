package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gitrose.bukinghotel.R;

import java.util.Calendar;

public class SelectDateActivity extends BaseActivity {

//    private TextView YearMonthTv;
//    private ExpCalendarView expCalendarView;

//    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

//        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
//        dayPickerView.setController(this);

//        expCalendarView = ((ExpCalendarView) findViewById(R.id.calendar_exp));
//        YearMonthTv = (TextView) findViewById(R.id.main_YYMM_Tv);
//        YearMonthTv.setText(Calendar.getInstance().get(Calendar.YEAR) + "년" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "월");
//
////      Set up listeners.
//        expCalendarView.setOnDateClickListener(new OnExpDateClickListener()).setOnMonthScrollListener(new OnMonthScrollListener() {
//            @Override
//            public void onMonthChange(int year, int month) {
//                YearMonthTv.setText(String.format("%d년%d월", year, month));
//            }
//
//            @Override
//            public void onMonthScroll(float positionOffset) {
////                Log.i("listener", "onMonthScroll:" + positionOffset);
//            }
//        });
//
//        imageInit();

    }


//    private boolean ifExpand = true;
//
//    private void imageInit() {
//        final ImageView expandIV = (ImageView) findViewById(R.id.main_expandIV);
//        expandIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ifExpand) {
//                    CellConfig.Month2WeekPos = CellConfig.middlePosition;
//                    CellConfig.ifMonth = false;
//                    expandIV.setImageResource(R.mipmap.icon_arrow_down);
//                    expCalendarView.shrink();
//                } else {
//                    CellConfig.Week2MonthPos = CellConfig.middlePosition;
//                    CellConfig.ifMonth = true;
//                    expandIV.setImageResource(R.mipmap.icon_arrow_up);
//                    expCalendarView.expand();
//                }
//                ifExpand = !ifExpand;
//            }
//        });
//    }
}
