package com.gitrose.bukinghotel.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.fragment.FrgLandscape;
import com.gitrose.bukinghotel.fragment.FrgRoomTypeDialog;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

public class ActDealDetail extends BaseFragmentActivity implements FrgRoomTypeDialog.onSubmitListener {

    private SharedPreferences _preferences;
    private String cookie;
    private ProgressBar progressbar;
    private String hId;
    private String pId;
    private String evt;
    private String ec_date;
    private String ee_date;
    private Boolean from_push;
    private ViewPager mViewPager;
    private String[] landscapeImgs;

    public static int FIRST_PAGE;
    public static int LOOPS;
    public static int PAGES;
    public static int nowPosition;
    public static int markNowPosition;
    public static int markPrevPosition;

    private String city;
    private String[] names;
    private String[] normal_prices;
    private String[] sale_prices;
    private String[] pids;
    private String rId;
    private String category;
    private String free_promotion;
    private String hotel_name;
    private TextView room_type_name;
    private TextView txt_realprice;
    private TextView txt_nowprice;
    private WebView webView;
    private Runnable Update;
    private Handler handler;
    private Timer swipeTimer;
    private MyPagerAdapter mPagerAdapter;
    private TextView txt_title;
    public static FrgRoomTypeDialog frgroomtype;

    static {
        PAGES = 0;
        LOOPS = 1000;
        FIRST_PAGE = 0;
        nowPosition = 0;
        markNowPosition = 0;
        markPrevPosition = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!(this.Update == null && this.swipeTimer == null)) {
            this.swipeTimer.cancel();
            this.swipeTimer.purge();
            this.swipeTimer = null;
            this.Update = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetailInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();

        super.onStart();
        this._preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.cookie = this._preferences.getString("userid", null);
        this.progressbar = (ProgressBar) findViewById(R.id.loading_progress);
        Intent intent = getIntent();
        this.hId = intent.getStringExtra("hid");
        this.pId = intent.getStringExtra("pid");
        this.evt = intent.getStringExtra("evt");
        this.ec_date = intent.getStringExtra("ec_date");
        this.ee_date = intent.getStringExtra("ee_date");
        this.from_push = Boolean.valueOf(intent.getBooleanExtra("from_push", false));
        this.mViewPager = (ViewPager) findViewById(R.id.img_pager);
        this.mViewPager.getLayoutParams().height = HotelUtil.getListHeight(this);
        markNowPosition = 0;
        markPrevPosition = 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_deal_detail);
    }

    private void getDetailInfo() {
//        String url = "http://api.hotelnow.co.kr/product_detail/" + this.hId;
        String url = "http://121.78.66.29/bktonight/api/bt_hotel_info.php";
        QGHttpRequest.getInstance().getDetailInfo(this, this.hId, url, this.pId, this.evt, this.ec_date, this.ee_date, new GetDetailInfoHandler(this));
    }

    @Override
    public void setOnSubmitListener(int idx) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        this.room_type_name.setText(this.names[idx]);
        this.txt_realprice.setText(getString(R.string.price, new Object[]{nf.format(Integer.valueOf(this.normal_prices[idx]))}));
        this.txt_realprice.setPaintFlags(this.txt_realprice.getPaintFlags() | 16);
        this.txt_nowprice.setText(getString(R.string.price, new Object[]{nf.format(Integer.valueOf(this.sale_prices[idx]))}));
        this.pId = this.pids[idx];
    }

    class GetDetailInfoHandler extends QGHttpHandler<String> {

        public GetDetailInfoHandler(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {

            ActDealDetail.this.SetData(s);
        }

        public void onFinish() {
            super.onFinish();
        }
    }

    class FullButtonClick implements View.OnClickListener {
        FullButtonClick() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(ActDealDetail.this, ActPortraitView.class);
            intent.putExtra("hid", ActDealDetail.this.hId);
            intent.putExtra("idx", ActDealDetail.markNowPosition);
            ActDealDetail.this.startActivity(intent);
            ActDealDetail.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    private void SetData(String strResult)
    {

        try {
            JSONObject response = new JSONObject(strResult);


            int i;
            ActDealDetail.this.progressbar.setVisibility(View.GONE);
            ((LinearLayout) ActDealDetail.this.findViewById(R.id.wrapper)).setVisibility(View.VISIBLE);
            JSONArray data = response.getJSONArray("data");
            JSONArray roomtype = data.getJSONObject(0).getJSONArray("roomtypes");
            JSONArray photos = data.getJSONObject(0).getJSONArray("photos");
            ActDealDetail.this.landscapeImgs = new String[photos.length()];
            ActDealDetail.this.pId = data.getJSONObject(0).getString("product_id");
            ActDealDetail.this.city = data.getJSONObject(0).getString("city");
            ActDealDetail.this.names = new String[data.length()];
            ActDealDetail.this.normal_prices = new String[data.length()];
            ActDealDetail.this.sale_prices = new String[data.length()];
            ActDealDetail.this.pids = new String[data.length()];
            int total_sold_out = 0;
            int total_items_quantity = 0;
            for (i = 0; i < data.length(); i++) {
                ActDealDetail.this.names[i] = data.getJSONObject(i).getJSONArray("roomtypes").getJSONObject(0).getString("name");
                ActDealDetail.this.normal_prices[i] = data.getJSONObject(i).getString("normal_price");
                ActDealDetail.this.sale_prices[i] = data.getJSONObject(i).getString("sale_price");
                ActDealDetail.this.pids[i] = data.getJSONObject(i).getString("product_id");
                total_sold_out += data.getJSONObject(i).getInt("sold_out");
                total_items_quantity += data.getJSONObject(i).getInt("items_quantity");
            }
            for (i = 0; i < photos.length(); i++) {
                ActDealDetail.this.landscapeImgs[i] = photos.getJSONObject(i).getString("landscape");
            }
            ActDealDetail.this.hId = data.getJSONObject(0).getString("hotel_id");
            ActDealDetail.this.rId = data.getJSONObject(0).getString("room_id");
            ActDealDetail.this.category = data.getJSONObject(0).getString("category");
            ActDealDetail.this.free_promotion = data.getJSONObject(0).getString("free_promotion");
            ActDealDetail.this.hotel_name = data.getJSONObject(0).getString("name");
            ActDealDetail.this.txt_title = (TextView)ActDealDetail.this.findViewById(R.id.title_tv_left);
            ActDealDetail.this.txt_title.setText(data.getJSONObject(0).getString("name"));
            ActDealDetail.this.room_type_name = (TextView) ActDealDetail.this.findViewById(R.id.room_type_name);
            ActDealDetail.this.txt_realprice = (TextView) ActDealDetail.this.findViewById(R.id.txt_realprice);
            ActDealDetail.this.txt_nowprice = (TextView) ActDealDetail.this.findViewById(R.id.txt_nowprice);
            TextView room_cnt = (TextView) ActDealDetail.this.findViewById(R.id.room_cnt);
            LinearLayout room_cnt_layout = (LinearLayout) ActDealDetail.this.findViewById(R.id.room_cnt_layout);
            Button reservationBtn = (Button) ActDealDetail.this.findViewById(R.id.btn_reservation);
            ActDealDetail.this.room_type_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            ActDealDetail.this.room_type_name.setSelected(true);
            NumberFormat nf = NumberFormat.getNumberInstance();
            TextView access$27 = ActDealDetail.this.txt_realprice;
            ActDealDetail actDealDetail = ActDealDetail.this;
            Object[] objArr = new Object[1];
            objArr[0] = nf.format(Integer.valueOf(data.getJSONObject(0).getString("normal_price")));
            access$27.setText(actDealDetail.getString(R.string.price, objArr));
            ActDealDetail.this.txt_realprice.setPaintFlags(ActDealDetail.this.txt_realprice.getPaintFlags() | 16);
            if (data.length() > 1) {
                ActDealDetail.this.room_type_name.setText(ActDealDetail.this.getString(R.string.roomtype_cnt, new Object[]{String.valueOf(data.length())}));
                access$27 = ActDealDetail.this.txt_nowprice;
                actDealDetail = ActDealDetail.this;
                objArr = new Object[1];
                objArr[0] = nf.format(Integer.valueOf(data.getJSONObject(0).getString("sale_price")));
                access$27.setText(actDealDetail.getString(R.string.price_from, objArr));
            } else {
                ActDealDetail.this.room_type_name.setText(roomtype.getJSONObject(0).getString("name"));
                access$27 = ActDealDetail.this.txt_nowprice;
                actDealDetail = ActDealDetail.this;
                objArr = new Object[1];
                objArr[0] = nf.format(Integer.valueOf(data.getJSONObject(0).getString("sale_price")));
                access$27.setText(actDealDetail.getString(R.string.price, objArr));
            }
            ActDealDetail actDealDetail2 = ActDealDetail.this;
            ((Button) ActDealDetail.this.findViewById(R.id.full_btn)).setOnClickListener(new FullButtonClick());
            ActDealDetail.this.webView = (WebView) ActDealDetail.this.findViewById(R.id.web_area);
            ActDealDetail.this.webView.getSettings();
            ActDealDetail.this.webView.setBackgroundColor(getResources().getColor(R.color.back_detail));
            WebSettings webSettings = ActDealDetail.this.webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUserAgentString(webSettings.getUserAgentString() + "HOTELNOW_APP_ANDROID");
            webSettings.setCacheMode(-1);
            ActDealDetail.this.webView.addJavascriptInterface(new DetailInterface(), "DetailInterface");
            ActDealDetail.this.webView.setWebViewClient(new WebViewHandler());
            if (data.length() > 1) {
                if (ActDealDetail.this.ec_date != null) {
                    if (ActDealDetail.this.ee_date != null) {
//                        ActDealDetail.this.webView.loadUrl("http://api.hotelnow.co.kr/product_detail_web/" + ActDealDetail.this.hId + "?ec_date=" + ActDealDetail.this.ec_date + "&ee_date=" + ActDealDetail.this.ee_date);
                        ActDealDetail.this.webView.loadUrl("http://121.78.66.29/bktonight/html_detail_hotel.php?ht_id=" + ActDealDetail.this.hId);
                    }
                }
                else
//                ActDealDetail.this.webView.loadUrl("http://api.hotelnow.co.kr/product_detail_web/" + ActDealDetail.this.hId);
                    ActDealDetail.this.webView.loadUrl("http://121.78.66.29/bktonight/html_detail_hotel.php?ht_id=" + ActDealDetail.this.hId);
            } else {
//                ActDealDetail.this.webView.loadUrl("http://api.hotelnow.co.kr/product_detail_web/" + ActDealDetail.this.hId + "?pid=" + ActDealDetail.this.pId);
                ActDealDetail.this.webView.loadUrl("http://121.78.66.29/bktonight/html_detail_hotel.php?ht_id=" + ActDealDetail.this.hId);
            }
            ActDealDetail.this.showPager();
            reservationBtn.setVisibility(View.VISIBLE);
            if (ActDealDetail.this.free_promotion.equals("Y")) {
                room_cnt_layout.setVisibility(View.GONE);
                ActDealDetail.this.txt_realprice.setVisibility(View.GONE);
                ActDealDetail.this.txt_nowprice.setTextSize(13.0f);
                ActDealDetail.this.txt_nowprice.setText(ActDealDetail.this.getString(R.string.free_event));
                reservationBtn.setBackgroundResource(R.drawable.button_round_reserve_red);
                reservationBtn.setText(ActDealDetail.this.getString(R.string.event_apply));
                ((ImageView) ActDealDetail.this.findViewById(R.id.event_img)).setVisibility(View.VISIBLE);
//                reservationBtn.setOnClickListener(new C05263());
                return;
            }
            if (data.getJSONObject(0).getString("avail_sell_time").toUpperCase().equals("N")) {
                room_cnt_layout.setVisibility(View.VISIBLE);
                room_cnt_layout.setBackgroundColor(Color.parseColor("#88000000"));
                room_cnt.setText(ActDealDetail.this.getString(R.string.wait_message2, new Object[]{CONFIG.open_sell_time}));
                ActDealDetail.this.txt_realprice.setVisibility(View.GONE);
                ActDealDetail.this.txt_nowprice.setTextSize(13.0f);
                ActDealDetail.this.txt_nowprice.setText(ActDealDetail.this.getString(R.string.wait_price_setting));
                reservationBtn.setBackgroundResource(R.drawable.button_round_reserve_disable);
                reservationBtn.setText(ActDealDetail.this.getString(R.string.sell_waiting));
            } else if (total_sold_out == 0 || total_items_quantity == 0) {
                reservationBtn.setBackgroundResource(R.drawable.button_round_reserve_disable);
                reservationBtn.setText(ActDealDetail.this.getString(R.string.sell_complete));
            } else {
                if (data.length() > 1) {
                    reservationBtn.setText(ActDealDetail.this.getString(R.string.select_roomtype));
                    reservationBtn.setOnClickListener(new RoomTypeCLick(data));
                } else {
                    if (data.getJSONObject(0).getInt("sold_out") > 2 || data.getJSONObject(0).getInt("sold_out") <= 0) {
                        room_cnt_layout.setVisibility(View.GONE);
                    } else {
                        room_cnt_layout.setVisibility(View.VISIBLE);
                        room_cnt_layout.setBackgroundColor(Color.parseColor("#CC8E44AD"));
                        room_cnt.setText(ActDealDetail.this.getString(R.string.remain_room_cnt, new Object[]{data.getJSONObject(0).getString("sold_out")}));
                    }
                    reservationBtn.setText(ActDealDetail.this.getString(R.string.booking_now));
                    reservationBtn.setOnClickListener(new ReserveClick());
                }

            }

            ActDealDetail.this.setTimer();
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class ReserveClick implements View.OnClickListener {
        ReserveClick() {
        }

        public void onClick(View v) {
            Intent intent;
            if (ActDealDetail.this.cookie != null)
            {
                intent = new Intent(ActDealDetail.this, ActReservation.class);
                intent.putExtra("pid", ActDealDetail.this.pId);
                if (!(ActDealDetail.this.ec_date == null || ActDealDetail.this.ee_date == null)) {
                    intent.putExtra("ec_date", ActDealDetail.this.ec_date);
                    intent.putExtra("ee_date", ActDealDetail.this.ee_date);
                }
                ActDealDetail.this.startActivity(intent);
                ActDealDetail.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
            else {
                intent = new Intent(ActDealDetail.this, ActLoginPopUp.class);
                intent.putExtra("from", "reservation");
                intent.putExtra("pid", ActDealDetail.this.pId);
                ActDealDetail.this.startActivity(intent);
                ActDealDetail.this.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_down);
            }
        }
    }

    class WebViewHandler extends WebViewClient {
        WebViewHandler() {
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.loadUrl("file:///android_asset/404.html");
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    class RoomTypeCLick implements View.OnClickListener {
        private final JSONArray val$data;

        RoomTypeCLick(JSONArray jSONArray) {
            this.val$data = jSONArray;
        }

        public void onClick(View v) {
            ActDealDetail.frgroomtype = new FrgRoomTypeDialog();
            ActDealDetail.frgroomtype.mListener = ActDealDetail.this;
            ActDealDetail.frgroomtype.products_data = this.val$data;
            ActDealDetail.frgroomtype.hotelname = ActDealDetail.this.hotel_name;
            ActDealDetail.frgroomtype.city = ActDealDetail.this.city;
            ActDealDetail.frgroomtype.show(ActDealDetail.this.getSupportFragmentManager(), "");
        }
    }

    public ActDealDetail() {
        this.city = "";
        this.from_push = Boolean.valueOf(false);
        this.Update = null;
        this.handler = new Handler();
        this.swipeTimer = new Timer();
    }

    private void setTimer() {
        this.Update = new UpdateHandler();
        this.swipeTimer = new Timer();
        this.swipeTimer.schedule(new ScheduleHandler(), 1000, 4000);
    }

    class ScheduleHandler extends TimerTask {
        ScheduleHandler() {
        }

        public void run() {
            ActDealDetail.this.handler.post(ActDealDetail.this.Update);
        }
    }

    class UpdateHandler implements Runnable {
        UpdateHandler() {
        }

        public void run() {
            if (ActDealDetail.markNowPosition > ActDealDetail.PAGES - 1) {
                ActDealDetail.markNowPosition = 0;
            }
            if (ActDealDetail.this.landscapeImgs != null && ActDealDetail.this.mViewPager != null && ActDealDetail.PAGES != 0) {
                ViewPager access$39 = ActDealDetail.this.mViewPager;
                int i = ActDealDetail.nowPosition;
                ActDealDetail.nowPosition = i + 1;
                access$39.setCurrentItem(i, true);
                ActDealDetail.markNowPosition = ActDealDetail.nowPosition % ActDealDetail.PAGES;
            }
        }
    }

    private void showPager() {
        PAGES = this.landscapeImgs.length;
        FIRST_PAGE = (PAGES * LOOPS) / 2;
        try {
            this.mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this.landscapeImgs);
            this.mViewPager.setAdapter(this.mPagerAdapter);
            this.mViewPager.setOnPageChangeListener(this.mPagerAdapter);
            this.mViewPager.setCurrentItem(FIRST_PAGE, true);
            this.mViewPager.setOffscreenPageLimit(3);
            this.mViewPager.setOnPageChangeListener(new PageChangeHandler());
        } catch (Exception e) {
//            HotelUtil.doRestart(getApplicationContext());
        }
        nowPosition = FIRST_PAGE;
        initPageMark();
    }

    private void initPageMark() {
        ((LinearLayout) findViewById(R.id.page_mark)).removeAllViews();
        for (int i = 0; i < this.landscapeImgs.length; i++) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            if (i == markNowPosition) {
                iv.setBackgroundResource(R.drawable.dot_on);
            } else {
                iv.setBackgroundResource(R.drawable.dot_off);
            }
            ((LinearLayout) findViewById(R.id.page_mark)).addView(iv);
        }
        markPrevPosition = markNowPosition;
    }

    class PageChangeHandler implements ViewPager.OnPageChangeListener {
        PageChangeHandler() {
        }

        public void onPageSelected(int position) {
            try {
                ActDealDetail.nowPosition = position;
                ActDealDetail.markNowPosition = position % ActDealDetail.PAGES;
                ((LinearLayout) ActDealDetail.this.findViewById(R.id.page_mark)).getChildAt(ActDealDetail.markPrevPosition).setBackgroundResource(R.drawable.dot_off);
                ((LinearLayout) ActDealDetail.this.findViewById(R.id.page_mark)).getChildAt(ActDealDetail.markNowPosition).setBackgroundResource(R.drawable.dot_on);
                ActDealDetail.markPrevPosition = ActDealDetail.markNowPosition;
            } catch (Exception e) {
//                HotelUtil.doRestart(ActDealDetail.this.getApplicationContext());
            }
        }

        public void onPageScrolled(int position, float positionOffest, int positionOffsetPixels) {
            if (ActDealDetail.PAGES != 0) {
                ActDealDetail.markNowPosition = position % ActDealDetail.PAGES;
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private String[] landscapeImgs;

        public MyPagerAdapter(FragmentManager frgm, String[] imgs) {
            super(frgm);
            this.landscapeImgs = imgs;
        }

        public Fragment getItem(int position) {
            if (ActDealDetail.PAGES != 0) {
                position %= ActDealDetail.PAGES;
            }
            return FrgLandscape.newInstance(ActDealDetail.this, position, this.landscapeImgs[position], ActDealDetail.this.hId);
        }

        public int getCount() {
            return ActDealDetail.PAGES * ActDealDetail.LOOPS;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    private class DetailInterface {

        /* renamed from: com.hotelnow.activities.ActDealDetail.DetailInterface.1 */
        class C05311 implements Runnable {
            C05311() {
            }

            public void run() {
//                Intent intent = new Intent(ActDealDetail.this, ActPortraitView.class);
//                intent.putExtra("hid", ActDealDetail.this.hId);
//                intent.putExtra("idx", 0);
//                ActDealDetail.this.startActivity(intent);
//                ActDealDetail.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        }

        /* renamed from: com.hotelnow.activities.ActDealDetail.DetailInterface.2 */
        class C05322 implements Runnable {
            private final /* synthetic */ String val$url;

            C05322(String str) {
                this.val$url = str;
            }

            public void run() {
//                Intent intent = new Intent(ActDealDetail.this, ActWebView.class);
//                intent.putExtra("url", this.val$url);
//                intent.putExtra("title", ActDealDetail.this.hotel_name);
//                ActDealDetail.this.startActivity(intent);
//                ActDealDetail.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        }

        /* renamed from: com.hotelnow.activities.ActDealDetail.DetailInterface.3 */
        class C05333 implements Runnable {
            C05333() {
            }

            public void run() {
//                Intent intent = new Intent(ActDealDetail.this, ActMap.class);
//                intent.putExtra("hId", ActDealDetail.this.hId);
//                ActDealDetail.this.startActivity(intent);
//                ActDealDetail.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        }

        /* renamed from: com.hotelnow.activities.ActDealDetail.DetailInterface.4 */
        class C05344 implements Runnable {
            C05344() {
            }

            public void run() {
                ActDealDetail.this.webView.loadUrl("http://api.hotelnow.co.kr/product_detail_web/" + ActDealDetail.this.pId);
            }
        }

        private DetailInterface() {
        }

        @JavascriptInterface
        public void goPortratit() {
//            ActDealDetail.this.handler.post(new C05311());
        }

        @JavascriptInterface
        public void goBlog(String url) {
//            ActDealDetail.this.handler.post(new C05322(url));
        }

        @JavascriptInterface
        public void goMap() {
//            ActDealDetail.this.handler.post(new C05333());
        }

        @JavascriptInterface
        public void reoladDetail() {
//            ActDealDetail.this.handler.post(new C05344());
        }
    }
}
