package com.gitrose.bukinghotel.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;

/**
 * Created by GITRose on 1/23/2016.
 */
public class DialogBookingCaution extends Dialog {
    private TextView caution_txt;
    private Context mContext;
    private Button mLeftButton;
    private View.OnClickListener mLeftClickListener;
    private Button mRightButton;
    private View.OnClickListener mRightClickListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = 2;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_booking_caution);
        this.caution_txt = (TextView) findViewById(R.id.caution_txt);
        this.mLeftButton = (Button) findViewById(R.id.btn_skip);
        this.mRightButton = (Button) findViewById(R.id.btn_ok);
        this.mLeftButton.setOnClickListener((View.OnClickListener) this.mLeftClickListener);
        this.mRightButton.setOnClickListener((View.OnClickListener) this.mRightClickListener);
    }

    public DialogBookingCaution(Context context, View.OnClickListener left, View.OnClickListener right) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.mLeftClickListener = left;
        this.mRightClickListener = right;
    }
}
