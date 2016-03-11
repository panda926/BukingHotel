package com.gitrose.bukinghotel.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GITRose on 1/18/2016.
 */
public class UserAuth {

    private static UserAuth userAuth;

    private UserAuth() {
    }

    public static UserAuth getInstance() {
        if (userAuth == null) {
            synchronized (UserAuth.class) {
                if (userAuth == null) {
                    userAuth = new UserAuth();
                }
            }
        }
        return userAuth;
    }

    public boolean authUser(Context context) {

        SharedPreferences sp = context.getSharedPreferences(Constant.PREFENCES_NAME, 0);
        String uid = sp.getString(Constant.UID, null);
        if (uid == null) {
            return false;
        }

        return true;
    }
}
