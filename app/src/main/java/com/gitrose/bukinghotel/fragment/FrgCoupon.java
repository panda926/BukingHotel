package com.gitrose.bukinghotel.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActLoginPopUp;
import com.gitrose.bukinghotel.adapter.CouponEntry;
import com.gitrose.bukinghotel.adapter.CouponListAdapter;
import com.gitrose.bukinghotel.adapter.MyBookingListEntry;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.sys.CONFIG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by GITRose on 1/20/2016.
 */
public class FrgCoupon extends BaseFragment {

    private SharedPreferences _preferences;
    private Button btn_ok;
    private EditText code;
    private String cookie;
    private ArrayList<CouponEntry> cpnEntries;
    private ListView cpnListview;
    private LinearLayout cpn_list;
    private Typeface font;
    private CouponListAdapter mAdapter;
    private Handler mHandler;
    private Runnable mRunnable;
    private ProgressBar progressbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_coupon, container, false);
        this.code = (EditText) v.findViewById(R.id.code);
        this.btn_ok = (Button) v.findViewById(R.id.btn_ok);
        this.cpnListview = (ListView) v.findViewById(R.id.listview);
        this.progressbar = (ProgressBar) v.findViewById(R.id.loading_progress);
        this.cpn_list = (LinearLayout) v.findViewById(R.id.cpn_list);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this._preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.cookie = this._preferences.getString("userid", null);
        if (this.cookie != null) {
            this.cpn_list.setVisibility(View.VISIBLE);
        } else {
            this.cpn_list.setVisibility(View.GONE);
        }
        this.mRunnable = new GetCouponListHandler();
        this.mHandler = new Handler();
        this.mHandler.postDelayed(this.mRunnable, 300);
        this.font = Typeface.createFromAsset(getActivity().getAssets(), CONFIG.fontPath);
        this.code.setTypeface(this.font);
//        this.btn_ok.setOnClickListener(new C06302());
        this.btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrgCoupon.this.couponApply();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        this._preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.cookie = this._preferences.getString("userid", null);
        if (this.cookie != null) {
            this.cpn_list.setVisibility(View.VISIBLE);
        } else {
            this.cpn_list.setVisibility(View.GONE);
        }
        this.mRunnable = new GetCouponListHandler();
        this.mHandler = new Handler();
        this.mHandler.postDelayed(this.mRunnable, 300);
    }

    private void couponApply() {
        if (this.cookie == null) {
            Toast.makeText(getActivity(), "로그인 후 이용 가능합니다.", Toast.LENGTH_LONG).show();
            getActivity().startActivity(new Intent(getActivity(), ActLoginPopUp.class));
            getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_down);
            return;
        }

        String inputCode = this.code.getText().toString().trim();
        if (!inputCode.equals("") && !inputCode.equals(null)) {
//            JSONObject params = new JSONObject();
//            try {
//                params.put("pcode", inputCode);
//            } catch (JSONException e) {
//            }
//            VolleyUtil.getRequestQueue().add(new JsonObjectRequest(1, CONFIG.promotionUrl, params, new C11813(), new C11824()));

            QGHttpRequest.getInstance().GetCouponInfo(this.getActivity(), inputCode, new GetCouponResult(this.getActivity()));
        }
    }

    class GetCouponResult extends QGHttpHandler<String>
    {

        public GetCouponResult(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {
            FrgCoupon.this.SetCouponInfo(s);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Toast toast = Toast.makeText(FrgCoupon.this.getActivity(), responseString, Toast.LENGTH_LONG);
            toast.setGravity(17, 0, 0);
            toast.show();
        }
    }

    private void SetCouponInfo(String strResult)
    {
        try {

            JSONObject response = new JSONObject(strResult);
            if (response.getString("result").equals("success")) {
                ((InputMethodManager) FrgCoupon.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(1, 0);
                FrgCoupon.this.code.setText("");
                Toast.makeText(FrgCoupon.this.getActivity(), response.getString("msg"), Toast.LENGTH_LONG).show();
                FrgCoupon.this.getCouponList();
                return;
            }
            Toast toast = Toast.makeText(FrgCoupon.this.getActivity(), response.getString("msg"), Toast.LENGTH_LONG);
            toast.setGravity(17, 0, 0);
            toast.show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class GetCouponListHandler implements Runnable {
        GetCouponListHandler() {
        }

        public void run() {
            if (FrgCoupon.this.cookie != null) {
                FrgCoupon.this.getCouponList();
            }
        }
    }

    private void getCouponList() {
        this.progressbar.setVisibility(View.VISIBLE);
        QGHttpRequest.getInstance().getCouponList(this.getActivity(), this.cookie, new GetCouponList(this.getActivity()));
    }

    class GetCouponList extends QGHttpHandler<String>
    {

        public GetCouponList(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {
            FrgCoupon.this.ShowCouponList(s);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Toast.makeText(FrgCoupon.this.getActivity(), FrgCoupon.this.getString(R.string.error_coupon_money), Toast.LENGTH_LONG).show();
        }
    }

    private void ShowCouponList(String strResult)
    {
        try {

            JSONObject response = new JSONObject(strResult);

            FrgCoupon.this.cpnEntries = new ArrayList();
//            if (response.getString("result").equals("success"))
            {
                JSONArray feed = response.getJSONArray("data");
                for (int i = 0; i < feed.length(); i++) {
                    JSONObject entry = feed.getJSONObject(i);
                    FrgCoupon.this.cpnEntries.add(new CouponEntry(entry.getString("start_date"), entry.getString("end_date"), entry.getString("expiration_yn"), entry.getString("id"), entry.getString("promotion_code"), entry.getString("promotion_name"), entry.getString("useyn"), entry.getInt("promotion_price")));
                }
                FrgCoupon.this.mAdapter = new CouponListAdapter(FrgCoupon.this.getActivity(), 0, FrgCoupon.this.cpnEntries);
                FrgCoupon.this.cpnListview.setAdapter(FrgCoupon.this.mAdapter);
                FrgCoupon.this.mAdapter.notifyDataSetChanged();
            }
//            else {
//                Toast.makeText(FrgCoupon.this.getActivity(), FrgCoupon.this.getString(R.string.error_coupon_money), Toast.LENGTH_LONG).show();
//            }
            FrgCoupon.this.progressbar.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(FrgCoupon.this.getActivity(), FrgCoupon.this.getString(R.string.error_booking_info), Toast.LENGTH_LONG).show();
        }
    }
}
