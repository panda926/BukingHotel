package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.FrgLogin;
import com.gitrose.bukinghotel.fragment.FrgSetting;

public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private FrgLogin frgLogin;
    private ImageView imgClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        this.imgClose = (ImageView)findViewById(R.id.menu_sort);
        this.imgClose.setOnClickListener(this);
        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.frgLogin = new FrgLogin();
        transaction.add((int) R.id.login_container, this.frgLogin).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.menu_sort:
                this.finish();
                break;
        }
    }
}
