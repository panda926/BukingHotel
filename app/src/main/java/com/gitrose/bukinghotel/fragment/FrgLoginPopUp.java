package com.gitrose.bukinghotel.fragment;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActReservation;
import com.gitrose.bukinghotel.activity.ActSignUp;
import com.gitrose.bukinghotel.activity.ActWebView;
import com.gitrose.bukinghotel.activity.GetPassActivity;
import com.gitrose.bukinghotel.activity.Register1Activity;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GITRose on 1/22/2016.
 */
public class FrgLoginPopUp extends BaseFragment {

    private SharedPreferences _preferences;
    private ProgressDialog dialog;
    private EditText email;
    private Typeface font;
    private String from;
    private EditText passwd;
    private String pid;

    public FrgLoginPopUp() {
        this.from = "";
        this.pid = "";
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        
        this.email = (EditText) getView().findViewById(R.id.email);
        this.passwd = (EditText) getView().findViewById(R.id.passwd);
        
        Intent intent = this.getActivity().getIntent();
        this.from = intent.getStringExtra("from") != null ? intent.getStringExtra("from") : "";
        this.pid = intent.getStringExtra("pid") != null ? intent.getStringExtra("pid") : "";
        ((TextView) getView().findViewById(R.id.btn_join)).setOnClickListener(new JoinClick());
        ((TextView) getView().findViewById(R.id.btn_resetpass)).setOnClickListener(new resetpassClick());
        ((Button) getView().findViewById(R.id.btn_signin)).setOnClickListener(new signinClick());
    }

    class signinClick implements View.OnClickListener {
        signinClick() {
        }

        public void onClick(View v) {
//            if (FrgLoginPopUp.this.email.getText().toString().trim().length() <= 0) {
//                Toast.makeText(FrgLoginPopUp.this.getActivity().getApplicationContext(), FrgLoginPopUp.this.getString(R.string.validator_email), Toast.LENGTH_LONG).show();
//                FrgLoginPopUp.this.email.requestFocus();
//            } else if (!FrgLoginPopUp.this.email.getText().toString().contains("@")) {
//                Toast.makeText(FrgLoginPopUp.this.getActivity().getApplicationContext(), FrgLoginPopUp.this.getString(R.string.validator_email_invalid), Toast.LENGTH_LONG).show();
//                FrgLoginPopUp.this.email.requestFocus();
//            }
            if (FrgLoginPopUp.this.passwd.getText().toString().trim().length() <= 0) {
                Toast.makeText(FrgLoginPopUp.this.getActivity().getApplicationContext(), FrgLoginPopUp.this.getString(R.string.validator_password), Toast.LENGTH_LONG).show();
                FrgLoginPopUp.this.passwd.requestFocus();
            } else {
                FrgLoginPopUp.this.dialog = new ProgressDialog(FrgLoginPopUp.this.getActivity());
                FrgLoginPopUp.this.dialog.setMessage(FrgLoginPopUp.this.getString(R.string.login_loading));
                FrgLoginPopUp.this.dialog.setIndeterminate(true);
                FrgLoginPopUp.this.dialog.setCancelable(true);
                FrgLoginPopUp.this.dialog.show();

                QGHttpRequest.getInstance().UserLogin(FrgLoginPopUp.this.getActivity(), FrgLoginPopUp.this.email.getText().toString(),
                        FrgLoginPopUp.this.passwd.getText().toString(), HotelUtil.getAppVersionName(FrgLoginPopUp.this.getActivity()),
                        HotelUtil.getUserAgent(FrgLoginPopUp.this.getActivity()), HotelUtil.getAndroidId(FrgLoginPopUp.this.getActivity()),
                        new GetLoginResult(FrgLoginPopUp.this.getActivity()));
            }
        }
    }

    class GetLoginResult extends QGHttpHandler<String>
    {

        public GetLoginResult(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {
            FrgLoginPopUp.this.SetLoginResult(s);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Toast.makeText(FrgLoginPopUp.this.getActivity().getApplicationContext(), FrgLoginPopUp.this.getString(R.string.error_connect_problem), Toast.LENGTH_LONG).show();
        }
    }

