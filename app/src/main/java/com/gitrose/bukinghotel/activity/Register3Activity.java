package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.FrgSignUp;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;

public class Register3Activity extends BaseActivity implements View.OnClickListener {

    private TextView et_signup_name;
    private TextView et_signup_phone;
    private TextView et_signup_recommender;

    private String m_strId;
    private String m_strEmailAddress;
    private String m_strPassword;

    private ImageView imgBack;
    private Button btnSignUp;

    private SharedPreferences _preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        init();
    }

    private void init() {

        this._preferences = PreferenceManager.getDefaultSharedPreferences(this);

        this.m_strId = getIntent().getStringExtra("id");
        this.m_strEmailAddress = getIntent().getStringExtra("email");
        this.m_strPassword = getIntent().getStringExtra("password");

        this.imgBack = (ImageView)findViewById(R.id.menu_search);
        this.btnSignUp = (Button)findViewById(R.id.btn_signin);

        et_signup_name = (TextView)findViewById(R.id.user_name);
        et_signup_phone = (TextView)findViewById(R.id.phone_num_1);
        et_signup_recommender = (TextView)findViewById(R.id.et_signup_recommender);

        this.imgBack.setOnClickListener(this);
        this.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_signin:
                if(evaluateAvail()) {
                    QGHttpRequest.getInstance().RegistorRequest(this, this.m_strId, this.m_strEmailAddress, this.m_strPassword, this.et_signup_name.getText().toString(), this.et_signup_phone.getText().toString(), this.et_signup_recommender.getText().toString(), new RegistorResult(this));
                }
                break;

            case R.id.menu_search:
                this.finish();
                this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

    public class RegistorResult extends QGHttpHandler<String> {

        public RegistorResult(Context context) {
            super(context);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            String strMessage = "같은 아이디나 이메일이 존재 합니다.";
            ShowToastMessage(strMessage);
        }

        @Override
        public void onGetDataSuccess(String userInfo) {
            StartMainActivity();
        }
    }
    private void ShowToastMessage(String strMessage) {
        Toast.makeText(this, strMessage, Toast.LENGTH_LONG).show();
    }

    private void StartMainActivity()
    {
//        Intent firstIntent = new Intent(this, SignUpEndActivity.class);
//        firstIntent.putExtra("name", this.et_signup_name.getText().toString());
//        startActivity(firstIntent);

        String username = this.et_signup_name.getText().toString();
        String phone = this.et_signup_phone.getText().toString();
        String userid = this.m_strId;
        SharedPreferences.Editor prefEditor = this._preferences.edit();
        prefEditor.putString("email", this.m_strEmailAddress.trim());
        prefEditor.putString("username", username);
        prefEditor.putString("phone", phone);
        prefEditor.putString("userid", userid);
        prefEditor.commit();

        ShowToastMessage("가입이 완료되였습니다.");

        this.finish();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private boolean evaluateAvail(){

        String strname = et_signup_name.getText().toString();
        String strphone = et_signup_phone.getText().toString();
        String strrecommender = et_signup_recommender.getText().toString();

        if (strname.isEmpty()){
            Toast.makeText(this, "이름을 입력하십시오.", Toast.LENGTH_SHORT).show();
            et_signup_name.requestFocus();
            return false;
        }

        if (strphone.isEmpty()){
            Toast.makeText(this, "휴대폰번호를 입력하십시오.", Toast.LENGTH_SHORT).show();
            et_signup_phone.requestFocus();
            return false;
        }

        if (strrecommender.isEmpty()){
            Toast.makeText(this, "코드를 입력하십시오.", Toast.LENGTH_SHORT).show();
            et_signup_recommender.requestFocus();
            return false;
        }

        return true;
    }
}
