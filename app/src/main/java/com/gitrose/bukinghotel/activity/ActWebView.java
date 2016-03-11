package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.util.HotelUtil;

public class ActWebView extends BaseActivity {

    private final Handler handler;
    private String linkTitle;
    private String linkUrl;
    private ProgressBar loading_progress;
    private WebView webView;

    public ActWebView() {
        this.webView = null;
        this.handler = new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_web_view);

        this.loading_progress = (ProgressBar) findViewById(R.id.loading_progress);
        Intent intent = getIntent();
        if (intent != null) {
            this.linkUrl = intent.getStringExtra("url");
            this.linkTitle = intent.getStringExtra("title");
//            getSupportActionBar().setTitle(this.linkTitle);
        }
        this.webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " HOTELNOW_APP_ANDROID");

        this.webView.addJavascriptInterface(new DetailInterface(), "DetailInterface");
        this.webView.setWebViewClient(new CustomeWebView());
        this.webView.loadUrl(this.linkUrl);
    }

    class CustomeWebView extends WebViewClient {
        CustomeWebView() {
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.loadUrl("file:///android_asset/404.html");
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (ActWebView.this.loading_progress.getVisibility() == View.VISIBLE) {
                ActWebView.this.loading_progress.setVisibility(View.INVISIBLE);
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("plusfriend")) {
                HotelUtil.kakaoYelloId(ActWebView.this);
                ActWebView.this.finish();
                return true;
            } else if (url.startsWith("tel:")) {
                ActWebView.this.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(url)));
                return true;
            } else if (url.startsWith("mailto:")) {
                ActWebView.this.startActivity(new Intent("android.intent.action.SENDTO", Uri.parse(url)));
                return true;
            } else {
                view.loadUrl(url);
                return false;
            }
        }
    }

    private class DetailInterface {

        class WebViewHandler implements Runnable {
            WebViewHandler() {
            }

            public void run() {
                ActWebView.this.webView.loadUrl(ActWebView.this.linkUrl);
            }
        }

        private DetailInterface() {
        }

        @JavascriptInterface
        public void reoladDetail() {
            ActWebView.this.handler.post(new WebViewHandler());
        }
    }
}
