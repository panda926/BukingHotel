package com.gitrose.bukinghotel.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;

import org.json.JSONArray;
import org.json.JSONException;

public class FrgRoomTypeDialog extends DialogFragment {
    private String[] categories;
    public String city;
    private String[] contents;
    private String[] hids;
    public String hotelname;
    public String idx;
    private Integer[] item_quantities;
    public Button mButton;
    public onSubmitListener mListener;
    private PagerAdapter mPagerAdapter;
    private String[] names;
    private String[] normal_prices;
    private String[] pcontents;
    private String[] pids;
    public JSONArray products_data;
    private String[] rids;
    private String[] roomImgs;
    private String[] sale_prices;
    private Integer[] soldout_cnts;
    private String[] titles;

    public interface onSubmitListener {
        void setOnSubmitListener(int i);
    }

    private class PagerAdapter extends FragmentPagerAdapter implements OnPageChangeListener {
        private JSONArray pdata;

        public PagerAdapter(FragmentManager fm, Context context, JSONArray data) {
            super(fm);
            this.pdata = data;
        }

        public Fragment getItem(int position) {
            return FrgRoomtype.create(FrgRoomTypeDialog.this.city, FrgRoomTypeDialog.this.hotelname, FrgRoomTypeDialog.this.roomImgs[position], FrgRoomTypeDialog.this.contents[position], FrgRoomTypeDialog.this.names[position], FrgRoomTypeDialog.this.normal_prices[position], FrgRoomTypeDialog.this.sale_prices[position], FrgRoomTypeDialog.this.soldout_cnts[position].intValue(), FrgRoomTypeDialog.this.item_quantities[position].intValue(), this.pdata.length(), position, FrgRoomTypeDialog.this.pids[position], FrgRoomTypeDialog.this.titles[position], FrgRoomTypeDialog.this.pcontents[position], FrgRoomTypeDialog.this.categories[position], FrgRoomTypeDialog.this.hids[position], FrgRoomTypeDialog.this.rids[position]);
        }

        public int getCount() {
            return this.pdata.length();
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        window.setGravity(17);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roomtype, container);
        this.pids = new String[this.products_data.length()];
        this.roomImgs = new String[this.products_data.length()];
        this.contents = new String[this.products_data.length()];
        this.names = new String[this.products_data.length()];
        this.normal_prices = new String[this.products_data.length()];
        this.sale_prices = new String[this.products_data.length()];
        this.soldout_cnts = new Integer[this.products_data.length()];
        this.item_quantities = new Integer[this.products_data.length()];
        this.titles = new String[this.products_data.length()];
        this.pcontents = new String[this.products_data.length()];
        this.categories = new String[this.products_data.length()];
        this.hids = new String[this.products_data.length()];
        this.rids = new String[this.products_data.length()];
        int i = 0;
        while (i < this.products_data.length()) {
            try {
                this.pids[i] = this.products_data.getJSONObject(i).getString("product_id");
                this.roomImgs[i] = this.products_data.getJSONObject(i).getJSONArray("room_imgs").getJSONObject(0).getString("landscape");
                this.contents[i] = this.products_data.getJSONObject(i).getJSONArray("roomtypes").getJSONObject(0).getString("content");
                this.names[i] = this.products_data.getJSONObject(i).getJSONArray("roomtypes").getJSONObject(0).getString("name");
                this.normal_prices[i] = this.products_data.getJSONObject(i).getString("normal_price");
                this.sale_prices[i] = this.products_data.getJSONObject(i).getString("sale_price");
                this.soldout_cnts[i] = Integer.valueOf(this.products_data.getJSONObject(i).getInt("sold_out"));
                this.item_quantities[i] = Integer.valueOf(this.products_data.getJSONObject(i).getInt("items_quantity"));
                this.titles[i] = this.products_data.getJSONObject(i).getString("title");
                this.pcontents[i] = this.products_data.getJSONObject(i).getString("content");
                this.categories[i] = this.products_data.getJSONObject(i).getString("category");
                this.hids[i] = this.products_data.getJSONObject(i).getString("hotel_id");
                this.rids[i] = this.products_data.getJSONObject(i).getString("room_id");
                i++;
            } catch (JSONException e) {
                Toast.makeText(container.getContext(), "룸 정보가 올바르지 않습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        }
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewPager mViewPager = (ViewPager) getView().findViewById(R.id.room_pager);
        mViewPager.setClipToPadding(false);
        this.mPagerAdapter = new PagerAdapter(getChildFragmentManager(), getActivity(), this.products_data);
        mViewPager.setAdapter(this.mPagerAdapter);
        mViewPager.setOnPageChangeListener(this.mPagerAdapter);
    }
}
