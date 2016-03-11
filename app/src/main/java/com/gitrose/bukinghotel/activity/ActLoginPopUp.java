package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.FrgLoginPopUp;
import com.gitrose.bukinghotel.fragment.FrgSignUp;

public class ActLoginPopUp extends BaseFragmentActivity {

    private FragmentManager fragmentManager;
    private FrgLoginPopUp frgLoginPopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_login_pop_up);

        initView();
    }

    private void initView() {

        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.frgLoginPopUp = new FrgLoginPopUp();
        transaction.add((int) R.id.login_container, this.frgLoginPopUp).commit();
    }
}
