package com.gitrose.bukinghotel.util;

/**
 * Created by GITRose on 1/19/2016.
 */
import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyUtil {
    private static RequestQueue mRequestQueue;

    private VolleyUtil() {
    }

    static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        }
        throw new IllegalStateException("RequestQueue not initialized");
    }
}
