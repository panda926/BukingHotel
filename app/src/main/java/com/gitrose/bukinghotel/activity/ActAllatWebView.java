package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ActAllatWebView extends BaseActivity {

    private String bid;
    private String cancelMsg;
    private final Handler handler;
    private String hotel_name;
    private String pid;
    private String uid;
    private TextView txtTitle;
    WebView webView;

    public ActAllatWebView() {
        this.handler = new Handler();
        this.cancelMsg = "";
        this.hotel_name = "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_allat_web_view);

        Intent intent = getIntent();
        int paytype = intent.getIntExtra("paytype", 1);
        this.hotel_name = intent.getStringExtra("hotel_name");
        this.bid = intent.getStringExtra("bid");
        this.pid = intent.getStringExtra("pid");
        this.uid = PreferenceManager.getDefaultSharedPreferences(this).getString("userid", null);
        this.txtTitle = (TextView)findViewById(R.id.title_tv_left);
        this.txtTitle.setText("결제하기 - " + this.hotel_name);
        this.webView = (WebView) findViewById(R.id.webView);
        if (Build.VERSION.SDK_INT >= 21) {
            this.webView.getSettings().setMixedContentMode(0);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(this.webView, true);
        }
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setUserAgentString(new StringBuilder(String.valueOf(this.webView.getSettings().getUserAgentString())).append(" HOTELNOW_APP_ANDROID").toString());
        this.webView.setWebViewClient(new MyViewClient());
        this.webView.setWebChromeClient(new WebChromeClient());
        this.webView.addJavascriptInterface(new billWebInterface(), "billWebInterface");
        String url = "";
        if (paytype == 1) {
            url = "http://54.178.161.178:8808/approval.php?bid=" + this.bid + "&uid=" + this.uid;
        } else if (paytype == 2) {
            url = "http://54.178.161.178:8088/mc_web.php?bid=" + this.bid + "&uid=" + this.uid;
        } else if (paytype == 3) {
            url = "http://54.178.161.178:8008/order.php?bid=" + this.bid + "&uid=" + this.uid;
        }
        this.webView.loadUrl(url);
    }

    private class MyViewClient extends WebViewClient {
        private MyViewClient() {
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(ActAllatWebView.this.getApplicationContext(), String.valueOf(errorCode) + " / " + description, Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class billWebInterface {

        /* renamed from: com.hotelnow.activities.ActAllatWebView.billWebInterface.1 */
        class C05201 implements Runnable {
            private final /* synthetic */ String val$str;

            /* renamed from: com.hotelnow.activities.ActAllatWebView.billWebInterface.1.1 */
            class C05191 implements DialogInterface.OnClickListener {
                C05191() {
                }

                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    ActAllatWebView.this.finish();
                    ActAllatWebView.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            }

            C05201(String str) {
                this.val$str = str;
            }

            public void run() {
                ActAllatWebView.this.setBookingCancel("");
                HotelUtil.getAlertTheme(ActAllatWebView.this).setTitle("알림").setMessage(this.val$str.length() <= 0 ? "결제를 취소하였습니다." : this.val$str).setCancelable(false).setPositiveButton("닫기", new C05191()).show();
            }
        }

        /* renamed from: com.hotelnow.activities.ActAllatWebView.billWebInterface.2 */
        class C05212 implements Runnable {
            private final /* synthetic */ String val$payid;

            /* renamed from: com.hotelnow.activities.ActAllatWebView.billWebInterface.2.1 */
            class C11341 implements Response.Listener<JSONObject> {
                C11341() {
                }

                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("result").equals("success")) {

//                            Intent intent = new Intent(ActAllatWebView.this, ActMyBookingDetail.class);
//                            intent.putExtra("reservation", true);
//                            intent.putExtra("bid", ActAllatWebView.this.bid);
//                            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(android.app.PendingIntent.FLAG_NO_CREATE);
//                            intent.addFlags(AccessibilityNodeInfoCompat.ACTION_PASTE);
//                            intent.addFlags(android.app.PendingIntent.FLAG_CANCEL_CURRENT);
//                            ActAllatWebView.this.startActivity(intent);
//                            ActAllatWebView.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                            if (!response.has("pay_type")) {
                                Toast.makeText(ActAllatWebView.this.getApplicationContext(), "결제 및 예약이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                CONFIG.hotelObj = null;
                                CONFIG.sel_date = null;
                                CONFIG.sel_keyword = null;
                                CONFIG.sel_area = null;
                                CONFIG.sel_subarea = null;
                                CONFIG.sel_category = null;
                                CONFIG.sel_checkin = null;
                                CONFIG.sel_checkout = null;
                                CONFIG.sel_priceidx = 0;
                            }
                        } else {
                            Toast.makeText(ActAllatWebView.this.getApplicationContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                        ActAllatWebView.this.finish();
                    } catch (JSONException e2) {
                        Toast.makeText(ActAllatWebView.this.getApplicationContext(), "결제를 성공했지만 예약 처리가 되지 않았습니다. 부킹투나잇로 연락 부탁드립니다.", Toast.LENGTH_LONG).show();
                        ActAllatWebView.this.finish();
                        ActAllatWebView.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }
                }
            }

            /* renamed from: com.hotelnow.activities.ActAllatWebView.billWebInterface.2.2 */
            class C11352 implements Response.ErrorListener {
                C11352() {
                }

                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ActAllatWebView.this.getApplicationContext(), "결제는 성공했지만 예약 처리가 되지 않았습니다. 부킹투나잇로 연락 부탁드립니다.", Toast.LENGTH_LONG).show();
                    ActAllatWebView.this.finish();
                    ActAllatWebView.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            }

            C05212(String str) {
                this.val$payid = str;
            }

            public void run() {
                JSONObject params = new JSONObject();
                try {
                    params.put("payid", this.val$payid);
                } catch (JSONException e) {
                }
//                VolleyUtil.getRequestQueue().add(new JsonObjectRequest(1, CONFIG.bookingSuccessUrl, params, new C11341(), new C11352()));
            }
        }

        private billWebInterface() {
        }

        @JavascriptInterface
        public void finishAlert(String str) {
            ActAllatWebView.this.handler.post(new C05201(str));
        }

        @JavascriptInterface
        public void paymentSuccess(String payid) {
            ActAllatWebView.this.handler.post(new C05212(payid));
        }
    }

    private void setBookingCancel(String msg) {
        if (msg == "") {
            msg = "결제를 취소하였습니다.";
        }
        this.cancelMsg = msg;
        JSONObject params = new JSONObject();
        try {
            params.put("bid", this.bid);
        } catch (JSONException e) {
        }
//        VolleyUtil.getRequestQueue().add(new JsonObjectRequest(1, CONFIG.bookingCancelUrl, params, new C11323(), new C11334()));
    }
}
