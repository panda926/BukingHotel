package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.HotelFragment;
import com.gitrose.bukinghotel.view.CustomPopupWindow;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TodayHotelActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static TodayHotelActivity hotelActivity;
    private Handler mHandler;
    private FragmentManager fragmentManager;
    private HotelFragment hotelFragment;
    private ImageView m_imgSearch;
    private ImageView m_menuSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_hotel);

        this.mHandler = new Handler();
        hotelActivity = this;

        initView();
    }

    private void initView() {

        this.m_imgSearch = (ImageView)findViewById(R.id.menu_search);
        this.m_imgSearch.setOnClickListener(this);
        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.hotelFragment = new HotelFragment();
        transaction.add((int) R.id.hotel_container, this.hotelFragment).commit();
        this.m_menuSort = (ImageView)findViewById(R.id.menu_sort);
        this.m_menuSort.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.menu_search:
                ShowSearchActivity();
                break;

            case R.id.menu_sort:
                CustomPopupWindow popupWindow = new CustomPopupWindow(this);
                popupWindow.showAtDropDownRight(this.m_menuSort);
                break;
        }
    }

    private void ShowSearchActivity()
    {
        this.startActivityForResult(new Intent(this, ActSearch.class), 99);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
