package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;

public class Register1Activity extends BaseActivity implements View.OnClickListener {

    private WebView webView1;
    private WebView webView2;
    private WebView webView3;

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBoxAll;

    private ImageView imgBack;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        initView();
    }

    private void initView()
    {
        this.webView1 = (WebView)findViewById(R.id.web_area);
        this.webView2 = (WebView)findViewById(R.id.web_area1);
        this.webView3 = (WebView)findViewById(R.id.web_area2);

        this.webView1.getSettings();
        this.webView2.getSettings();
        this.webView3.getSettings();
        this.webView1.setBackgroundColor(getResources().getColor(R.color.back_detail));
        this.webView2.setBackgroundColor(getResources().getColor(R.color.back_detail));
        this.webView3.setBackgroundColor(getResources().getColor(R.color.back_detail));
        this.webView1.loadUrl("http://121.78.66.29/bktonight/html_service.php");
        this.webView2.loadUrl("http://121.78.66.29/bktonight/html_provision.php");
        this.webView3.loadUrl("http://121.78.66.29/bktonight/html_privacy.php");

        this.checkBox1 = (CheckBox)findViewById(R.id.agree_checkbox1);
        this.checkBox2 = (CheckBox)findViewById(R.id.agree_checkbox2);
        this.checkBox3 = (CheckBox)findViewById(R.id.agree_checkbox3);
        this.checkBoxAll = (CheckBox)findViewById(R.id.checkbox_all);

        this.imgBack = (ImageView)findViewById(R.id.menu_search);

        this.btnSignUp = (Button)findViewById(R.id.btn_signin);

        this.checkBox1.setOnClickListener(this);
        this.checkBox2.setOnClickListener(this);
        this.checkBox3.setOnClickListener(this);
        this.checkBoxAll.setOnClickListener(this);

        this.imgBack.setOnClickListener(this);
        this.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.agree_checkbox1:
                if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked())
                    checkBoxAll.setChecked(true);
                else
                    checkBoxAll.setChecked(false);
                break;

            case R.id.agree_checkbox2:
                if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked())
                    checkBoxAll.setChecked(true);
                else
                    checkBoxAll.setChecked(false);
                break;

            case R.id.agree_checkbox3:
                if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked())
                    checkBoxAll.setChecked(true);
                else
                    checkBoxAll.setChecked(false);
                break;

            case R.id.checkbox_all:
                if(checkBoxAll.isChecked()) {
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(true);
                }else {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                }
                break;

            case R.id.menu_search:
                this.finish();
                this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.btn_signin:
                if(evaluateAvail()) {
                    Intent intent = new Intent(this, Register2Activity.class);
                    startActivity(intent);
                    this.finish();
                }
                break;
        }
    }

    private boolean evaluateAvail(){

        boolean veryChk1 = checkBox1.isChecked();
        boolean veryChk2 = checkBox2.isChecked();
        boolean veryChk3 = checkBox3.isChecked();
        boolean veryChk_all = checkBoxAll.isChecked();

        if (!veryChk1){
            Toast.makeText(this, "이용악관에 동의합니까?", Toast.LENGTH_SHORT).show();
            checkBox1.forceLayout();
            return false;
        }
        if (!veryChk2){
            Toast.makeText(this, "서비스 이용악관에 동의합니까?", Toast.LENGTH_SHORT).show();
            checkBox2.forceLayout();
            return false;
        }

        if (!veryChk3){
            Toast.makeText(this, "위치 기반 서비스 이용악관에 동의합니까?", Toast.LENGTH_SHORT).show();
            checkBox3.forceLayout();
            return false;
        }
        return true;
    }
}
