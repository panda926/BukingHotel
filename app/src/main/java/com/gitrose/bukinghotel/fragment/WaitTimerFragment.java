package com.gitrose.bukinghotel.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.BaseFragmentActivity;
import com.gitrose.bukinghotel.util.DailyPreference;
import com.gitrose.bukinghotel.util.SaleTime;
import com.gitrose.bukinghotel.util.WakeLock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by GITRose on 1/26/2016.
 */
public class WaitTimerFragment extends BaseFragment implements View.OnClickListener {

    private static Handler mHandler;
    private TextView tvTimer;
    private TextView alarmTextView;
    private ImageView alarmImageView;
    private View alarmTimerLayout;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private SaleTime saleTime;
    private long longVal1;
    private TYPE type;

    public enum TYPE {
        HOTEL,
        FNB
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        BaseFragmentActivity baseActivity = (BaseFragmentActivity) getActivity();
        if (baseActivity == null) {
            return null;
        }
        try {
            View inflate = layoutInflater.inflate(R.layout.fragment_wait_timer, viewGroup, false);
            this.saleTime = (SaleTime) getArguments().getParcelable("saletime");
            this.type = TYPE.valueOf(getArguments().getString("type"));
            this.alarmManager = (AlarmManager) baseActivity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//            this.pendingIntent = PendingIntent.getBroadcast(baseActivity.getApplicationContext(), 0, new Intent(baseActivity.getApplicationContext(), AlarmBroadcastReceiver.class), 134217728);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.open_stanby_bg_fnb);
            this.tvTimer = (TextView) inflate.findViewById(R.id.tv_timer);
            TextView textView = (TextView) inflate.findViewById(R.id.tv_wait_timer_main);
            TextView textView2 = (TextView) inflate.findViewById(R.id.tv_wait_timer_sub);
            this.alarmTimerLayout = inflate.findViewById(R.id.alarmTimerLayout);
            this.alarmTimerLayout.setOnClickListener(this);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("aa H", Locale.KOREA);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            boolean enabledOpeningAlarm = DailyPreference.getInstance(baseActivity).getEnabledOpeningAlarm();
            switch (C13703.arrayInt[this.type.ordinal()]) {
                case HeaderViewListener.STATE_MINIMIZED /*1*/:
                    imageView.setImageResource(R.drawable.open_stanby_bg);
                    textView.setText(simpleDateFormat.format(Long.valueOf(this.saleTime.getOpenTime())) + getString(R.string.prefix_wait_timer_frag_todays_hotel_open));
                    textView2.setText(R.string.frag_wait_timer_hotel_msg);
                    m2476a(baseActivity, this.alarmTimerLayout, enabledOpeningAlarm);
                    break;
//                case HeaderViewListener.STATE_HIDDEN /*2*/:
//                    imageView.setImageResource(R.drawable.open_stanby_bg_fnb);
//                    textView.setTextColor(getResources().getColor(R.color.white));
//                    textView.setText(simpleDateFormat.format(Long.valueOf(this.saleTime.getOpenTime())) + getString(R.string.prefix_wait_timer_frag_todays_fnb_open));
//                    textView2.setTextColor(getResources().getColor(R.color.white));
//                    textView2.setText(R.string.frag_wait_timer_fnb_msg);
//                    this.tvTimer.setTextColor(getResources().getColor(R.color.white));
//                    m2476a(baseActivity, this.alarmTimerLayout, enabledOpeningAlarm);
//                    break;
            }
            m2480e();
            return inflate;
        } catch (OutOfMemoryError e) {
//            Util.finishOutOfMemory(baseActivity);
            return null;
        }
    }

