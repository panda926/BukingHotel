package com.gitrose.bukinghotel.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DailyPreference {
    private static DailyPreference f3721a;
    private SharedPreferences f3722b;
    private Editor f3723c;

    private DailyPreference(Context context) {
        this.f3722b = context.getSharedPreferences("dailyHOTEL_v1", 0);
        this.f3723c = this.f3722b.edit();
    }

    public static synchronized DailyPreference getInstance(Context context) {
        DailyPreference dailyPreference;
        synchronized (DailyPreference.class) {
            if (f3721a == null) {
                f3721a = new DailyPreference(context);
            }
            dailyPreference = f3721a;
        }
        return dailyPreference;
    }

    public void clear() {
        if (this.f3723c != null) {
            this.f3723c.clear();
            this.f3723c.apply();
        }
    }

    public boolean getEnabledOpeningAlarm() {
        if (this.f3722b != null) {
            return this.f3722b.getBoolean("1", false);
        }
        return false;
    }

    public void setEnabledOpeningAlarm(boolean z) {
        if (this.f3723c != null) {
            this.f3723c.putBoolean("1", z);
            this.f3723c.apply();
        }
    }

    public boolean isNewTodayFnB() {
        if (this.f3722b != null) {
            return this.f3722b.getBoolean("2", true);
        }
        return false;
    }

    public void setNewTodayFnB(boolean z) {
        if (this.f3723c != null) {
            this.f3723c.putBoolean("2", z);
            this.f3723c.apply();
        }
    }

    public String getLastMenu() {
        if (this.f3722b != null) {
            return this.f3722b.getString("3", null);
        }
        return null;
    }

    public void setLastMenu(String str) {
        if (this.f3723c != null) {
            this.f3723c.putString("3", str);
            this.f3723c.apply();
        }
    }

    public boolean isShowGuide() {
        if (this.f3722b != null) {
            return this.f3722b.getBoolean("4", false);
        }
        return false;
    }

    public void setShowGuide(boolean z) {
        if (this.f3723c != null) {
            this.f3723c.putBoolean("4", z);
            this.f3723c.apply();
        }
    }
}
