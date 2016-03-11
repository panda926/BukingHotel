package com.gitrose.bukinghotel.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Base64;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by GITRose on 1/19/2016.
 */
public class HotelUtil {

    public static Location userLocation;

    public static int getListHeight(Context context) {
        SharedPreferences _preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (_preferences.getFloat("list_height", 0.0f) <= 0.0f) {
            return Math.round((float) ((context.getResources().getDisplayMetrics().widthPixels / 16) * 9));
        }
        return (int) _preferences.getFloat("list_height", 0.0f);
    }

    public static String number_format(int num) {
        return NumberFormat.getNumberInstance().format((long) num);
    }

    public static long diffOfDate(String begin, String end) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        long diffDays = 0;
        try {
            return (formatter.parse(end).getTime() - formatter.parse(begin).getTime()) / 86400000;
        } catch (ParseException e) {
            e.printStackTrace();
            return diffDays;
        }
    }

    public static String stringToHTMLString(String string) {
        return string.replace("&lt;", "<").replace("&gt;", "<").replace("&amp;", "<").replace("&quot;", "\"").replace("&nbsp;", " ");
    }

    public static String GetToday()
    {
        Calendar c = Calendar.getInstance();
        String date = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return date;
    }

    public static String GetTodayDate()
    {
        Calendar c = Calendar.getInstance();
        String date = String.valueOf(c.getTime().getDate());

        return date;
    }

    public static String GetTomorrowDate()
    {
        Calendar c = Calendar.getInstance();
        c.add(c.DATE, 1);
        String date = String.valueOf(c.getTime().getDate());

        return date;
    }

    public static String GetTomorrow()
    {
        Calendar c = Calendar.getInstance();
        c.add(c.DATE, 1);
        String date = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return date;
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static boolean chkLocationService(Context context) {
        LocationManager lm = null;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        if (null == null) {
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            gps_enabled = lm.isProviderEnabled("gps");
        } catch (Exception e) {
        }
        try {
            network_enabled = lm.isProviderEnabled("network");
        } catch (Exception e2) {
        }
        if (gps_enabled || network_enabled) {
            return true;
        }
        return false;
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static void setGcmToken(Context context, String regId, String userId, Boolean flag) {
//        String androidId = getAndroidId(context);
//        JSONObject params = new JSONObject();
//        try {
//            params.put(StaticValues.PARAM_OS, "a");
//            params.put(StaticValues.PARAM_UUID, androidId);
//            params.put("push_token", regId);
//            params.put("ver", getAppVersionName(context));
//            if (flag != null) {
//                params.put("use_yn", flag.booleanValue() ? WiseTracker.MEMBER : WiseTracker.NON_MEMBER);
//            }
//            if (userId != null) {
//                params.put("user_id", userId);
//            }
//        } catch (JSONException e) {
//        }
//        VolleyUtil.getRequestQueue().add(new JsonObjectRequest(1, CONFIG.notiSettingUrl, params, new C11912(), new C11923()));
    }

    public static void kakaoYelloId(Activity activity) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("kakaoplus://plusfriend/friend/@%ED%98%B8%ED%85%94%EB%82%98%EC%9A%B0"));
            intent.setFlags(805306368);
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.kakao.talk")));
            } catch (ActivityNotFoundException e2) {
                activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=com.kakao.talk")));
            }
        }
    }

    public static String getUserAgent(Context context) {
        return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf("Android " + Build.VERSION.RELEASE + "(SDK-" + String.valueOf(Build.VERSION.SDK_INT) + ");")).append(context.getResources().getConfiguration().locale).append(";").toString())).append(Build.MODEL).append(";").append(Build.MANUFACTURER).append(";").append(Build.PRODUCT).toString();
    }

    public static void captchaImgUrl(Context context, ImageView captcha) {

        QGHttpRequest.getInstance().GetCaptureImage(context, new GetCaptureImageResult(context, captcha));
    }

    static class GetCaptureImageResult extends QGHttpHandler<String>
    {
        private final ImageView imgCaptchaView;
        private final Context mContext;
        public GetCaptureImageResult(Context context, ImageView imgview) {
            super(context);
            mContext = context;
            imgCaptchaView = imgview;
        }

        @Override
        public void onGetDataSuccess(String s) {
            try {
                JSONObject response = new JSONObject(s);
                byte[] decodedString = Base64.decode(response.getJSONArray("data").getJSONObject(0).getString("captcha"), 0);
                imgCaptchaView.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this.mContext, this.mContext.getString(R.string.error_capcha_img), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Toast.makeText(this.mContext, this.mContext.getString(R.string.error_capcha_img), Toast.LENGTH_LONG).show();
        }
    }

    public static void showMsgAlert(Context context, String msg) {
        getAlertTheme(context).setTitle(context.getString(R.string.alert_notice)).setMessage(msg).setPositiveButton(context.getString(R.string.alert_close), new MsgDlgClick()).show();
    }

    static class MsgDlgClick implements DialogInterface.OnClickListener {
        MsgDlgClick() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
        }
    }

    public static AlertDialog.Builder getAlertTheme(Context context) {
        if (Build.VERSION.SDK_INT >= 11) {
            return new AlertDialog.Builder(context, 2);
        }
        return new AlertDialog.Builder(context);
    }
}
