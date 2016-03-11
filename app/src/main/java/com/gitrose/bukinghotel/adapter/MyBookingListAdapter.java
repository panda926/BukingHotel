package com.gitrose.bukinghotel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyBookingListAdapter extends ArrayAdapter<MyBookingListEntry> {
    private Context mContext;

    private class ViewHolder {
        ImageView imgHotelImg;
        TextView txtBid;
        TextView txtCheckin;
        TextView txtCheckout;
        TextView txtHotelName;
        TextView txtRoomName;
        TextView txtStatus;

        public ViewHolder(View v) {
            this.imgHotelImg = (ImageView) v.findViewById(R.id.hotel_img);
            this.txtBid = (TextView) v.findViewById(R.id.bid);
            this.txtHotelName = (TextView) v.findViewById(R.id.hotel_name);
            this.txtRoomName = (TextView) v.findViewById(R.id.room_name);
            this.txtCheckin = (TextView) v.findViewById(R.id.check_in);
            this.txtCheckout = (TextView) v.findViewById(R.id.check_out);
            this.txtStatus = (TextView) v.findViewById(R.id.status);
            v.setTag(this);
        }
    }

    public MyBookingListAdapter(Context context, int textViewResourceId, List<MyBookingListEntry> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
    }

    @SuppressLint({"SimpleDateFormat"})
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_my_booking_list, null);
        }
        ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }
        MyBookingListEntry entry = (MyBookingListEntry) getItem(position);
        holder.txtBid.setText(entry.getId());
        holder.imgHotelImg.setScaleType(ScaleType.FIT_XY);
        UrlImageViewHelper.setUrlDrawable(holder.imgHotelImg, entry.getHotelImg());
        holder.txtHotelName.setText(entry.getHotelName());
        holder.txtRoomName.setText(entry.getRoomName());
        holder.txtCheckin.setText(new StringBuilder(String.valueOf(this.mContext.getString(R.string.checkin))).append(" : ").append(entry.getCheckinDate().substring(0, 10)).toString());
        holder.txtCheckout.setText(new StringBuilder(String.valueOf(this.mContext.getString(R.string.checkout))).append(" : ").append(entry.getCheckoutDate().substring(0, 10)).toString());
        if (entry.getStatus().equals("booked")) {
            holder.txtStatus.setText(entry.getBookedTxt());
        } else {
            holder.txtStatus.setText(entry.getProgressTxt());
        }
        if (Integer.valueOf(entry.getCheckinDate().substring(0, 10).replace("-", "")).intValue() < Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())).intValue()) {
            v.setBackgroundColor(Color.parseColor("#b8b8b8"));
        }
//        else if (entry.getStatus().equals(FacebookDialog.COMPLETION_GESTURE_CANCEL)) {
//            v.setBackgroundColor(Color.parseColor("#b8b8b8"));
//        }
        else
        {
            v.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        return v;
    }
}
