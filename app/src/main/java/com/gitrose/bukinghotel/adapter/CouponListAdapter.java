package com.gitrose.bukinghotel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.util.HotelUtil;

import java.util.List;

public class CouponListAdapter extends ArrayAdapter<CouponEntry> {
    private Context mContext;

    private class ViewHolder {
        LinearLayout coupon_row;
        TextView txtDate;
        TextView txtPrice;
        TextView txtTitle;
        TextView txtUseyn;

        public ViewHolder(View v) {
            this.coupon_row = (LinearLayout) v.findViewById(R.id.coupon_row);
            this.txtTitle = (TextView) v.findViewById(R.id.title);
            this.txtUseyn = (TextView) v.findViewById(R.id.useyn);
            this.txtPrice = (TextView) v.findViewById(R.id.price);
            this.txtDate = (TextView) v.findViewById(R.id.date);
            v.setTag(this);
        }
    }

    public CouponListAdapter(Context context, int textViewResourceId, List<CouponEntry> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
    }

    @SuppressLint({"SimpleDateFormat"})
    public View getView(int position, View convertView, ViewGroup parent) {
        String useyn;
        View v = convertView;
        if (v == null) {
            v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_coupon_money, null);
        }
        ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }
        CouponEntry entry = (CouponEntry) getItem(position);
        String price = new StringBuilder(String.valueOf(HotelUtil.number_format(entry.getPrice()))).append("\uc6d0").toString();
        if (entry.getUseyn().equals("Y")) {
            holder.coupon_row.setBackgroundColor(Color.parseColor("#b8b8b8"));
            useyn = "\uc0ac\uc6a9\ud568";
            holder.txtUseyn.setTextColor(Color.parseColor("#666666"));
        } else if (entry.getExpiration().equals("Y")) {
            holder.coupon_row.setBackgroundColor(Color.parseColor("#b8b8b8"));
            useyn = "\ub9cc\ub8cc";
            holder.txtUseyn.setTextColor(Color.parseColor("#666666"));
        } else {
            holder.coupon_row.setBackgroundColor(Color.parseColor("#ffffff"));
            useyn = "\uc0ac\uc6a9\uac00\ub2a5";
            holder.txtUseyn.setTextColor(Color.parseColor("#ec792f"));
        }
        holder.txtTitle.setText(entry.getName());
        holder.txtUseyn.setText(useyn);
        holder.txtPrice.setText(price);
        holder.txtDate.setText(new StringBuilder(String.valueOf(entry.getStart().substring(0, 10))).append("\n").append(entry.getEnd().substring(0, 10)).toString());
        return v;
    }
}
