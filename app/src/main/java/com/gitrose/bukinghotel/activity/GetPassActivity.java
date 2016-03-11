package com.gitrose.bukinghotel.activity;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.view.BaseDialog;

public class GetPassActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backimg;
    private EditText et_forgot_pwd;
    private Button findPass;

    private BaseDialog dialog;
    private TextView txtMessage;
    private Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pass);

        GotoAction();
    }

    private void GotoAction() {

        dialog = new BaseDialog(this, R.style.Transparent);

        backimg = (ImageView)findViewById(R.id.menu_search);
        backimg.setOnClickListener(this);

        findPass = (Button)findViewById(R.id.btn_signin);
        findPass.setOnClickListener(this);

        et_forgot_pwd = (EditText)findViewById(R.id.userid);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.menu_search:
                this.finish();
                break;
            case R.id.btn_signin:
                if( evaluateAvail()) {
                    QGHttpRequest.getInstance().FindPassRequest(this, this.et_forgot_pwd.getText().toString(), new FindPassResult(this));
                }
                break;
        }
    }

    public class FindPassResult extends QGHttpHandler<String> {

        public FindPassResult(Context context) {
            super(context);
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            String strMessage = "등록된 이메일 주소가 아닙니다..";
            ShowToastMessage(strMessage);
        }

        @Override
        public void onGetDataSuccess(String userInfo) {
            ShowDialog();
        }
    }

    private void ShowToastMessage(String strMessage) {
        Toast.makeText(this, strMessage, Toast.LENGTH_LONG).show();
    }

    private void ShowDialog()
    {
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dailog_find_pass, null);

        this.txtMessage = (TextView)dialogView.findViewById(R.id.txt_photo_take);
        this.btnOK = (Button)dialogView.findViewById(R.id.btn_signin);

        txtMessage.setText(this.et_forgot_pwd.getText().toString() + getString(R.string.alarm));

        this.txtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPassActivity.this.dialog.dismiss();
                finish();
            }
        });

        this.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPassActivity.this.dialog.dismiss();
                finish();
            }
        });

        this.dialog.show(dialogView);

//        AlertDialog.Builder buider = new AlertDialog.Builder(this);
//        buider.setView(dialogView);
//
//        final AlertDialog dialog = buider.create();
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//
//        m_takePhoto = (TextView)dialogView.findViewById(R.id.txt_photo_take);
//        m_takePhoto.setText(this.m_email.getText().toString() + getString(R.string.alarm));
//
//
//        m_library = (TextView)dialogView.findViewById(R.id.txt_photo_close);
//        m_library.setOnClickListener(new TextView.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                dialog.dismiss();
//            }
//
//        });
//
//        m_closeDlg = (ImageView)dialogView.findViewById(R.id.btn_photo_close);
//        m_closeDlg.setOnClickListener(new ImageView.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                dialog.dismiss();
//            }
//
//        });
    }

    private boolean evaluateAvail(){

        String emailAddress = et_forgot_pwd.getText().toString();
        if (emailAddress.isEmpty()){
            Toast.makeText(this, "이메일을 입력하십시오.", Toast.LENGTH_SHORT).show();
            et_forgot_pwd.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            Toast.makeText(this, "이메일주소가 정확치 않습니다.", Toast.LENGTH_SHORT).show();
            et_forgot_pwd.requestFocus();
            return false;
        }

        return true;
    }
}
