package com.gitrose.bukinghotel.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActDealDetail;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.Serializable;
import java.util.Map;

@SuppressLint({"NewApi"})
public class FrgMainFixed extends BaseFragment {
    private static SharedPreferences _preferences;
    private String evt;
    private String hid;
    private String hname;

    class C06391 implements OnClickListener {
        C06391() {
        }

        public void onClick(View v) {

            Intent intent = new Intent(FrgMainFixed.this.getActivity(), ActDealDetail.class);
            intent.putExtra("hid", FrgMainFixed.this.hid);
            intent.putExtra("evt", FrgMainFixed.this.evt);
            FrgMainFixed.this.startActivity(intent);
            FrgMainFixed.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    public FrgMainFixed() {
        this.hname = "";
    }

    public static FrgMainFixed newInstance(Serializable fixed) {
        FrgMainFixed f = new FrgMainFixed();
        Map<String, String> fix = (Map) fixed;
        Bundle args = new Bundle();
        args.putString("hid", (String) fix.get("hid"));
        args.putString("name", (String) fix.get("name"));
        args.putString("street1", (String) fix.get("street1"));
        args.putString("landscape", (String) fix.get("landscape"));
        args.putString("distance", (String) fix.get("distance"));
        args.putString("street2", (String) fix.get("street2"));
        args.putString("code", (String) fix.get("code"));
        args.putString("category", (String) fix.get("category"));
        args.putString("event", (String) fix.get("event"));
        args.putString("normal_price", (String) fix.get("normal_price"));
        args.putString("sale_price", (String) fix.get("sale_price"));
        args.putString("sale_price_total", (String) fix.get("sale_price_total"));
        args.putString("sold_out", (String) fix.get("sold_out"));
        args.putString("items_quantity", (String) fix.get("items_quantity"));
        args.putString("sale_rate", (String) fix.get("sale_rate"));
        args.putString("fthumb1", (String) fix.get("fthumb1"));
        args.putString("fthumb2", (String) fix.get("fthumb2"));
        f.setArguments(args);
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        return (LinearLayout) inflater.inflate(R.layout.row_main_fixed, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView niv = (ImageView) getView().findViewById(R.id.img_landscape);
        LayoutParams layoutParams = niv.getLayoutParams();
        layoutParams.height = HotelUtil.getListHeight(getActivity());
//        UrlImageViewHelper.setUrlDrawable(niv, new StringBuilder(String.valueOf(getArguments().getString("landscape"))).append("?evt").toString());
        UrlImageViewHelper.setUrlDrawable(niv, new StringBuilder(String.valueOf(getArguments().getString("landscape"))).toString());
        ImageView fthumb1 = (ImageView) getView().findViewById(R.id.fthumb1);
        ImageView fthumb2 = (ImageView) getView().findViewById(R.id.fthumb2);
        TextView txtNotyet = (TextView) getView().findViewById(R.id.txt_notyet);
        TextView txtCount = (TextView) getView().findViewById(R.id.txt_count);
        TextView txtName = (TextView) getView().findViewById(R.id.txt_name);
        TextView txtCategory = (TextView) getView().findViewById(R.id.txt_category);
        TextView txtLocation1 = (TextView) getView().findViewById(R.id.txt_location1);
        TextView txtLocation2 = (TextView) getView().findViewById(R.id.txt_location2);
        TextView txtNormalPrice = (TextView) getView().findViewById(R.id.txt_realprice);
        TextView txtSalePrice = (TextView) getView().findViewById(R.id.txt_nowprice);
        TextView txt_message = (TextView) getView().findViewById(R.id.txt_message);
        LinearLayout txt_prices = (LinearLayout) getView().findViewById(R.id.txt_prices);
        if (getArguments().getString("fthumb1").equals("")) {
            fthumb1.setVisibility(View.GONE);
        } else {
            fthumb1.setVisibility(View.VISIBLE);
            UrlImageViewHelper.setUrlDrawable(fthumb1, getArguments().getString("fthumb1"));
        }
        if (getArguments().getString("fthumb2").equals("")) {
            fthumb2.setVisibility(View.GONE);
        } else {
            fthumb2.setVisibility(View.VISIBLE);
            UrlImageViewHelper.setUrlDrawable(fthumb2, getArguments().getString("fthumb2"));
        }
        if (CONFIG.saleAvailable) {
            txtNotyet.setVisibility(View.GONE);
            txt_message.setVisibility(View.GONE);
            txt_prices.setVisibility(View.VISIBLE);
            txt_message.setVisibility(View.GONE);
        } else {
            txtNotyet.setVisibility(View.VISIBLE);
            txtNotyet.setText(getActivity().getString(R.string.wait_message, new Object[]{CONFIG.open_sell_time}));
            txtCount.setVisibility(View.INVISIBLE);
            txt_message.setVisibility(View.VISIBLE);
            txt_message.setText(getActivity().getString(R.string.wait_price_setting));
            txt_prices.setVisibility(View.GONE);
        }
        int nprice = Integer.valueOf(getArguments().getString("normal_price")).intValue();
        int sprice = Integer.valueOf(getArguments().getString("sale_price")).intValue();
        this.hname = getArguments().getString("name");
        txtNormalPrice.setText(HotelUtil.number_format(nprice) + "\uc6d0");
        txtNormalPrice.setPaintFlags(txtNormalPrice.getPaintFlags() | 16);
        txtSalePrice.setText(HotelUtil.number_format(sprice) + "\uc6d0");
        txtName.setText(getArguments().getString("name"));
        txtName.setEllipsize(TruncateAt.MARQUEE);
        txtName.setSelected(true);
        txtCategory.setText(getArguments().getString("category"));
        Resources resources = getActivity().getResources();
        int color = resources.getIdentifier(getArguments().getString("code"), "color", getActivity().getPackageName());
        if (VERSION.SDK_INT >= 16) {
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(5.0f);
//            shape.setColor(resources.getColor(color));
            txtCategory.setBackground(shape);
        } else {
//            txtCategory.setBackgroundColor(resources.getColor(color));
        }
        txtLocation1.setText(getArguments().getString("street1"));
        _preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (_preferences.getBoolean("flag_use_location", false)) {
            txtLocation2.setText(" | " + getActivity().getString(R.string.hotel_distance, new Object[]{getArguments().getString("distance")}));
            txtLocation2.setTextColor(Color.parseColor("#bababa"));
        } else {
            txtLocation2.setText(" | " + getArguments().getString("street2"));
            txtLocation2.setTextColor(Color.parseColor("#72747a"));
        }
        this.hid = getArguments().getString("hid");
        this.evt = getArguments().getString("event");
        getView().setOnClickListener(new C06391());
    }
}
