package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.sys.MyApplication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by GITRose on 1/18/2016.
 */
public class BaseActivity extends Activity {

    protected MyApplication cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(CONFIG.fontPath)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        this.cache = (MyApplication) getApplicationContext();
        ActivityManager.getScreenManager().pushActivity(this);
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (MyApplication.getInstance() != null) {
            MyApplication.getInstance().getmRequestQueue().cancelAll((Object) this);
        }
        ActivityManager.getScreenManager().popActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public View getViewById(int id) {
        return findViewById(id);
    }

    public void startActivityForNew(Intent intent) {
        startActivity(intent);
        animationForNew();
    }

    @Override
    public void finish() {
        super.finish();

        animationForOld();
    }

    public Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
        translateAnimation.setInterpolator(new CycleInterpolator((float) counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    public void animationForNew() {
        overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        animationForNew();
    }

    public void animationForOld() {
        overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
    }

    public void animationForBottom() {
        overridePendingTransition(R.anim.main_translatey100to0, R.anim.main_translatey0tof100);
    }

    public void animationForOTop() {
        overridePendingTransition(R.anim.main_translateyf100to0, R.anim.main_translatey0to100);
    }

    public void superFinish() {
        super.finish();
    }
}
