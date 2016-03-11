package com.gitrose.bukinghotel.util;

import android.content.Context;
import android.os.PowerManager;

public class WakeLock {
    private static PowerManager.WakeLock f3740a;

    public static void acquireWakeLock(Context context, int i) {
        try {
            f3740a = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(i, context.getClass().getName());
            f3740a.acquire();
        } catch (Exception e) {
//            ExLog.m2544d(e.toString());
        }
    }

    public static void releaseWakeLock() {
        try {
            if (f3740a != null) {
                f3740a.release();
                f3740a = null;
            }
        } catch (Exception e) {
//            ExLog.m2544d(e.toString());
        }
    }
}
