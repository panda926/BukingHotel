package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.Display;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.sys.MyApplication;
import com.gitrose.bukinghotel.util.Constant;
import com.gitrose.bukinghotel.util.HotelUtil;
import com.gitrose.bukinghotel.util.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.BuildConfig;

public class LoadingActivity extends BaseActivity {

    public static final int LOADING_SECOND = 2;
    public static final int TIME_WHAT = 1;
    private MyApplication mApp;
    private int time;
    private SharedPreferences _preferences;
    private Handler sHandler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);



        int height;
        SharedPreferences.Editor prefEditor;
        Display display = getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 11) {
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        } else {
            height = display.getHeight();
        }

        this._preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String today = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
//        if (this._preferences.getBoolean("flag_first_executed", false))
        {
            if (this._preferences.getBoolean("flag_use_location", false)) {
                if (HotelUtil.chkLocationService(getApplicationContext())) {
                    setPreferenceValues("flag_use_location", true);
                } else {
                    setPreferenceValues("flag_use_location", false);
                }

            }
            if (!today.equals(this._preferences.getString("last_launch_date", null))) {
                prefEditor = this._preferences.edit();
                prefEditor.putString("last_launch_date", today);
                prefEditor.commit();
//                registerInBackground();
            }
            checkSeverInfo();
        }
//        else
//        {
//            registerInBackground();
//            showLocationAlert();
//        }

        float listheight = (float) HotelUtil.getListHeight(this);
        prefEditor = this._preferences.edit();
        prefEditor.putFloat("list_height", listheight);
        prefEditor.commit();


    }
    
    private void checkSeverInfo()
    {
        SharedPreferences.Editor prefEditor = LoadingActivity.this._preferences.edit();
        String uid = LoadingActivity.this._preferences.getString("userid", null);
        String umi = LoadingActivity.this._preferences.getString("moreinfo", null);

        uid = null;
        umi = null;

        if (uid == null || umi == null) {
            prefEditor.putString("email", null);
            prefEditor.putString("username", null);
            prefEditor.putString("phone", null);
            prefEditor.putString("userid", null);
            prefEditor.commit();
            LoadingActivity.this.startHandler();
            return;
        }
        LoadingActivity.this.authCheck();
    }

    public void startHandler() {
//        if (checkPlayServices()) 
//        {
//            this.sHandler = new Handler();
//            this.sHandler.postDelayed(new C05507(), 300);
//        }

        this.mApp = MyApplication.getInstance();
        this.mApp.isFirstApp = ((Boolean) SpUtils.getFromLocal(this, Constant.KEY_ISFIRST, Boolean.valueOf(true))).booleanValue();
        Message msg = this.mHandlder.obtainMessage();
        msg.what = TIME_WHAT;
        this.mHandlder.sendMessageDelayed(msg, 1000);
    }

    public void authCheck() {
        String ui = this._preferences.getString("userid", null);
        String umi = this._preferences.getString("moreinfo", null);

        QGHttpRequest.getInstance().AuthCheck(this, ui, umi, new AuthCheckResult(this));
//        String url = CONFIG.authcheckUrl;
//        JSONObject params = new JSONObject();
//        try {
//            params.put("ui", ui);
//            params.put("umi", umi);
//        } catch (JSONException e) {
//        }
//        VolleyUtil.getRequestQueue().add(new JsonObjectRequest(1, url, params, new C11473(), new C11484()));
    }

    class AuthCheckResult extends QGHttpHandler<String>
    {

        public AuthCheckResult(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {
            LoadingActivity.this.SetAuthCheckResult(s);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Toast.makeText(LoadingActivity.this.getApplicationContext(), LoadingActivity.this.getString(R.string.error_connect_problem), Toast.LENGTH_LONG).show();
        }
    }

    private void SetAuthCheckResult(String strResult)
    {
        try {


            JSONObject response = new JSONObject(strResult);
            if (response.getString("result").equals("success")) {

                SharedPreferences.Editor prefEditor = LoadingActivity.this._preferences.edit();
                prefEditor.putString("email", null);
                prefEditor.putString("username", null);
                prefEditor.putString("phone", null);
                prefEditor.putString("userid", null);
                prefEditor.commit();
            }

            LoadingActivity.this.startHandler();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoadingActivity.this.getApplicationContext(), LoadingActivity.this.getString(R.string.error_connect_problem), Toast.LENGTH_LONG).show();
        }
    }

//    class C05507 implements Runnable {
//        C05507() {
//        }
//
//        public void run() {
//            if (LoadingActivity.this.mLocationClient != null) {
//                try {
//                    HotelUtil.userLocation = LoadingActivity.this.mLocationClient.getLastLocation();
//                } catch (Exception e) {
//                }
//            }
//            if (!LoadingActivity.this.first_executed) {
//                LoadingActivity.this.setPreferenceValues("flag_first_executed", true);
//            }
//            LoadingActivity.this.startActivity(new Intent(LoadingActivity.this, ActMain.class));
//            LoadingActivity.this.finish();
//        }
//    }

    public void showLocationAlert() {
//        HotelUtil.getAlertTheme(this).setTitle(getString(R.string.alert_notice)).setMessage(getString(R.string.ask_use_location)).setCancelable(false).setNegativeButton(getString(R.string.alert_no), new C05485()).setPositiveButton(getString(R.string.alert_yes), new C05496()).show();
    }

    public void setPreferenceValues(String prefKey, boolean prefVal) {
        SharedPreferences.Editor prefEditor = this._preferences.edit();
        prefEditor.putBoolean(prefKey, prefVal);
        prefEditor.commit();
    }

    private Handler mHandlder = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (msg.what != LoadingActivity.TIME_WHAT) {
                return;
            }
            if (LoadingActivity.this.time < LoadingActivity.LOADING_SECOND) {
                LoadingActivity LoadingActivity = LoadingActivity.this;
                LoadingActivity.time = LoadingActivity.time + LoadingActivity.TIME_WHAT;
                Message msg1 = obtainMessage();
                msg1.what = LoadingActivity.TIME_WHAT;
                sendMessageDelayed(msg1, 1000);
            } else if (LoadingActivity.this.mApp.isFirstApp) {
                LoadingActivity.this.finish();
                SpUtils.saveToLocal(LoadingActivity.this, Constant.KEY_ISFIRST, Boolean.valueOf(false));
                LoadingActivity.this.startActivityForNew(new Intent(LoadingActivity.this, IntroActivity.class));
            } else {
                LoadingActivity.this.finish();
                if (MyApplication.getInstance().isPassAuth) {
//                    LoadingActivity.this.startActivityForNew(new Intent(LoadingActivity.this, HomeActivity.class));
                } else {
                    LoadingActivity.this.startActivityForNew(new Intent(LoadingActivity.this, WaitTimerActivity.class));
                }
            }
        }
    };
}
