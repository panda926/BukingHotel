package com.gitrose.bukinghotel.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActWebView;
import com.gitrose.bukinghotel.activity.LoginActivity;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GITRose on 1/22/2016.
 */
public class FrgSignUp extends BaseFragment {

    SharedPreferences _preferences;
    CheckBox agree_checkbox1;
    CheckBox agree_checkbox2;
    CheckBox agree_checkbox3;
    ArrayList<String> arraylist;
    ImageView captcha;
    EditText captcha_string;
    EditText codeInput;
    TextView codeResult;
    private View.OnClickListener confirmClickListener;
    EditText email;
    private Typeface font;
    private String from;
    private View.OnClickListener okClickListener;
    EditText passwd;
    String phone1;
    Spinner phone_first;
    EditText phone_num_2;
    EditText phone_num_3;
    String phone_number;
    String[] phone_prefixs;
    private String pid;
    int reserve_money;
    private View.OnClickListener skipClickListener;
    EditText username;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        this.from = intent.getStringExtra("from");
        this.pid = intent.getStringExtra("pid");
        this.font = Typeface.createFromAsset(this.getActivity().getAssets(), CONFIG.fontPath);
        this._preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        this.phone_prefixs = getResources().getStringArray(R.array.phone_prefix);
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, this.phone_prefixs);
        this.phone_first = (Spinner) getView().findViewById(R.id.phone_num_1);
        this.phone_first.setAdapter(adapter);
        this.captcha = (ImageView) getView().findViewById(R.id.captcha);
        HotelUtil.captchaImgUrl(getActivity(), this.captcha);
        this.captcha_string = (EditText) getView().findViewById(R.id.captcha_string);
        this.email = (EditText) getView().findViewById(R.id.email);
        this.passwd = (EditText) getView().findViewById(R.id.passwd);
        this.username = (EditText) getView().findViewById(R.id.username);
        this.phone_num_2 = (EditText) getView().findViewById(R.id.phone_num_2);
        this.phone_num_3 = (EditText) getView().findViewById(R.id.phone_num_3);
        this.agree_checkbox1 = (CheckBox) getView().findViewById(R.id.agree_checkbox1);
        this.agree_checkbox2 = (CheckBox) getView().findViewById(R.id.agree_checkbox2);
        this.agree_checkbox3 = (CheckBox) getView().findViewById(R.id.agree_checkbox3);