    private void m2480e() {
        BaseFragmentActivity baseActivity = (BaseFragmentActivity) getActivity();
        if (baseActivity != null) {
            this.longVal1 = new Date(this.saleTime.getOpenTime()).getTime() - new Date(this.saleTime.getCurrentTime()).getTime();
            m2475a(this.longVal1);
            WakeLock.acquireWakeLock(baseActivity.getApplicationContext(), 1);
            mHandler = new C13692(this);
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    private void m2475a(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("KST"));
        this.tvTimer.setText(simpleDateFormat.format(Long.valueOf(j)));
    }

    class C13692 extends Handler {
        final WaitTimerFragment frgWaitTimer;

        C13692(WaitTimerFragment waitTimerFragment) {
            this.frgWaitTimer = waitTimerFragment;
        }

        public void handleMessage(Message message) {
            BaseFragmentActivity baseActivity = (BaseFragmentActivity) this.frgWaitTimer.getActivity();
            if (baseActivity != null && !baseActivity.isFinishing()) {
                this.frgWaitTimer.longVal1 = this.frgWaitTimer.longVal1 - 1000;
                if (this.frgWaitTimer.longVal1 > 0) {
                    this.frgWaitTimer.m2475a(this.frgWaitTimer.longVal1);
                    sendEmptyMessageDelayed(0, 1000);
                    return;
                }
                removeMessages(0);
                WakeLock.releaseWakeLock();
                if (WaitTimerFragment.mHandler != null) {
//                    ((MainActivity) baseActivity).replaceFragment(((MainActivity) baseActivity).getFragment(0), String.valueOf(0));
                    WaitTimerFragment.mHandler = null;
                }
            }
        }
    }

    private void m2476a(BaseFragmentActivity baseActivity, View view, boolean z) {
        if (this.alarmTextView == null) {
            this.alarmTextView = (TextView) view.findViewById(R.id.alarmTextView);
        }
        if (this.alarmImageView == null) {
            this.alarmImageView = (ImageView) view.findViewById(R.id.alarmImageView);
        }
        if (z) {
            this.alarmTextView.setText(getString(R.string.frag_wait_timer_off));
            this.alarmImageView.setImageResource(R.drawable.open_stanby_ic_alert_off);
            return;
        }
        this.alarmTextView.setText(getString(R.string.frag_wait_timer_on));
        this.alarmImageView.setImageResource(R.drawable.open_stanby_ic_alert);
    }

    public interface HeaderViewListener {
        public static final int STATE_HIDDEN = 2;
        public static final int STATE_MINIMIZED = 1;
        public static final int STATE_VISIBLE = 0;

        void onStateChanged(View view, int i);
    }

    static class C13703 {
        static final int[] arrayInt;

        static {
            arrayInt = new int[TYPE.values().length];
            try {
                arrayInt[TYPE.HOTEL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                arrayInt[TYPE.FNB.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == this.alarmTimerLayout.getId()) {
            BaseFragmentActivity baseActivity = (BaseFragmentActivity) getActivity();
            if (baseActivity != null && !baseActivity.isFinishing()) {
                m2477a(!DailyPreference.getInstance(baseActivity).getEnabledOpeningAlarm());
            }
        }
    }

    private void m2477a(boolean z) {
        BaseFragmentActivity baseActivity = (BaseFragmentActivity) getActivity();
        if (baseActivity != null && !baseActivity.isFinishing()) {
            DailyPreference.getInstance(baseActivity).setEnabledOpeningAlarm(z);
            if (z) {
                this.alarmTextView.setText(getString(R.string.frag_wait_timer_off));
                this.alarmImageView.setImageResource(R.drawable.open_stanby_ic_alert_off);
                this.alarmManager.set(0, System.currentTimeMillis() + this.longVal1, this.pendingIntent);
                showToast(getString(R.string.frag_wait_timer_set), 0, true);
                return;
            }
            this.alarmTextView.setText(getString(R.string.frag_wait_timer_on));
            this.alarmImageView.setImageResource(R.drawable.open_stanby_ic_alert);
            this.alarmManager.cancel(this.pendingIntent);
            showToast(getString(R.string.frag_wait_timer_cancel), 0, true);
        }
    }
}
