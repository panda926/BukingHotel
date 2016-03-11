package com.gitrose.bukinghotel.http;

import android.content.Context;

import com.gitrose.bukinghotel.adapter.ArrayMainList;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class QGHttpRequest {
    private static QGHttpRequest mInstance;

    public static QGHttpRequest getInstance() {
        if (mInstance == null) {
            mInstance = new QGHttpRequest();
        }
        return mInstance;
    }

    public void getMainList(Context context, String strFromDate, String strToDate, String strOrder, QGHttpHandler<String> handler) {

        HashMap<String, String> params = new HashMap();
        params.put("sdate", strFromDate);
        params.put("edate", strToDate);
        params.put("order", strOrder);

        QGClient.getInstance().post(context, CONFIG.mainListUrl, params, handler);
    }

    public void getDetailInfo(Context context, String strHid, String strURL, String pid, String evt, String ecdate, String eedate, QGHttpHandler<String> handler){

        HashMap<String, String> params = new HashMap();
//        params.put("pid", pid);
//        params.put("evt", evt);
//        if(!(ecdate == null || eedate == null)) {
//            params.put("ec_date", ecdate);
//            params.put("ee_date", eedate);
//            params.put("consecutive", "Y");
//        }

        params.put("ids", strHid);

        QGClient.getInstance().post(context, strURL, params, handler);
    }

    public void getReserveHistory(Context context, String strUerID, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", strUerID);
        QGClient.getInstance().post(context, CONFIG.BookingListURL, params, handler);
    }

    public void getCouponList(Context context, String strUserID, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", strUserID);
        QGClient.getInstance().post(context, CONFIG.promotionListUrl, params, handler);
    }

    public void GetUserStatus(Context context, String strAndroidID, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("uuid", strAndroidID);
        QGClient.getInstance().get(context, CONFIG.notiStatusUrl, params, handler);
    }

    public void GetHotelDetailImage(Context context, String strHId, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("ht_id", strHId);
        QGClient.getInstance().post(context, CONFIG.PORTRATE_URL, params, handler);
    }

    public void UserLogin(Context context, String email, String password, String ver, String useragent, String uuid, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", email);
        params.put("mb_password", password);
//        params.put("ver", ver);
//        params.put("useragent", useragent);
//        params.put("uuid", uuid);
        QGClient.getInstance().post(context, CONFIG.loginUrl, params, handler);
    }

    public void GetCaptureImage(Context context, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        QGClient.getInstance().get(context, CONFIG.captchaUrl, params, handler);
    }

    public void GetDealInfo(Context context, String strPID, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("pid", strPID);
        QGClient.getInstance().post(context, CONFIG.ReserveURL, params, handler);
    }

    public void AuthCheck(Context context, String strUserId, String strUserMoreInfo, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("ui", strUserId);
        params.put("umi", strUserMoreInfo);
        QGClient.getInstance().post(context, CONFIG.authcheckUrl, params, handler);
    }

    public void GetCouponInfo(Context context, String strCode, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("pcode", strCode);
        QGClient.getInstance().post(context, CONFIG.promotionUrl, params, handler);
    }

    public void BookingReserve(Context context, HashMap<String, String> params, QGHttpHandler<String> handler)
    {
        QGClient.getInstance().post(context, CONFIG.bookingReserveUrl, params, handler);
    }

    public void RegistorRequest(Context context, String strId,String strEmailAddress, String strPassword,String strName, String strPhone, String strRecommend, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", strId);
        params.put("mb_email", strEmailAddress);
        params.put("mb_password", strPassword);
        params.put("mb_name", strName);
        params.put("mb_tel", strPhone);
        params.put("mb_nick", strRecommend);
        QGClient.getInstance().post(context, CONFIG.REGITOR_URL, params, handler);
    }

    public void FindPassRequest(Context context, String stremail, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_email", stremail);
        QGClient.getInstance().post(context, CONFIG.FIND_PASS_SERCH, params, handler);
    }
}