//        this.captcha_string.setTypeface(this.font);
//        this.email.setTypeface(this.font);
//        this.passwd.setTypeface(this.font);
//        this.username.setTypeface(this.font);
//        this.phone_num_2.setTypeface(this.font);
//        this.phone_num_3.setTypeface(this.font);
        ((TextView) getView().findViewById(R.id.btn_login)).setOnClickListener(new LoginClick());
        ((TextView) getView().findViewById(R.id.show_agreement1)).setOnClickListener(new agreement1Click());
        ((TextView) getView().findViewById(R.id.show_agreement2)).setOnClickListener(new agreement2Click());
        ((TextView) getView().findViewById(R.id.show_agreement3)).setOnClickListener(new agreement3Click());
        ((Button) getView().findViewById(R.id.btn_signin)).setOnClickListener(new SigninClick());
    }

    class SigninClick implements View.OnClickListener {

        /* renamed from: com.hotelnow.activities.FrgSignUp.8.1 */
        class C11771 implements Response.Listener<JSONObject> {
            C11771() {
            }

            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("result").equals("success")) {
                        JSONObject info = response.getJSONObject("user_info");
                        String username = new String(Base64.decode(info.getString("name"), 0));
                        String phone = new String(Base64.decode(info.getString("phone"), 0));
                        String userid = info.getString("id");
                        FrgSignUp.this.reserve_money = info.getInt("reserve_money");
                        SharedPreferences.Editor prefEditor = FrgSignUp.this._preferences.edit();
                        prefEditor.putString("email", FrgSignUp.this.email.getText().toString().trim());
                        prefEditor.putString("username", username);
                        prefEditor.putString("phone", phone);
                        prefEditor.putString("userid", userid);
                        prefEditor.commit();
                        if (response.getString("device_exist").equals("Y")) {
                            Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.join_finish), Toast.LENGTH_LONG).show();
                            FrgSignUp.this.finishSignup();
                        } else {
//                            DialogRecommend dialog = new DialogRecommend(FrgSignUp.this, FrgSignUp.this.skipClickListener, FrgSignUp.this.okClickListener);
//                            dialog.setCancelable(false);
//                            dialog.show();
//                            FrgSignUp.this.codeInput = (EditText) dialog.findViewById(R.id.recommend_code);
//                            FrgSignUp.this.codeResult = (TextView) dialog.findViewById(R.id.recommend_result);
//                            FrgSignUp.this.codeInput.setTypeface(FrgSignUp.this.font);
                        }

                        return;
                    }
                    Toast.makeText(FrgSignUp.this.getActivity(), response.getString("msg"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.error_member_join), Toast.LENGTH_LONG).show();
                }
            }
        }

        /* renamed from: com.hotelnow.activities.FrgSignUp.8.2 */
        class C11782 implements Response.ErrorListener {
            C11782() {
            }

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.error_member_join), Toast.LENGTH_LONG).show();
            }
        }

        SigninClick() {
        }

        public void onClick(View v) {
            if (FrgSignUp.this.email.getText().toString().trim().length() <= 0) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_email), Toast.LENGTH_LONG).show();
                FrgSignUp.this.email.requestFocus();
            } else if (!FrgSignUp.this.email.getText().toString().contains("@")) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_email_invalid), Toast.LENGTH_LONG).show();
                FrgSignUp.this.email.requestFocus();
            } else if (FrgSignUp.this.passwd.getText().toString().trim().length() < 4) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_password_length), Toast.LENGTH_LONG).show();
                FrgSignUp.this.passwd.requestFocus();
            } else if (FrgSignUp.this.username.getText().toString().trim().length() < 2) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_name_length), Toast.LENGTH_LONG).show();
                FrgSignUp.this.username.requestFocus();
            } else if (FrgSignUp.this.phone_num_2.getText().toString().trim().length() < 3) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_pnum), Toast.LENGTH_LONG).show();
                FrgSignUp.this.phone_num_2.requestFocus();
            } else if (FrgSignUp.this.phone_num_3.getText().toString().trim().length() < 4) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_pnum), Toast.LENGTH_LONG).show();
                FrgSignUp.this.phone_num_3.requestFocus();
            } else if (FrgSignUp.this.captcha_string.getText().toString().trim().length() <= 0) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_capcha), Toast.LENGTH_LONG).show();
                FrgSignUp.this.captcha_string.requestFocus();
            } else if (!FrgSignUp.this.agree_checkbox1.isChecked()) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_service_agreement), Toast.LENGTH_LONG).show();
            } else if (!FrgSignUp.this.agree_checkbox2.isChecked()) {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_userinfo_agreement), Toast.LENGTH_LONG).show();
            } else if (FrgSignUp.this.agree_checkbox3.isChecked()) {
                String phone_num_1;
                if (FrgSignUp.this.phone_first.getSelectedItem() != null) {
                    phone_num_1 = (String) FrgSignUp.this.phone_first.getSelectedItem();
                } else {
                    phone_num_1 = FrgSignUp.this.phone_prefixs[0];
                }
                phone_num_1 = phone_num_1;
                FrgSignUp.this.phone_number = new StringBuilder(String.valueOf(phone_num_1)).append("-").append(FrgSignUp.this.phone_num_2.getText().toString()).append("-").append(FrgSignUp.this.phone_num_3.getText().toString()).toString();
                JSONObject params = new JSONObject();
                try {
                    params.put("email", FrgSignUp.this.email.getText().toString());
                    params.put("password", FrgSignUp.this.passwd.getText().toString());
                    params.put("name", FrgSignUp.this.username.getText().toString());
                    params.put("captcha", FrgSignUp.this.captcha_string.getText().toString());
                    params.put("phone", FrgSignUp.this.phone_number);
                    params.put("ver", HotelUtil.getAppVersionName(FrgSignUp.this.getActivity()));
                    params.put("useragent", HotelUtil.getUserAgent(FrgSignUp.this.getActivity()));
                    params.put("uuid", HotelUtil.getAndroidId(FrgSignUp.this.getActivity()));
                } catch (JSONException e) {
                }
//                VolleyUtil.getRequestQueue().add(new JsonObjectRequest(1, CONFIG.signupUrl, params, new C11771(), new C11782()));
            } else {
                Toast.makeText(FrgSignUp.this.getActivity(), FrgSignUp.this.getString(R.string.validator_location_agreement), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void finishSignup() {
//        ActMain.showUserInfo(this.reserve_money);
        this.getActivity().finish();
        this.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        Intent intent;
        if (this.from != null && this.from.equals("reservation")) {
//            intent = new Intent(this, ActReservation.class);
//            intent.putExtra("pid", this.pid);
//            startActivity(intent);
//            this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        } else if (this.from != null && this.from.equals("event")) {
//            intent = new Intent(this, ActFreePromotion.class);
//            intent.putExtra("pid", this.pid);
//            intent.putExtra("from", "signup");
//            startActivity(intent);
//            this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class agreement3Click implements View.OnClickListener {
        agreement3Click() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(FrgSignUp.this.getActivity(), ActWebView.class);
            intent.putExtra("url", CONFIG.setting_agree3);
            intent.putExtra("title", FrgSignUp.this.getString(R.string.location_agreement));
            FrgSignUp.this.startActivity(intent);
            FrgSignUp.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class agreement2Click implements View.OnClickListener {
        agreement2Click() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(FrgSignUp.this.getActivity(), ActWebView.class);
            intent.putExtra("url", CONFIG.setting_agree2);
            intent.putExtra("title", FrgSignUp.this.getString(R.string.userinfo_agreement));
            FrgSignUp.this.startActivity(intent);
            FrgSignUp.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class agreement1Click implements View.OnClickListener {
        agreement1Click() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(FrgSignUp.this.getActivity(), ActWebView.class);
            intent.putExtra("url", CONFIG.setting_agree1);
            intent.putExtra("title", FrgSignUp.this.getString(R.string.service_agreement));
            FrgSignUp.this.startActivity(intent);
            FrgSignUp.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    class LoginClick implements View.OnClickListener {
        LoginClick() {
        }

        public void onClick(View v) {
            FrgSignUp.this.startActivity(new Intent(FrgSignUp.this.getActivity(), LoginActivity.class));
            FrgSignUp.this.getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }
}
