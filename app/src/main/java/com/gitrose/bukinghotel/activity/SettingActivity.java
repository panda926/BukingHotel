package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.FrgSetting;
import com.gitrose.bukinghotel.fragment.HotelFragment;

public class SettingActivity extends BaseFragmentActivity {

    private FragmentManager fragmentManager;
    private FrgSetting frgSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {

        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.frgSetting = new FrgSetting();
        transaction.add((int) R.id.setting_container, this.frgSetting).commit();
    }
}