    private void SetLoginResult(String strResult)
    {
        try {

            FrgLoginPopUp.this.dialog.dismiss();
            JSONObject info = new JSONObject(strResult);
//            if (response.getString("result").equals("success"))
            {

//                JSONObject info = response.getJSONObject("info");
                String username = info.getString("name");
                String phone = info.getString("phone");
                String userid = info.getString("id");
                String moreinfo = info.has("more_info") ? info.getString("more_info") : "";
                FrgLoginPopUp.this._preferences = PreferenceManager.getDefaultSharedPreferences(FrgLoginPopUp.this.getActivity());
                SharedPreferences.Editor prefEditor = FrgLoginPopUp.this._preferences.edit();
                prefEditor.putString("email", FrgLoginPopUp.this.email.getText().toString().trim());
                prefEditor.putString("username", username);
                prefEditor.putString("phone", phone);
                prefEditor.putString("userid", userid);
                prefEditor.putString("moreinfo", moreinfo);
                prefEditor.commit();

//                ActMain.showUserInfo(info.getInt("reserve_money"));
                Toast.makeText(FrgLoginPopUp.this.getActivity().getApplicationContext(), FrgLoginPopUp.this.getString(R.string.login_complete), Toast.LENGTH_LONG).show();
                FrgLoginPopUp.this.getActivity().finish();
                FrgLoginPopUp.this.getActivity().overridePendingTransition(R.anim.slide_out_up, R.anim.slide_out_down);
                Intent intent;
                if (FrgLoginPopUp.this.from.equals("reservation")) {
                    intent = new Intent(FrgLoginPopUp.this.getActivity(), ActReservation.class);
                    intent.putExtra("pid", FrgLoginPopUp.this.pid);
                    FrgLoginPopUp.this.startActivity(intent);
                    FrgLoginPopUp.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    return;
                } else if (FrgLoginPopUp.this.from.equals("event")) {
//                    intent = new Intent(FrgLoginPopUp.this, ActFreePromotion.class);
//                    intent.putExtra("pid", FrgLoginPopUp.this.pid);
//                    FrgLoginPopUp.this.startActivity(intent);
//                    FrgLoginPopUp.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
//                    return;
                } else {
//                            Intent intentLoading = new Intent(FrgLoginPopUp.this.getActivity().getBaseContext(), ActMain.class);
//                            intentLoading.addFlags(PendingIntent. FLAG_IMMUTABLE);
//                            intentLoading.addFlags(PendingIntent.FLAG_NO_CREATE);
//                            intentLoading.addFlags(AccessibilityNodeInfoCompat.ACTION_PASTE);
//                            intentLoading.addFlags(PendingIntent.FLAG_CANCEL_CURRENT );
//                            FrgLoginPopUp.this.startActivity(intentLoading);
                    return;
                }
            }

            Toast.makeText(FrgLoginPopUp.this.getActivity().getApplicationContext(), FrgLoginPopUp.this.getString(R.string.validator_user_not_match), Toast.LENGTH_LONG).show();
            
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(FrgLoginPopUp.this.getActivity().getApplicationContext(), FrgLoginPopUp.this.getString(R.string.error_connect_problem), Toast.LENGTH_LONG).show();
        }
    }

    class resetpassClick implements View.OnClickListener {
        resetpassClick() {
        }

        public void onClick(View v) {
//            FrgLoginPopUp.this.getActivity().finish();
//            Intent intent = new Intent(FrgLoginPopUp.this.getActivity(), ActWebView.class);
//            intent.putExtra("url", CONFIG.passresetUrl);
//            intent.putExtra("title", FrgLoginPopUp.this.getString(R.string.reset_password));
//            FrgLoginPopUp.this.startActivity(intent);
//            FrgLoginPopUp.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_down);

            Intent intent = new Intent(FrgLoginPopUp.this.getActivity(), GetPassActivity.class);
            FrgLoginPopUp.this.startActivity(intent);
            FrgLoginPopUp.this.getActivity().finish();
        }
    }

    class JoinClick implements View.OnClickListener {
        JoinClick() {
        }

        public void onClick(View v) {
//            Intent intent = new Intent(FrgLoginPopUp.this.getActivity(), ActSignUp.class);
//            intent.putExtra("from", FrgLoginPopUp.this.from);
//            intent.putExtra("pid", FrgLoginPopUp.this.pid);
//            FrgLoginPopUp.this.startActivity(intent);
//            FrgLoginPopUp.this.getActivity().finish();
//            FrgLoginPopUp.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_down);

            Intent intent = new Intent(FrgLoginPopUp.this.getActivity(), Register1Activity.class);
            FrgLoginPopUp.this.startActivity(intent);
            FrgLoginPopUp.this.getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loginpopup, container, false);
    }
}
