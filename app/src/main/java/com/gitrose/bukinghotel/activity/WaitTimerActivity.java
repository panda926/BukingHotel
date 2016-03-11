package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.FrgLoginPopUp;
import com.gitrose.bukinghotel.fragment.WaitTimerFragment;
import com.gitrose.bukinghotel.util.SaleTime;

public class WaitTimerActivity extends BaseFragmentActivity {
    private FragmentManager fragmentManager;
    private WaitTimerFragment frgWaitTimer;
    private SaleTime saleTime;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_timer);
        
        initView();
    }

    @Override
    public void onBackPressed() {

        this.startActivityForNew(new Intent(this, HomeActivity.class));
        super.onBackPressed();
    }

    private void initView() {
        saleTime = new SaleTime();

        saleTime.setCurrentTime(30);
        saleTime.setOpenTime(100);
        saleTime.setCloseTime(100);
        saleTime.setDailyTime(50);
        
        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.frgWaitTimer = new WaitTimerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("saletime", saleTime);
        bundle.putString("type", WaitTimerFragment.TYPE.HOTEL.name());
        frgWaitTimer.setArguments(bundle);
        transaction.add((int) R.id.login_container, this.frgWaitTimer).commit();
    }
    
}
