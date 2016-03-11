package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.sys.MyApplication;
import com.gitrose.bukinghotel.util.Constant;
import com.gitrose.bukinghotel.view.AnimationTabHost;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends TabActivity implements View.OnClickListener {

    private static HomeActivity mainActivity;
    ImageView m_imgTodayHotel;
    ImageView m_imgReserve;
    ImageView m_imgCoupon;
    ImageView m_imgSetting;
    private Intent intent;
    private int cur_index;
    private long mExitTime;

    private AnimationTabHost tabHost;

    public HomeActivity() {

        this.intent = null;
        this.cur_index = 0;
        this.mExitTime = 0;
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(CONFIG.fontPath)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        setContentView(R.layout.activity_home);

        mainActivity = this;
        ActivityManager.getScreenManager().pushActivity(this);
        this.tabHost = (AnimationTabHost) getTabHost();

        TabSpec tabTodayHotel = this.tabHost.newTabSpec("0");
        tabTodayHotel.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
        this.intent = new Intent(this, TodayHotelActivity.class);
        tabTodayHotel.setContent(this.intent);
        this.tabHost.addTab(tabTodayHotel);

        TabSpec tabReserve = this.tabHost.newTabSpec("1");
        tabReserve.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
        this.intent = new Intent(this, ReserveActivity.class);
        tabReserve.setContent(this.intent);
        this.tabHost.addTab(tabReserve);

        TabSpec tabCoupon = this.tabHost.newTabSpec("2");
        tabCoupon.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
        this.intent = new Intent(this, CouponActivity.class);
        tabCoupon.setContent(this.intent);
        this.tabHost.addTab(tabCoupon);

        TabSpec tabSetting = this.tabHost.newTabSpec("3");
        tabSetting.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
        this.intent = new Intent(this, SettingActivity.class);
        tabSetting.setContent(this.intent);
        this.tabHost.addTab(tabSetting);

        initView();
        setUiChange(0);
        this.tabHost.setOpenAnimation(true);
    }

    private void setUiChange(int index) {

        getTabHost().setCurrentTab(index);

        switch (this.cur_index) {
            case 0:
                this.m_imgTodayHotel.setSelected(false);
                break;
            case 1:
                this.m_imgReserve.setSelected(false);
                break;
            case 2:
                this.m_imgCoupon.setSelected(false);
                break;
            case 3:
                this.m_imgSetting.setSelected(false);
                break;
        }
        switch (index) {
            case 0:
                this.m_imgTodayHotel.setSelected(true);
                break;
            case 1:
                this.m_imgReserve.setSelected(true);
                break;
            case 2:
                this.m_imgCoupon.setSelected(true);
                break;
            case 3:
                this.m_imgSetting.setSelected(true);
                break;
        }
        this.cur_index = index;
    }

    private void initView()
    {
        m_imgTodayHotel = (ImageView)findViewById(R.id.today_hotel);
        m_imgReserve = (ImageView)findViewById(R.id.reserve_history);
        m_imgCoupon = (ImageView)findViewById(R.id.coupon);
        m_imgSetting = (ImageView)findViewById(R.id.setting);

        m_imgTodayHotel.setOnClickListener(this);
        m_imgReserve.setOnClickListener(this);
        m_imgCoupon.setOnClickListener(this);
        m_imgSetting.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.today_hotel:
                setUiChange(0);
                break;

            case R.id.reserve_history:
                setUiChange(1);
                break;

            case R.id.coupon:
                setUiChange(2);
                break;

            case R.id.setting:
                setUiChange(3);
                break;
        }
    }

    protected void onPause() {
        super.overridePendingTransition(Constant.animation_1, Constant.animation_2);
        Constant.animation_1 = R.anim.main_translatex100to0;
        Constant.animation_2 = R.anim.main_translatex0tof100;
        super.onPause();
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() != 4 || event.getAction() != 0 || event.getRepeatCount() != 0) {
            return super.dispatchKeyEvent(event);
        }
        if (System.currentTimeMillis() - this.mExitTime > 2000) {
            Toast.makeText(getApplicationContext(), "'뒤로'버튼을 한번 더 클릭하시면 앱이 종료됩니다.", Toast.LENGTH_LONG).show();
            this.mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
        return true;
    }
}
