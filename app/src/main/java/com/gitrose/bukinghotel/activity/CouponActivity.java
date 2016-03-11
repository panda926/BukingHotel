package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.FrgBooking;
import com.gitrose.bukinghotel.fragment.FrgCoupon;

public class CouponActivity extends BaseFragmentActivity {

    private FrgCoupon frgCoupon;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        initView();
    }

    private void initView() {

        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.frgCoupon = new FrgCoupon();
        transaction.add((int) R.id.coupon_container, this.frgCoupon).commit();
    }
}
