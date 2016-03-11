package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;

public class Register2Activity extends BaseActivity implements View.OnClickListener {

    private EditText etUserID;
    private EditText etEmail;
    private EditText etPass;
    private EditText etConfPass;
    private Button btnNext;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initView();
    }

    private void initView()
    {
        this.etUserID = (EditText)findViewById(R.id.userid);
        this.etEmail = (EditText)findViewById(R.id.email);
        this.etPass = (EditText)findViewById(R.id.passwd);
        this.etConfPass = (EditText)findViewById(R.id.confpasswd);

        this.btnNext = (Button)findViewById(R.id.btn_signin);

        this.imgBack = (ImageView)findViewById(R.id.menu_search);

        this.imgBack.setOnClickListener(this);
        this.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.menu_search:
                this.finish();
                this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.btn_signin:
                if(evaluateAvail()) {
                    Intent intentnext = new Intent(this, Register3Activity.class);
                    intentnext.putExtra("id", this.etUserID.getText().toString());
                    intentnext.putExtra("email", this.etEmail.getText().toString());
                    intentnext.putExtra("password", this.etPass.getText().toString());
                    startActivity(intentnext);

                    this.finish();
                }
                break;
        }
    }

    private boolean evaluateAvail(){

        String strid = etUserID.getText().toString();
        String stremail = etEmail.getText().toString();
        String strpass = etPass.getText().toString();
        String strverypass = etConfPass.getText().toString();

        if (strid.isEmpty()){
            Toast.makeText(this, "아이디를 입력하십시오.", Toast.LENGTH_SHORT).show();
            etUserID.requestFocus();
            return false;
        }
        if (stremail.isEmpty()){
            Toast.makeText(this, "이메일을 입력하십시오.", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(stremail).matches()){
            Toast.makeText(this, "이메일주소가 정확치 않습니다.", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        }

        if (strpass.isEmpty()){
            Toast.makeText(this, "비밀번호를 입력하십시오.", Toast.LENGTH_SHORT).show();
            etPass.requestFocus();
            return false;
        }

        if (strpass.length() <= 4 ){
            Toast.makeText(this, "비밀번호는 4자이상입니다.", Toast.LENGTH_SHORT).show();
            etPass.requestFocus();
            return false;
        }

        if (!strpass.equals(strverypass)){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            etConfPass.requestFocus();
            return false;
        }

        if (strverypass.isEmpty()){
            Toast.makeText(this, "비밀번호 확인을 입력하십시오.", Toast.LENGTH_SHORT).show();
            etConfPass.requestFocus();
            return false;
        }

        return true;
    }
}
