package com.gitrose.bukinghotel.fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActSignUp;
import com.gitrose.bukinghotel.activity.ActWebView;
import com.gitrose.bukinghotel.activity.GetPassActivity;
import com.gitrose.bukinghotel.activity.LoginActivity;
import com.gitrose.bukinghotel.activity.Register1Activity;
import com.gitrose.bukinghotel.adapter.CouponEntry;
import com.gitrose.bukinghotel.adapter.CouponListAdapter;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GITRose on 1/20/2016.
 */
public class FrgSetting extends BaseFragment {

    private SharedPreferences _preferences;
    private CheckBox accept_location;
    private CheckBox accept_push;
    private LinearLayout after_login;
    private LinearLayout after_login1;
    private LinearLayout before_login;
    private String cookie;
    private ProgressDialog dialog;
    private boolean fromSetting;
    private Boolean locationStatus;
    private TextView txtUserName;
    private TextView txtUserEmail;
    private TextView txtReserveMoney;

    public FrgSetting() {
        this.locationStatus = Boolean.valueOf(false);
        this.fromSetting = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.before_login = (LinearLayout) getView().findViewById(R.id.before_login);
        this.after_login = (LinearLayout) getView().findViewById(R.id.after_login);
        this.after_login1 = (LinearLayout) getView().findViewById(R.id.after_login1);
        this._preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.accept_location = (CheckBox) getView().findViewById(R.id.accept_location);
        this.accept_push = (CheckBox) getView().findViewById(R.id.accept_push);
        this.locationStatus = Boolean.valueOf(CONFIG.useLocation);
        this.txtUserEmail = (TextView)getView().findViewById(R.id.user_email);
        this.txtUserName = (TextView)getView().findViewById(R.id.user_name);
        this.txtReserveMoney = (TextView)getView().findViewById(R.id.user_reserve_money);
        ((TextView) getView().findViewById(R.id.btn_signin)).setOnClickListener(new SignupClick());
        ((TextView) getView().findViewById(R.id.activity_login_tv_forget_password)).setOnClickListener(new GetPassClick());
//        ((TableRow) getView().findViewById(R.id.btn_location)).setOnClickListener(new C06562());
//        ((TableRow) getView().findViewById(R.id.btn_notification)).setOnClickListener(new C06573());
//        ((Button) getView().findViewById(R.id.btn_logout)).setOnClickListener(new C06604());
        ((Button) getView().findViewById(R.id.btn_login)).setOnClickListener(new LogInClick());
        ((ImageView) getView().findViewById(R.id.btn_notice)).setOnClickListener(new noticeClick());
        ((ImageView) getView().findViewById(R.id.btn_send_mail)).setOnClickListener(new sendMailClick());
        ((ImageView) getView().findViewById(R.id.btn_agreement1)).setOnClickListener(new agreement1Click());
        ((ImageView) getView().findViewById(R.id.btn_agreement2)).setOnClickListener(new agreement2Click());
        ((ImageView) getView().findViewById(R.id.btn_agreement3)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FrgSetting.this.getActivity(), ActWebView.class);
                intent.putExtra("url", CONFIG.setting_agree3);
                intent.putExtra("title", FrgSetting.this.getString(R.string.location_agreement));
                FrgSetting.this.startActivity(intent);
                FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

        ((ImageView) getView().findViewById(R.id.faq)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FrgSetting.this.getActivity(), ActWebView.class);
                intent.putExtra("url", CONFIG.strFAQ);
                intent.putExtra("title", FrgSetting.this.getString(R.string.location_agreement));
                FrgSetting.this.startActivity(intent);
                FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

        ((Button) getView().findViewById(R.id.btn_call)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FrgSetting.this.startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:070-4481-5541")));
                FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        ((TextView) getView().findViewById(R.id.txt_version)).setText(HotelUtil.getAppVersionName(getActivity()));
    }

    class SignupClick implements View.OnClickListener {
        SignupClick() {
        }

        public void onClick(View v) {
//            FrgSetting.this.startActivity(new Intent(FrgSetting.this.getActivity(), ActSignUp.class));
//            FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

            FrgSetting.this.startActivity(new Intent(FrgSetting.this.getActivity(), Register1Activity.class));
            FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class GetPassClick implements View.OnClickListener {
        GetPassClick() {
        }

        public void onClick(View v) {
//            FrgSetting.this.startActivity(new Intent(FrgSetting.this.getActivity(), ActSignUp.class));
//            FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

            FrgSetting.this.startActivity(new Intent(FrgSetting.this.getActivity(), GetPassActivity.class));
            FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class LogInClick implements View.OnClickListener {
        LogInClick() {
        }

        public void onClick(View v) {
            FrgSetting.this.startActivity(new Intent(FrgSetting.this.getActivity(), LoginActivity.class));
            FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class agreement2Click implements View.OnClickListener {
        agreement2Click() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(FrgSetting.this.getActivity(), ActWebView.class);
            intent.putExtra("url", CONFIG.setting_agree2);
            intent.putExtra("title", FrgSetting.this.getString(R.string.userinfo_agreement));
            FrgSetting.this.startActivity(intent);
            FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class agreement1Click implements View.OnClickListener {
        agreement1Click() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(FrgSetting.this.getActivity(), ActWebView.class);
            intent.putExtra("url", CONFIG.setting_agree1);
            intent.putExtra("title", FrgSetting.this.getString(R.string.service_agreement));
            FrgSetting.this.startActivity(intent);
            FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class sendMailClick implements View.OnClickListener {
        sendMailClick() {
        }

        public void onClick(View v) {
            String userId = FrgSetting.this._preferences.getString("userid", null);
            Intent i = new Intent("android.intent.action.SEND");
            i.setType("message/rfc822");
            i.putExtra("android.intent.extra.EMAIL", new String[]{CONFIG.setting_email});
            i.putExtra("android.intent.extra.TEXT", "\n\n\n\n\n---------------------------------------\nver:" + HotelUtil.getAppVersionName(FrgSetting.this.getActivity()) + ";" + HotelUtil.getUserAgent(FrgSetting.this.getActivity()) + ";" + userId);
            try {
                FrgSetting.this.startActivity(Intent.createChooser(i, FrgSetting.this.getString(R.string.send_email)));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(FrgSetting.this.getActivity(), FrgSetting.this.getString(R.string.no_email_program), Toast.LENGTH_LONG).show();
            }
        }
    }

    class noticeClick implements View.OnClickListener {
        noticeClick() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(FrgSetting.this.getActivity(), ActWebView.class);
            intent.putExtra("url", CONFIG.setting_notice);
            intent.putExtra("title", FrgSetting.this.getString(R.string.notice));
            FrgSetting.this.startActivity(intent);
            FrgSetting.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor prefEditor = this._preferences.edit();
        prefEditor.putBoolean("flag_use_location", this.accept_location.isChecked());
        prefEditor.commit();
        if (this.accept_location.isChecked()) {
            CONFIG.useLocation = true;
        } else {
            CONFIG.useLocation = false;
        }
        if (this.locationStatus.booleanValue() != CONFIG.useLocation) {
            CONFIG.reloadFlag = true;
        }
        String regId = this._preferences.getString("gcm_registration_id", null);
        String userId = this._preferences.getString("userid", null);
        if (regId != null) {
            HotelUtil.setGcmToken(getActivity(), regId, userId, Boolean.valueOf(this.accept_push.isChecked()));
        }
    }

    public void onResume() {
        super.onResume();
        if (this.fromSetting) {
            this.fromSetting = false;
            SharedPreferences.Editor prefEditor;
            if (HotelUtil.chkLocationService(getActivity())) {
//                ((ActMain) getActivity()).loadLocationClient();
                prefEditor = this._preferences.edit();
                prefEditor.putBoolean("flag_use_location", true);
                prefEditor.commit();
                this.accept_location.setChecked(true);
            } else
            {
                prefEditor = this._preferences.edit();
                prefEditor.putBoolean("flag_use_location", false);
                prefEditor.commit();
                this.accept_location.setChecked(false);
            }
        }
        showUserStatus();
    }

    private void ShowUserInfo()
    {
        this.txtUserName.setText(this._preferences.getString("username", ""));
        this.txtUserEmail.setText(this._preferences.getString("email", ""));
        this.txtReserveMoney.setText(this._preferences.getInt("resevemoney", 0) + "원");
    }

    private void showUserStatus() {
        this.cookie = this._preferences.getString("userid", null);
        if (this.cookie != null) {
            this.after_login.setVisibility(View.VISIBLE);
            this.after_login1.setVisibility(View.VISIBLE);
            this.before_login.setVisibility(View.GONE);
            ShowUserInfo();
        } else {
            this.after_login.setVisibility(View.GONE);
            this.after_login1.setVisibility(View.GONE);
            this.before_login.setVisibility(View.VISIBLE);
        }
        this.accept_location.setChecked(this._preferences.getBoolean("flag_use_location", true));
        String androidId = HotelUtil.getAndroidId(getActivity());
        QGHttpRequest.getInstance().GetUserStatus(this.getActivity(), androidId, new GetUserStatusResult(this.getActivity()));

//        String url = CONFIG.notiStatusUrl;
//        JSONObject params = new JSONObject();
//        try {
//            params.put(StaticValues.PARAM_UUID, androidId);
//        } catch (JSONException e) {
//        }
//        VolleyUtil.getRequestQueue().add(new JsonObjectRequest(1, url, params, new Response.Listener<JSONObject>() {
//            public void onResponse(JSONObject response) {
//                Log.e("response", response.toString());
//                try {
//                    if (response.getString(StaticValues.PARAM_STATUS).toUpperCase().equals(WiseTracker.MEMBER)) {
//                        FrgSetting.this.accept_push.setChecked(true);
//                    } else {
//                        FrgSetting.this.accept_push.setChecked(false);
//                    }
//                } catch (JSONException e) {
//                    FrgSetting.this.accept_push.setChecked(false);
//                }
//            }
//        }, new Response.ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//                FrgSetting.this.accept_push.setChecked(false);
//            }
//        }));
    }

    class GetUserStatusResult extends QGHttpHandler<String>
    {

        public GetUserStatusResult(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {
            FrgSetting.this.ShowUserStatus(s);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            FrgSetting.this.accept_push.setChecked(false);
        }
    }

    private void ShowUserStatus(String strResult)
    {
        try {

            JSONObject response = new JSONObject(strResult);

            if (response.getString("status").toUpperCase().equals("Y")) {
                FrgSetting.this.accept_push.setChecked(true);
            } else {
                FrgSetting.this.accept_push.setChecked(false);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            FrgSetting.this.accept_push.setChecked(false);
        }
    }

    private void setLogOut() {
        this.dialog = new ProgressDialog(getActivity());
        this.dialog.setMessage(getString(R.string.logout_loading));
        this.dialog.setIndeterminate(true);
        this.dialog.setCancelable(true);
        this.dialog.show();
//        VolleyUtil.getRequestQueue().add(new JsonObjectRequest(0, CONFIG.logoutUrl, null, new Response.Listener<JSONObject>() {
//            public void onResponse(JSONObject response) {
//                FrgSetting.this.dialog.dismiss();
//                try {
//                    if (response.getString("result").equals("success")) {
//                        String moreinfo = response.has("_umi") ? response.getString("_umi") : BuildConfig.FLAVOR;
//                        SharedPreferences.Editor prefEditor = FrgSetting.this._preferences.edit();
//                        prefEditor.putString(Properties.EMAIL, null);
//                        prefEditor.putString(Properties.USER_NAME, null);
//                        prefEditor.putString(StaticValues.PARAM_PHONE, null);
//                        prefEditor.putString("userid", null);
//                        prefEditor.putString("moreinfo", moreinfo);
//                        prefEditor.commit();
//                        ((ActMain) FrgSetting.this.getActivity()).replaceFragment("FrgSetting");
//                        ActMain.hideUserInfo();
//                        return;
//                    }
//                    Toast.makeText(FrgSetting.this.getActivity(), FrgSetting.this.getString(R.string.error_connect_problem), 0).show();
//                } catch (JSONException e) {
//                    Toast.makeText(FrgSetting.this.getActivity(), FrgSetting.this.getString(R.string.error_connect_problem), 0).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//                if (error.networkResponse != null) {
//                    Toast.makeText(FrgSetting.this.getActivity(), FrgSetting.this.getString(R.string.error_connect_problem), 0).show();
//                }
//            }
//        }));
    }
}
