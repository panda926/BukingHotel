package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.IntroGuideFragment;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends BaseFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private FragmentPagerAdapter paperAdapter;
    private ViewPager introPaper;
    private LinearLayout llIntroStart;
    private TextView tvSkip;
    private TextView tvStart;
    private List<Integer> listInteger1;
    private List<String> listString1;
    private List<String> listString2;
    private List<Integer> listInteger2;
    private int nVal;
    public SharedPreferences sharedPreference;
    private CirclePageIndicator pageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        } else {
//            getWindow().setFlags(Place.TYPE_SUBLOCALITY_LEVEL_2, Place.TYPE_SUBLOCALITY_LEVEL_2);
//        }

        this.introPaper = (ViewPager) findViewById(R.id.intro_pager);
        this.llIntroStart = (LinearLayout) findViewById(R.id.ll_intro_start);
        this.tvSkip = (TextView) findViewById(R.id.tv_skip);
        this.tvStart = (TextView) findViewById(R.id.tv_start);
        initArray();
        this.paperAdapter = new IntroPaperAdapter(this, getSupportFragmentManager());
        this.introPaper.setAdapter(this.paperAdapter);
        this.pageIndicator = (CirclePageIndicator)findViewById(R.id.intro_indicator);
        this.pageIndicator.setViewPager(introPaper);
        this.pageIndicator.setSnap(true);
        this.pageIndicator.setOnPageChangeListener(this);
        this.llIntroStart.setOnClickListener(this);
        this.paperAdapter.notifyDataSetChanged();
        this.tvSkip.setOnClickListener(this);
    }

    class IntroPaperAdapter extends FragmentPagerAdapter {
        final IntroActivity activity;

        IntroPaperAdapter(IntroActivity introActivity, FragmentManager fragmentManager) {

            super(fragmentManager);
            this.activity = introActivity;

        }

        public Fragment getItem(int i) {
            return IntroGuideFragment.newInstance(IntroActivity.this,
                            ((Integer) this.activity.listInteger1.get(i)).intValue(),
                    (String) this.activity.listString1.get(i),
                    ((Integer) this.activity.listInteger2.get(i)).intValue(),
                    (String) this.activity.listString2.get(i));
        }

        public int getCount() {
            return this.activity.listString1.size();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == this.llIntroStart.getId() || view.getId() == this.tvSkip.getId()) {
            SetPreferenceValue();
            finish();
        }
    }

    public void onBackPressed() {
        SetPreferenceValue();
        super.onBackPressed();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void onPageSelected(int i) {
        this.nVal = i;
        if (i == this.listString1.size() - 1) {
            this.llIntroStart.setVisibility(View.VISIBLE);
            this.tvSkip.setVisibility(View.GONE);
            this.llIntroStart.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
            this.tvSkip.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
            return;
        }
        this.llIntroStart.setVisibility(View.GONE);
        this.tvSkip.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void finish() {
        super.finish();
        this.startActivityForNew(new Intent(this, HomeActivity.class));
        overridePendingTransition(R.anim.hold, R.anim.fade_out);
    }

    private void SetPreferenceValue() {
//        SharedPreferences.Editor edit = this.sharedPreference.edit();
//        edit.putBoolean(Constants.KEY_PREFERENCE_SHOW_GUIDE, false);
//        edit.commit();
    }

    private void initArray() {
        this.listInteger1 = new ArrayList();
        this.listString1 = new ArrayList();
        this.listString2 = new ArrayList();
        this.listInteger2 = new ArrayList();
        this.listString1.add(getString(R.string.guide_title_1));
        this.listString1.add(getString(R.string.guide_title_2));
        this.listString1.add(getString(R.string.guide_title_3));
        this.listString1.add(getString(R.string.guide_title_4));
        this.listString2.add(getString(R.string.guide_desc_1));
        this.listString2.add(getString(R.string.guide_desc_2));
        this.listString2.add(getString(R.string.guide_desc_3));
        this.listString2.add(getString(R.string.guide_desc_4));

        this.listInteger1.add(Integer.valueOf(R.drawable.img_bg_guide1));
        this.listInteger1.add(Integer.valueOf(R.drawable.img_bg_guide2));
        this.listInteger1.add(Integer.valueOf(R.drawable.img_bg_guide3));
        this.listInteger1.add(Integer.valueOf(R.drawable.img_bg_guide4));

        this.listInteger2.add(Integer.valueOf(R.drawable.img_ic_guide_curation));
        this.listInteger2.add(Integer.valueOf(R.drawable.img_ic_guide_curation));
        this.listInteger2.add(Integer.valueOf(R.drawable.img_ic_guide_curation));
        this.listInteger2.add(Integer.valueOf(R.drawable.img_ic_guide_curation));
    }
}
