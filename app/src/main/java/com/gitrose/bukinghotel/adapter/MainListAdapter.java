package com.gitrose.bukinghotel.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

/**
 * Created by GITRose on 1/19/2016.
 */
public class MainListAdapter extends ArrayAdapter<MainListEntry> {

    private Context mContext;
    private String mSortType;

    private class ViewHolder {
        ImageView imgLandscape;
        ImageView imgThumb1;
        ImageView imgThumb2;
        LinearLayout likerateLayer;
        LinearLayout salerateLayer;
        LinearLayout soldoutLayer;
        TextView txtCategory;
        TextView txtCount;
        TextView txtEvt;
        TextView txtHid;
        TextView txtLike;
        TextView txtLocation1;
        TextView txtLocation2;
        TextView txtName;
        TextView txtNormalPrice1;
        TextView txtNormalPrice2;
        TextView txtNotyet;
        TextView txtPid;
        TextView txtSale;
        TextView txtSalePrice;
        TextView txt_message;
        LinearLayout txt_prices;

        public ViewHolder(View v) {
            this.imgLandscape = (ImageView) v.findViewById(R.id.img_landscape);
            this.txtSale = (TextView) v.findViewById(R.id.txt_sale);
            this.txtCount = (TextView) v.findViewById(R.id.txt_count);
            this.txtName = (TextView) v.findViewById(R.id.txt_name);
            this.txtCategory = (TextView) v.findViewById(R.id.txt_category);
            this.txtLocation1 = (TextView) v.findViewById(R.id.txt_location1);
            this.txtLocation2 = (TextView) v.findViewById(R.id.txt_location2);
            this.txtNormalPrice1 = (TextView) v.findViewById(R.id.txt_realprice1);
            this.txtNormalPrice2 = (TextView) v.findViewById(R.id.txt_realprice2);
            this.txtSalePrice = (TextView) v.findViewById(R.id.txt_nowprice);
            this.txtHid = (TextView) v.findViewById(R.id.hid);
            this.txtPid = (TextView) v.findViewById(R.id.pid);
            this.txtEvt = (TextView) v.findViewById(R.id.event);
            this.txtNotyet = (TextView) v.findViewById(R.id.txt_notyet);
            this.txt_message = (TextView) v.findViewById(R.id.txt_message);
            this.txt_prices = (LinearLayout) v.findViewById(R.id.txt_prices);
            this.salerateLayer = (LinearLayout) v.findViewById(R.id.layer_salerate);
            this.soldoutLayer = (LinearLayout) v.findViewById(R.id.layer_soldout);
            this.likerateLayer = (LinearLayout) v.findViewById(R.id.layer_likerate);
            this.txtLike = (TextView) v.findViewById(R.id.like_text);
            this.imgThumb1 = (ImageView) v.findViewById(R.id.thumb1);
            this.imgThumb2 = (ImageView) v.findViewById(R.id.thumb2);
            v.setTag(this);
        }
    }

