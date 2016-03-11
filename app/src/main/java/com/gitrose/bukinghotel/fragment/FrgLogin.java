package com.gitrose.bukinghotel.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
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
import com.gitrose.bukinghotel.activity.ActWebView;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by GITRose on 1/22/2016.
 */
public class FrgLogin extends BaseFragment {

    SharedPreferences _preferences;
    ProgressDialog dialog;
    EditText email;
    private Typeface font;
    EditText passwd;
        
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.font = Typeface.createFromAsset(getActivity().getAssets(), CONFIG.fontPath);
        this.email = (EditText) getView().findViewById(R.id.email);
        this.passwd = (EditText) getView().findViewById(R.id.passwd);
        this.email.setTypeface(this.font);
        this.passwd.setTypeface(this.font);
        ((TextView) getView().findViewById(R.id.btn_join)).setOnClickListener(new JoinClick());
        ((TextView) getView().findViewById(R.id.btn_resetpass)).setOnClickListener(new ResetPassClick());
        ((Button) getView().findViewById(R.id.btn_signin)).setOnClickListener(new SignInClick());
    }

    class SignInClick implements View.OnClickListener {

        SignInClick() {
        }

        public void onClick(View v) {
//            if (FrgLogin.this.email.getText().toString().trim().length() <= 0)
//            {
//                Toast.makeText(FrgLogin.this.getActivity(), FrgLogin.this.getString(R.string.validator_email), Toast.LENGTH_LONG).show();
//                FrgLogin.this.email.requestFocus();
//            }
//            else if (!FrgLogin.this.email.getText().toString().contains("@"))
//            {
//                Toast.makeText(FrgLogin.this.getActivity(), FrgLogin.this.getString(R.string.validator_email_invalid), Toast.LENGTH_LONG).show();
//                FrgLogin.this.email.requestFocus();
//            }
            if (FrgLogin.this.passwd.getText().toString().trim().length() <= 0)
            {
                Toast.makeText(FrgLogin.this.getActivity(), FrgLogin.this.getString(R.string.validator_password), Toast.LENGTH_LONG).show();
                FrgLogin.this.passwd.requestFocus();
            } else {
                FrgLogin.this.dialog = new ProgressDialog(FrgLogin.this.getActivity());
                FrgLogin.this.dialog.setMessage(FrgLogin.this.getString(R.string.login_loading));
                FrgLogin.this.dialog.setIndeterminate(true);
                FrgLogin.this.dialog.setCancelable(true);
                FrgLogin.this.dialog.show();

                QGHttpRequest.getInstance().UserLogin(FrgLogin.this.getActivity(), FrgLogin.this.email.getText().toString(),
                        FrgLogin.this.passwd.getText().toString(), HotelUtil.getAppVersionName(FrgLogin.this.getActivity()),
                        HotelUtil.getUserAgent(FrgLogin.this.getActivity()), HotelUtil.getAndroidId(FrgLogin.this.getActivity()),
                        new GetLoginResult(FrgLogin.this.getActivity()));
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
            FrgLogin.this.SetLoginResult(s);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Toast.makeText(FrgLogin.this.getActivity(), FrgLogin.this.getString(R.string.validator_user_not_match), Toast.LENGTH_LONG).show();
        }
    }

    private void SetLoginResult(String strResult)
    {
        try {

            FrgLogin.this.dialog.dismiss();
            JSONObject info = new JSONObject(strResult);
//            if (response.getString("result").equals("success"))
            {
//                JSONObject info = response.getJSONObject("info");
                String username = info.getString("name");
//                String phone = new String(Base64.decode(info.getString("phone"), 0));
                String phone = info.getString("phone");
                String userid = info.getString("id");
                String moreinfo = info.has("more_info") ? info.getString("more_info") : "";
                FrgLogin.this._preferences = PreferenceManager.getDefaultSharedPreferences(FrgLogin.this.getActivity());
                SharedPreferences.Editor prefEditor = FrgLogin.this._preferences.edit();
                prefEditor.putString("email", FrgLogin.this.email.getText().toString().trim());
                prefEditor.putString("username", username);
                prefEditor.putString("phone", phone);
                prefEditor.putString("userid", userid);
                prefEditor.putString("moreinfo", moreinfo);
                prefEditor.putInt("resevemoney", info.getInt("reserve_money"));
                prefEditor.commit();

//              ActMain.showUserInfo(info.getInt("reserve_money"));
                Toast.makeText(FrgLogin.this.getActivity(), FrgLogin.this.getString(R.string.login_complete), Toast.LENGTH_LONG).show();
                FrgLogin.this.getActivity().finish();
                FrgLogin.this.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return;
            }
//            Toast.makeText(FrgLogin.this.getActivity(), FrgLogin.this.getString(R.string.validator_user_not_match), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(FrgLogin.this.getActivity(), FrgLogin.this.getString(R.string.error_connect_problem), Toast.LENGTH_LONG).show();
        }
    }

    class ResetPassClick implements View.OnClickListener {
        ResetPassClick() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(FrgLogin.this.getActivity(), ActWebView.class);
            intent.putExtra("url", CONFIG.passresetUrl);
            intent.putExtra("title", FrgLogin.this.getString(R.string.reset_password));
            FrgLogin.this.startActivity(intent);
            FrgLogin.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class JoinClick implements View.OnClickListener {
        JoinClick() {
        }

        public void onClick(View v) {
//            FrgLogin.this.startActivity(new Intent(FrgLogin.this, ActSignup.class));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}
