package com.gitrose.bukinghotel.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActDealDetail;
import com.gitrose.bukinghotel.activity.ActLoginPopUp;
import com.gitrose.bukinghotel.activity.ActReservation;
import com.gitrose.bukinghotel.util.HotelUtil;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class FrgRoomtype extends BaseFragment {
    private SharedPreferences _preferences;
    private String category;
    private String city;
    private int cnt;
    private String content;
    private String cookie;
    private String hid;
    private String hname;
    private int items_quantity;
    private String name;
    private String normal_price;
    private String pcontent_txt;
    private String pid;
    private int position;
    private String ptitle_txt;
    private String rid;
    private String roomimg;
    private String sale_price;
    private int totalcnt;

    class ReserveClick implements OnClickListener {
        ReserveClick() {
        }

        public void onClick(View v) {
            Intent intent;
            if (FrgRoomtype.this.cookie != null) {
                intent = new Intent(FrgRoomtype.this.getActivity(), ActReservation.class);
                intent.putExtra("pid", FrgRoomtype.this.pid);
                FrgRoomtype.this.startActivity(intent);
                FrgRoomtype.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            } else {
                intent = new Intent(FrgRoomtype.this.getActivity(), ActLoginPopUp.class);
                intent.putExtra("from", "reservation");
                intent.putExtra("pid", FrgRoomtype.this.pid);
                FrgRoomtype.this.startActivity(intent);
                FrgRoomtype.this.getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_down);
            }
            ActDealDetail.frgroomtype.getDialog().dismiss();
        }
    }

    public static FrgRoomtype create(String ct, String hotelname, String roomimg, String content, String name, String normal_price, String sale_price, int cnt, int items_quantity, int totalcnt, int position, String pid, String title, String pcontent, String category, String hid, String rid) {
        FrgRoomtype fragment = new FrgRoomtype();
        Bundle args = new Bundle();
        args.putInt("cnt", cnt);
        args.putInt("items_quantity", items_quantity);
        args.putInt("position", position);
        args.putInt("totalcnt", totalcnt);
        args.putString("pid", pid);
        args.putString("roomimg", roomimg);
        args.putString("content", content);
        args.putString("name", name);
        args.putString("normal_price", normal_price);
        args.putString("sale_price", sale_price);
        args.putString("ptitle_txt", title);
        args.putString("pcontent_txt", pcontent);
        args.putString("hid", hid);
        args.putString("rid", rid);
        args.putString("category", category);
        args.putString("hname", hotelname);
        args.putString("city", ct);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cnt = getArguments().getInt("cnt");
        this.items_quantity = getArguments().getInt("items_quantity");
        this.position = getArguments().getInt("position");
        this.totalcnt = getArguments().getInt("totalcnt");
        this.pid = getArguments().getString("pid");
        this.roomimg = getArguments().getString("roomimg");
        this.content = getArguments().getString("content");
        this.name = getArguments().getString("name");
        this.normal_price = getArguments().getString("normal_price");
        this.sale_price = getArguments().getString("sale_price");
        this.ptitle_txt = getArguments().getString("ptitle_txt");
        this.pcontent_txt = getArguments().getString("pcontent_txt");
        this.category = getArguments().getString("category");
        this.hid = getArguments().getString("hid");
        this.rid = getArguments().getString("rid");
        this.category = getArguments().getString("category");
        this.hname = getArguments().getString("hname");
        this.city = getArguments().getString("city");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_rooms, container, false);
        TextView tv_normal_price = (TextView) rootView.findViewById(R.id.normal_price);
        TextView tv_sale_price = (TextView) rootView.findViewById(R.id.sale_price);
        TextView tv_content = (TextView) rootView.findViewById(R.id.content);
        ((TextView) rootView.findViewById(R.id.room_name)).setText(this.name);
        tv_normal_price.setText(new StringBuilder(String.valueOf(HotelUtil.number_format(Integer.valueOf(this.normal_price).intValue()))).append("\uc6d0").toString());
        tv_sale_price.setText(new StringBuilder(String.valueOf(HotelUtil.number_format(Integer.valueOf(this.sale_price).intValue()))).append("\uc6d0").toString());
        tv_content.setText(this.content);
        LinearLayout room_cnt_layout = (LinearLayout) rootView.findViewById(R.id.room_cnt_layout);
        TextView room_cnt = (TextView) rootView.findViewById(R.id.room_cnt);
        if (this.cnt == 1 || this.cnt == 2) {
            room_cnt_layout.setVisibility(View.VISIBLE);
            room_cnt.setText("\ub531 " + this.cnt + " \uac1d\uc2e4 \ub0a8\uc74c");
        } else {
            room_cnt_layout.setVisibility(View.GONE);
        }
        tv_normal_price.setPaintFlags(tv_normal_price.getPaintFlags() | 16);
        LinearLayout product_msg = (LinearLayout) rootView.findViewById(R.id.product_msg);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView pcontent = (TextView) rootView.findViewById(R.id.pcontent);
        if (!(title.equals("") || pcontent.equals(""))) {
            product_msg.setVisibility(View.VISIBLE);
            if (!this.ptitle_txt.equals("")) {
                title.setVisibility(View.VISIBLE);
                title.setText(this.ptitle_txt);
            }
            if (!this.pcontent_txt.equals("")) {
                pcontent.setVisibility(View.VISIBLE);
                pcontent.setText(this.pcontent_txt);
            }
        }
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayout marklayout = (LinearLayout) getView().findViewById(R.id.page_mark);
        Button buttonReservation = (Button) getView().findViewById(R.id.select_button);
        for (int i = 0; i < this.totalcnt; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setLayoutParams(new LayoutParams(-2, -2));
            if (i == this.position) {
                iv.setBackgroundResource(R.drawable.dot_on_dark);
            } else {
                iv.setBackgroundResource(R.drawable.dot_off_dark);
            }
            marklayout.addView(iv);
        }
        ImageView roomimgview = (ImageView) getView().findViewById(R.id.room_img);
        roomimgview.getLayoutParams().height = (HotelUtil.getListHeight(getActivity()) / 7) * 6;
        UrlImageViewHelper.setUrlDrawable(roomimgview, this.roomimg);
        this._preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.cookie = this._preferences.getString("userid", null);
        if (this.cnt <= 0 || this.items_quantity == 0) {
            buttonReservation.setBackgroundResource(R.drawable.button_round_reserve_disable);
            buttonReservation.setText("\ud310\ub9e4 \uc644\ub8cc");
            return;
        }
        buttonReservation.setOnClickListener(new ReserveClick());
    }
}