    public MainListAdapter(Context context, int textViewResourceId, List<MainListEntry> objects, String sortType) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mSortType = sortType;
    }

    public MainListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_main_list, null);
        }
        ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }
        MainListEntry entry = (MainListEntry) getItem(position);
        holder.txtEvt.setText(entry.getIsEvent());
        holder.txtHid.setText(entry.getHid());
        holder.txtPid.setText(entry.getPid());
        holder.txtName.setText(entry.getName());
        holder.txtName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.txtName.setSelected(true);
        holder.txtLocation1.setText(entry.getStreet1());
        holder.imgLandscape.getLayoutParams().height = HotelUtil.getListHeight(this.mContext);
        UrlImageViewHelper.setUrlDrawable(holder.imgLandscape, entry.getLandscape());
        if (entry.getThumb1().equals("")) {
            holder.imgThumb1.setVisibility(View.GONE);
        } else {
            holder.imgThumb1.setVisibility(View.VISIBLE);
            UrlImageViewHelper.setUrlDrawable(holder.imgThumb1, entry.getThumb1());
        }
        if (entry.getThumb2().equals("")) {
            holder.imgThumb2.setVisibility(View.GONE);
        } else {
            holder.imgThumb2.setVisibility(View.VISIBLE);
            UrlImageViewHelper.setUrlDrawable(holder.imgThumb2, entry.getThumb2());
        }
        if (CONFIG.useLocation) {
            holder.txtLocation2.setText(" | " + this.mContext.getString(R.string.hotel_distance, new Object[]{entry.getDistance()}));
            holder.txtLocation2.setTextColor(Color.parseColor("#bababa"));
        } else {
            holder.txtLocation2.setText(" | " + entry.getStreet2());
            holder.txtLocation2.setTextColor(Color.parseColor("#72747a"));
        }
        Resources resources = this.mContext.getResources();
        int color = resources.getIdentifier(entry.getCode(), "color", this.mContext.getPackageName());
        holder.txtCategory.setText(entry.getCategory());
        if (Build.VERSION.SDK_INT >= 16) {
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(5.0f);
//            shape.setColor(resources.getColor(color));
            holder.txtCategory.setBackground(shape);
        } else {
//            holder.txtCategory.setBackgroundColor(resources.getColor(color));
        }
        if (CONFIG.saleAvailable) {
            holder.txtNotyet.setVisibility(View.INVISIBLE);
        } else {
            holder.txtNotyet.setVisibility(View.VISIBLE);
            holder.txtNotyet.setText(this.mContext.getString(R.string.wait_message, new Object[]{CONFIG.open_sell_time}));
        }
        holder.txtEvt.setText(entry.getIsEvent());

        if (entry.getIsEvent().equals("Y")) {
            holder.txtEvt.setText("Y");
            holder.txtLocation2.setText(" | " + entry.getStreet2());
            holder.txtLocation2.setTextColor(Color.parseColor("#72747a"));
            holder.soldoutLayer.setVisibility(View.INVISIBLE);
            holder.salerateLayer.setVisibility(View.INVISIBLE);
            holder.txtCount.setVisibility(View.INVISIBLE);
            holder.txtNotyet.setVisibility(View.INVISIBLE);
            holder.txt_message.setVisibility(View.VISIBLE);
            holder.txt_message.setText(this.mContext.getString(R.string.free_event));
            holder.txt_prices.setVisibility(View.GONE);
        } else if (CONFIG.saleAvailable) {
            holder.txt_prices.setVisibility(View.VISIBLE);
            if (CONFIG.sel_checkin == null || CONFIG.sel_checkout == null) {
                holder.txtSalePrice.setText(new StringBuilder(String.valueOf(HotelUtil.number_format(Integer.valueOf(entry.getSalePrice()).intValue()))).append("\uc6d0").toString());
                holder.txtNormalPrice1.setText("");
                holder.txtNormalPrice2.setText(new StringBuilder(String.valueOf(HotelUtil.number_format(Integer.valueOf(entry.getNormalPrice()).intValue()))).append("\uc6d0").toString());
                holder.txtNormalPrice2.setPaintFlags(holder.txtNormalPrice2.getPaintFlags() | 16);
            } else if (HotelUtil.diffOfDate(CONFIG.sel_checkin.replace("-", ""), CONFIG.sel_checkout.replace("-", "")) > 1) {
                holder.txtSalePrice.setText(new StringBuilder(String.valueOf(HotelUtil.number_format(Integer.valueOf(entry.getSalePrice()).intValue()))).append("\uc6d0").toString());
                holder.txtNormalPrice1.setText("1\ubc15 \ud3c9\uade0 ");
                holder.txtNormalPrice2.setText(new StringBuilder(String.valueOf(HotelUtil.number_format(Integer.valueOf(entry.getNormalPriceAvg()).intValue()))).append("\uc6d0").toString());
                holder.txtNormalPrice2.setPaintFlags(holder.txtNormalPrice2.getPaintFlags() | 16);
            } else {
                holder.txtSalePrice.setText(new StringBuilder(String.valueOf(HotelUtil.number_format(Integer.valueOf(entry.getSalePrice()).intValue()))).append("\uc6d0").toString());
                holder.txtNormalPrice1.setText("");
                holder.txtNormalPrice2.setText(new StringBuilder(String.valueOf(HotelUtil.number_format(Integer.valueOf(entry.getNormalPrice()).intValue()))).append("\uc6d0").toString());
                holder.txtNormalPrice2.setPaintFlags(holder.txtNormalPrice2.getPaintFlags() | 16);
            }

            if (!entry.getIsEvent().equals("Y")) {
                holder.txt_message.setVisibility(View.GONE);
            }

            if (entry.getSoldout() >= 3 || entry.getSoldout() <= 0) {
                holder.txtCount.setVisibility(View.INVISIBLE);
            } else {
                holder.txtCount.setText(this.mContext.getString(R.string.remain_room, new Object[]{Integer.valueOf(entry.getSoldout())}));
                holder.txtCount.setVisibility(View.VISIBLE);
            }
            holder.soldoutLayer.getLayoutParams().height = HotelUtil.getListHeight(this.mContext);
            if (entry.getSoldout() == 0 || entry.getItemsQuantity() == 0) {
                holder.soldoutLayer.setVisibility(View.VISIBLE);
                holder.txtCount.setVisibility(View.INVISIBLE);
                holder.salerateLayer.setVisibility(View.INVISIBLE);
            } else {
                holder.soldoutLayer.setVisibility(View.INVISIBLE);
                holder.salerateLayer.setVisibility(View.VISIBLE);
                if (entry.getSaleRate().equals("0")) {
                    holder.salerateLayer.setVisibility(View.INVISIBLE);
                    holder.txtSale.setVisibility(View.INVISIBLE);
                } else {
                    holder.txtSale.setVisibility(View.VISIBLE);
                    holder.txtSale.setText(entry.getSaleRate() + "%");
                }
            }
            if (this.mSortType == null || !this.mSortType.equals("like")) {
                holder.likerateLayer.setVisibility(View.GONE);
            } else {
                holder.likerateLayer.setVisibility(View.VISIBLE);
            }
        } else {
            holder.txtSale.setVisibility(View.INVISIBLE);
            holder.txtCount.setVisibility(View.INVISIBLE);
            holder.salerateLayer.setVisibility(View.INVISIBLE);
            holder.soldoutLayer.setVisibility(View.INVISIBLE);
            holder.txt_message.setVisibility(View.VISIBLE);
            holder.txt_message.setText(this.mContext.getString(R.string.wait_price_setting));
            holder.txt_prices.setVisibility(View.GONE);
        }
        return v;
    }
}
