package com.gitrose.bukinghotel.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActMyBookingDetail;
import com.gitrose.bukinghotel.adapter.MyBookingListAdapter;
import com.gitrose.bukinghotel.adapter.MyBookingListEntry;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GITRose on 1/20/2016.
 */
public class FrgBooking extends BaseFragment {

    private static final int PAGE_SIZE = 20;

    private TextView no_booking;
    private ProgressBar progressbar;
    private EndlessScrollListener endlessScrollListener;
    private ListView mMainListView;
    private Runnable mRunnable;
    private Handler mHandler;
    private MyBookingListAdapter mAdapter;
    private ArrayList<MyBookingListEntry> mEntries;
    private boolean mHasData;
    private boolean mInError;
    private String cookie;

    public FrgBooking() {

        this.mHasData = false;
        this.mInError = false;
        this.mEntries = new ArrayList();
    }

    public class EndlessScrollListener implements AbsListView.OnScrollListener {

        private int currentPage;
        private boolean loading;
        private int previousTotal;
        private int visibleThreshold;

        public EndlessScrollListener() {
            this.visibleThreshold = 5;
            this.currentPage = 1;
            this.previousTotal = 0;
            this.loading = true;
        }

        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = 5;
            this.currentPage = 1;
            this.previousTotal = 0;
            this.loading = true;
            this.visibleThreshold = visibleThreshold;
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (this.loading && totalItemCount > this.previousTotal) {
                this.loading = false;
                this.previousTotal = totalItemCount;
                this.currentPage++;
            }
            if (!this.loading && totalItemCount - visibleItemCount <= this.visibleThreshold + firstVisibleItem) {
//                FrgBooking.this.loadPage();
                this.loading = true;
            }
        }

        public void onScrollStateChanged(AbsListView arg0, int arg1) {
        }

        public int getCurrentPage() {
            return this.currentPage;
        }

        public void initialize() {
            this.currentPage = 1;
            this.previousTotal = 0;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    class C06271 implements AdapterView.OnItemClickListener {
        C06271() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            TextView tv = (TextView) view.findViewById(R.id.bid);
            Intent intent = new Intent(FrgBooking.this.getActivity(), ActMyBookingDetail.class);
            intent.putExtra("bid", tv.getText().toString());
            FrgBooking.this.startActivityForResult(intent, 88);
            FrgBooking.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.no_booking = (TextView) getView().findViewById(R.id.no_booking);
        this.progressbar = (ProgressBar) getView().findViewById(R.id.loading_progress);
        this.endlessScrollListener = new EndlessScrollListener();
        this.mMainListView = (ListView) getView().findViewById(R.id.booking_list);
        this.mAdapter = new MyBookingListAdapter(getActivity(), 0, this.mEntries);
        this.mMainListView.setAdapter(this.mAdapter);
        this.mMainListView.setOnScrollListener(this.endlessScrollListener);
        this.mMainListView.setOnItemClickListener(new C06271());
        cookie = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("userid", null);
        if (!this.mHasData && cookie != null) {
            this.no_booking.setVisibility(View.GONE);
            this.mRunnable = new RunThread();
            this.mHandler = new Handler();
            this.mHandler.postDelayed(this.mRunnable, 300);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        cookie = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("userid", null);
        if (!this.mHasData && cookie != null) {
            this.no_booking.setVisibility(View.GONE);
            this.mRunnable = new RunThread();
            this.mHandler = new Handler();
            this.mHandler.postDelayed(this.mRunnable, 300);
        }
    }

    class RunThread implements Runnable {
        RunThread() {
        }

        public void run() {
            FrgBooking.this.loadPage();
        }
    }

    private void loadPage() {
        if (!this.mHasData) {
            this.mHasData = true;
            this.progressbar.setVisibility(View.VISIBLE);

//            String strURL = "http://api.hotelnow.co.kr/booking/list/" + this.endlessScrollListener.getCurrentPage() + "/" + PAGE_SIZE;
            QGHttpRequest.getInstance().getReserveHistory(this.getActivity(), cookie, new GetReserveList(this.getActivity()));
        }
    }

    class GetReserveList extends QGHttpHandler<String>
    {

        public GetReserveList(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {
            FrgBooking.this.ShowReserveList(s);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);

            FrgBooking.this.mInError = true;
            Toast.makeText(FrgBooking.this.getActivity(), FrgBooking.this.getString(R.string.error_booking_info), Toast.LENGTH_LONG).show();
        }
    }

    private void ShowReserveList(String strResult)
    {
        try {

            JSONObject response = new JSONObject(strResult);

            FrgBooking.this.progressbar.setVisibility(View.GONE);
            JSONArray feed = response.getJSONArray("data");
            for (int i = 0; i < feed.length(); i++) {
                JSONObject entry = feed.getJSONObject(i);
                FrgBooking.this.mEntries.add(new MyBookingListEntry(entry.getString("id"), entry.getString("status"), entry.getString("book_explain"), entry.getString("progress_explain"), entry.getString("hotel_name"), entry.getString("room_name"), entry.getString("checkin_date"), entry.getString("checkout_date"), entry.getString("b_img")));
            }
            FrgBooking.this.mAdapter.notifyDataSetChanged();
            FrgBooking.this.mHasData = false;
            if (FrgBooking.this.mAdapter.getCount() == 0) {
                FrgBooking.this.no_booking.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
