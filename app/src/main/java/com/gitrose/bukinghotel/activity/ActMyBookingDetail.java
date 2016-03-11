package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;

public class ActMyBookingDetail extends Activity {

    private SharedPreferences _preferences;
    private boolean account_pay;
    private String bId;
    private TextView booking_hide;
//    private MenuItem call_btn;
    private boolean call_btn_show;
    private boolean fromReservation;
    private String hId;
    private String hotel_name;
    private String hotel_phone_number;
    private String lat;
    private String lng;
//    private DialogSnsRecommend mDialog;
//    private SimpleFacebook mSimpleFacebook;
    private boolean showSnsDialog;
    private String title;
    private RelativeLayout wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_my_booking_detail);

        this.wrapper = (RelativeLayout) findViewById(R.id.wrapper);
        this.booking_hide = (TextView) findViewById(R.id.booking_hide);
//        this.mSimpleFacebook = SimpleFacebook.getInstance(this);
        this._preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (this._preferences.getString("userid", null) == null) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
        Intent intent = getIntent();
        if (intent != null) {
            this.bId = intent.getStringExtra("bid");
            this.fromReservation = intent.getBooleanExtra("reservation", false);
            this.account_pay = intent.getBooleanExtra("account_pay", false);
//            VolleyUtil.getRequestQueue().add(new JsonObjectRequest(0, "http://api.hotelnow.co.kr/booking/detail/" + this.bId, null, createMyBookingSuccessListener(), bookingErrorListener()));
        }
    }
}
