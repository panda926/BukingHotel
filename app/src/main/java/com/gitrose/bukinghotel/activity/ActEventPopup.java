package com.gitrose.bukinghotel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;

import org.json.JSONObject;

public class ActEventPopup extends BaseActivity {
    private SharedPreferences _preferences;
    private String cookie;
    private JSONObject evtObj;
    private final Handler handler;
    private int idx;
    private String linkUrl;
    private ProgressBar loading_progress;
//    private SimpleFacebook mSimpleFacebook;
    private String obj;
    private String uid;
    private WebView webView;

    /* renamed from: com.hotelnow.activities.ActEventPopup.1 */
    class C05351 extends WebViewClient {
        C05351() {
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.loadUrl("file:///android_asset/404.html");
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (ActEventPopup.this.loading_progress.getVisibility() == View.VISIBLE) {
                ActEventPopup.this.loading_progress.setVisibility(View.INVISIBLE);
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("hotelnowevent://")) {
                try {
                    JSONObject obj = new JSONObject(url.split("otelnowevent://")[1]);
                    if (obj.getString("method").equals("social_open")) {
//                        if (obj.getString("param").equals("kakao")) {
//                            HotelUtil.showKakaoLink(ActEventPopup.this);
//                        } else if (obj.getString("param").equals("facebook")) {
//                            HotelUtil.showFacebookFeed(ActEventPopup.this);
//                        }
                    }
                    if (obj.getString("method").equals("move_hotel")) {
                        String hid = obj.getString("param");
                        Intent intent = new Intent(ActEventPopup.this, ActDealDetail.class);
                        intent.putExtra("hid", hid);
                        ActEventPopup.this.startActivity(intent);
                        ActEventPopup.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_down);
                        ActEventPopup.this.finish();
                    }
                    if (obj.getString("method").equals("move_page")) {
                        String type = obj.getString("param");
                        if (type.equals("account")) {
//                            ActMain.changeActionBar(type);
//                            ((ActMain) ActMain.mContext).replaceFragment("FrgSetting");
                        } else if (type.equals("booking")) {
//                            ActMain.changeActionBar(type);
//                            ((ActMain) ActMain.mContext).replaceFragment("FrgBooking");
                        }
                        ActEventPopup.this.finish();
                        ActEventPopup.this.overridePendingTransition(R.anim.slide_out_up, R.anim.slide_out_down);
                    }
                    if (obj.getString("method").equals("outer_link") && obj.getString("param").contains("hotelnow")) {
                        view.loadUrl(obj.getString("param"));
                    } else if (obj.getString("method").equals("outer_link") && !obj.getString("param").contains("hotelnow")) {
                        ActEventPopup.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj.getString("param"))));
                    }
                } catch (Throwable th) {
                    ActEventPopup.this.finish();
//                    Toast.makeText(ActEventPopup.this.getApplicationContext(), "\uc62c\ubc14\ub978 \ud615\uc2dd\uc758 \uc8fc\uc18c\uac00 \uc544\ub2d9\ub2c8\ub2e4.", Toast.LENGTH_LONG).show();
                }
            } else if (url.contains("plusfriend")) {
//                HotelUtil.kakaoYelloId(ActEventPopup.this);
                ActEventPopup.this.finish();
            } else {
                view.loadUrl(url);
            }
            return true;
        }
    }

    private class DetailInterface {

        /* renamed from: com.hotelnow.activities.ActEventPopup.DetailInterface.1 */
        class C05361 implements Runnable {
            C05361() {
            }

            public void run() {
                ActEventPopup.this.webView.loadUrl(new StringBuilder(String.valueOf(ActEventPopup.this.linkUrl)).append("?uid=").append(ActEventPopup.this.uid).toString());
            }
        }

        private DetailInterface() {
        }

        @JavascriptInterface
        public void reoladDetail() {
            ActEventPopup.this.handler.post(new C05361());
        }
    }

    public ActEventPopup() {
        this.linkUrl = "";
        this.evtObj = null;
        this.handler = new Handler();
        this.uid = "";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.act_event_popup);
//        this.mSimpleFacebook = SimpleFacebook.getInstance(this);
        Intent intent = getIntent();
        this.idx = intent.getIntExtra("idx", 0);
        this.obj = intent.getStringExtra("obj");
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle(getString(R.string.event));
//        getSupportActionBar().setDisplayOptions(16);
//        getSupportActionBar().setCustomView((int) R.layout.actionbar_center);
        ((TextView) findViewById(R.id.title_tv_left)).setText(getString(R.string.event));
        this._preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.cookie = this._preferences.getString("userid", null);
        this.uid = this._preferences.getString("userid", "");
        this.loading_progress = (ProgressBar) findViewById(R.id.loading_progress);
        this.webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " HOTELNOW_APP_ANDROID");

        this.webView.addJavascriptInterface(new DetailInterface(), "DetailInterface");
        this.webView.setWebViewClient(new C05351());
        if (intent != null) {
            try {
                if (this.idx > 0) {
                    this.linkUrl = "http://api.hotelnow.co.kr/popup_event/" + String.valueOf(this.idx) + "?uid=" + this.uid;
                } else {
//                    this.evtObj = new JSONObject(this.obj);
                    this.linkUrl = "http://api.hotelnow.co.kr/popup_event/" + this.obj + "?uid=" + this.uid;
                }
            } catch (Exception e) {
                this.webView.loadUrl("file:///android_asset/404.html");
                e.printStackTrace();
            }
        }
        this.webView.loadUrl(this.linkUrl);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
//            this.mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
        }
    }

    public void onResume() {
        super.onResume();
//        this.mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getSupportMenuInflater().inflate(R.menu.popup_menu, menu);
//        return true;
//    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.close /*2131427734*/:
//                if (this.idx > 0) {
//                    Intent intentReserve = new Intent(this, ActMain.class);
//                    intentReserve.addFlags(67108864);
//                    intentReserve.addFlags(DriveFile.MODE_WRITE_ONLY);
//                    intentReserve.addFlags(AccessibilityNodeInfoCompat.ACTION_PASTE);
//                    intentReserve.addFlags(DriveFile.MODE_READ_ONLY);
//                    startActivity(intentReserve);
//                } else {
//                    finish();
//                    overridePendingTransition(R.anim.slide_out_up, R.anim.slide_out_down);
//                }
//                return true;
//            default:
//                return false;
//        }
//    }

    public void onBackPressed() {
        if (this.idx > 0) {
//            Intent intentReserve = new Intent(this, ActMain.class);
//            intentReserve.addFlags(67108864);
//            intentReserve.addFlags(DriveFile.MODE_WRITE_ONLY);
//            intentReserve.addFlags(AccessibilityNodeInfoCompat.ACTION_PASTE);
//            intentReserve.addFlags(DriveFile.MODE_READ_ONLY);
//            startActivity(intentReserve);
        } else {
            finish();
            overridePendingTransition(R.anim.slide_out_up, R.anim.slide_out_down);
        }
        super.onBackPressed();
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    protected void onPause() {
        super.onPause();
    }
}
