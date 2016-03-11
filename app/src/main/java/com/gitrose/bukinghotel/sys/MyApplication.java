package com.gitrose.bukinghotel.sys;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.*;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

/**
 * Created by GITRose on 1/18/2016.
 */
public class MyApplication extends Application {

    private static MyApplication app;
    private RequestQueue mRequestQueue;
    public DefaultHttpClient mHttpClient;
    public boolean isFirstApp;
    public boolean isPassAuth;
    private boolean isfirstalert;

    public MyApplication()
    {
        this.isfirstalert = true;
        this.isFirstApp = false;
        this.isPassAuth = false;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        if(this.getCurProcessName(this) == null || getCurProcessName(this).equals("com.gitrose.bukinghotel"))
        {
            app = this;

            if (this.mRequestQueue == null) {
                DefaultHttpClient mDefaultHttpClient = new DefaultHttpClient();
                ClientConnectionManager mClientConnectionManager = mDefaultHttpClient.getConnectionManager();
                HttpParams mHttpParams = mDefaultHttpClient.getParams();
                this.mHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(mHttpParams, mClientConnectionManager.getSchemeRegistry()), mHttpParams);
                this.mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HttpClientStack(this.mHttpClient));
            }
        }
    }

    public RequestQueue getmRequestQueue() {
        return this.mRequestQueue;
    }

    public static MyApplication getInstance() {
        return app;
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo appProcess : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public boolean isIsfirstalert() {
        return this.isfirstalert;
    }

    public void setIsfirstalert(boolean isfirstalert) {
        this.isfirstalert = isfirstalert;
    }
}
