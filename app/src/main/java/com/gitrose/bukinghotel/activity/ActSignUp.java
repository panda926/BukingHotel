package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.FrgLogin;
import com.gitrose.bukinghotel.fragment.FrgSignUp;

public class ActSignUp extends BaseFragmentActivity {

    private FragmentManager fragmentManager;
    private FrgSignUp frgSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sign_up);

        initView();
    }

    private void initView() {

        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.frgSignUp = new FrgSignUp();
        transaction.add((int) R.id.signup_container, this.frgSignUp).commit();
    }
}
