package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.FrgBooking;
import com.gitrose.bukinghotel.fragment.HotelFragment;

public class ReserveActivity extends BaseFragmentActivity {

    private FrgBooking frgBooking;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        initView();
    }

    private void initView() {

        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.frgBooking = new FrgBooking();
        transaction.add((int) R.id.reserve_container, this.frgBooking).commit();
    }
}
