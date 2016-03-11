package com.gitrose.bukinghotel.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.adapter.CouponEntry;
import com.gitrose.bukinghotel.adapter.CouponListAdapter;
import com.gitrose.bukinghotel.fragment.FrgPortrait;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.sys.CONFIG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@SuppressLint({"NewApi", "SetJavaScriptEnabled"})
public class ActPortraitView extends BaseFragmentActivity {
    public static int FIRST_PAGE;
    public static int LOOPS;
    public static int PAGES;
    private String[] caption1;
    private String[] caption2;
    private String hid;
    private Integer idx;
    public MyPagerAdapter mPagerAdapter;
    public ViewPager mViewPager;
    private int markNowPosition;
    private int markPrevPosition;
    private String[] portraitImgs;

    class onPageChange implements OnPageChangeListener {
        onPageChange() {
        }

        public void onPageSelected(int position) {
            ActPortraitView.this.markNowPosition = position % ActPortraitView.PAGES;
            ((LinearLayout) ActPortraitView.this.findViewById(R.id.page_mark)).getChildAt(ActPortraitView.this.markPrevPosition).setBackgroundResource(R.drawable.dot_off);
            ((LinearLayout) ActPortraitView.this.findViewById(R.id.page_mark)).getChildAt(ActPortraitView.this.markNowPosition).setBackgroundResource(R.drawable.dot_on);
            ActPortraitView.this.markPrevPosition = ActPortraitView.this.markNowPosition;
            ActPortraitView.this.setPortraitMsg(ActPortraitView.this.markPrevPosition);
        }

        public void onPageScrolled(int position, float positionOffest, int positionOffsetPixels) {
            ActPortraitView.this.markNowPosition = position % ActPortraitView.PAGES;
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter implements OnPageChangeListener {
        private String[] landscapeImgs;

        public MyPagerAdapter(FragmentManager fm, String[] imgs) {
            super(fm);
            this.landscapeImgs = imgs;
        }

        public Fragment getItem(int position) {
            position %= ActPortraitView.PAGES;
            return FrgPortrait.newInstance(ActPortraitView.this, position, this.landscapeImgs[position]);
        }

        public int getCount() {
            return ActPortraitView.PAGES * ActPortraitView.LOOPS;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public ActPortraitView() {
        this.markNowPosition = 0;
        this.markPrevPosition = 0;
    }

    static {
        PAGES = 0;
        LOOPS = 1000;
        FIRST_PAGE = 0;
    }

    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_portrait_view);
        Intent intent = getIntent();
        this.hid = intent.getStringExtra("hid");
        this.idx = Integer.valueOf(intent.getIntExtra("idx", 0));
        CONFIG.useCaption = true;
        getPortraitInfo();
    }

    private void getPortraitInfo() {
//        String strUrl = "http://api.hotelnow.co.kr/product_big_image/" + this.hid;
        QGHttpRequest.getInstance().GetHotelDetailImage(this, hid, new GetHotelDetailImageResult(this));
    }

    class GetHotelDetailImageResult extends QGHttpHandler<String>
    {
        public GetHotelDetailImageResult(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {
            ActPortraitView.this.ShowhotelDetailImage(s);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Toast.makeText(ActPortraitView.this, ActPortraitView.this.getString(R.string.error_connect_problem), Toast.LENGTH_LONG).show();
        }
    }

    private void ShowhotelDetailImage(String strResult)
    {
        try {

            JSONObject response = new JSONObject(strResult);

            JSONArray data = response.getJSONArray("data");
            ActPortraitView.this.portraitImgs = new String[data.length()];
            ActPortraitView.this.caption1 = new String[data.length()];
            ActPortraitView.this.caption2 = new String[data.length()];
            for (int i = 0; i < data.length(); i++) {
                ActPortraitView.this.portraitImgs[i] = data.getJSONObject(i).getString("portrait");
                ActPortraitView.this.caption1[i] = data.getJSONObject(i).getString("caption1");
                ActPortraitView.this.caption2[i] = data.getJSONObject(i).getString("caption2");
            }
            ActPortraitView.this.showPager();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ActPortraitView.this, ActPortraitView.this.getString(R.string.error_connect_problem), Toast.LENGTH_LONG).show();
        }
    }

    private void showPager() {
        PAGES = this.portraitImgs.length;
        FIRST_PAGE = ((PAGES * LOOPS) / 2) + this.idx.intValue();
        this.markNowPosition = this.idx.intValue();
        this.mViewPager = (ViewPager) findViewById(R.id.img_pager);
        this.mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this.portraitImgs);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this.mPagerAdapter);
        this.mViewPager.setCurrentItem(FIRST_PAGE, true);
        this.mViewPager.setOffscreenPageLimit(3);
        this.mViewPager.setOnPageChangeListener(new onPageChange());
        initPageMark();
        setPortraitMsg(this.idx.intValue());
    }

    private void initPageMark() {
        for (int i = 0; i < this.portraitImgs.length; i++) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setLayoutParams(new LayoutParams(-2, -2));
            if (i == this.markNowPosition) {
                iv.setBackgroundResource(R.drawable.dot_on);
            } else {
                iv.setBackgroundResource(R.drawable.dot_off);
            }
            ((LinearLayout) findViewById(R.id.page_mark)).addView(iv);
        }
        this.markPrevPosition = this.markNowPosition;
    }

    private void setPortraitMsg(int position) {
        TextView captionTv1 = (TextView) findViewById(R.id.caption1);
        TextView captionTv2 = (TextView) findViewById(R.id.caption2);
        LinearLayout caption_area = (LinearLayout) findViewById(R.id.caption_area);
        if (this.caption1 != null) {
            if (this.caption1[position].length() > 0) {
                captionTv1.setVisibility(View.VISIBLE);
                captionTv1.setText(this.caption1[position]);
            } else {
                captionTv1.setVisibility(View.GONE);
                captionTv1.setText("");
            }
        }
        if (this.caption2 != null) {
            if (this.caption2[position].length() > 0) {
                captionTv2.setVisibility(View.VISIBLE);
                captionTv2.setText(this.caption2[position]);
            } else {
                captionTv2.setVisibility(View.GONE);
                captionTv2.setText("");
            }
        }
        if (CONFIG.useCaption) {
            caption_area.setVisibility(View.VISIBLE);
            if (this.caption1[position].length() > 0 || this.caption2[position].length() > 0) {
                caption_area.setVisibility(View.VISIBLE);
                return;
            } else {
                caption_area.setVisibility(View.GONE);
                return;
            }
        }
        caption_area.setVisibility(View.GONE);
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        super.onBackPressed();
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }
}
