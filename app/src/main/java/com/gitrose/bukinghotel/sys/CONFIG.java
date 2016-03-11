package com.gitrose.bukinghotel.sys;

import org.json.JSONObject;

public class CONFIG {

//    public static final String strGetMainList = "http://api.hotelnow.co.kr/products/1/999";
    public static final String strGetMainList = "http://121.78.66.29/bktonight/api/bt_hotel_list.php";
    public static final String REGITOR_URL = "http://121.78.66.29/bktonight/api/bt_user_register.php";
    public static final String PORTRATE_URL = "http://121.78.66.29/bktonight/api/ht_hotel_img_list.php";
    public static final String loginUrl = "http://121.78.66.29/bktonight/api/bt_login.php";
    public static final String ReserveURL = "http://121.78.66.29/bktonight/api/ht_booking_info.php";
    public static final String FIND_PASS_SERCH     =  "http://121.78.66.29/bktonight/api/bt_find_pass.php";
    public static final String promotionListUrl = "http://121.78.66.29/bktonight/api/user_coupon.php";
    public static final String BookingListURL = "http://121.78.66.29/bktonight/api/bt_booking_list.php";

    public static final String accountNumber = "\uc6b0\ub9ac\uc740\ud589 1005-302-209367";
    public static final String agreeUrl = "http://api.hotelnow.co.kr/terms";
    public static final String areaCntUrl = "http://api.hotelnow.co.kr/product/area_cnt";
    public static final String arsDomain = "http://54.178.161.178:8008";
    public static final String arsIndex = "http://54.178.161.178:8008/order.php";
    public static final String authcheckUrl = "http://api.hotelnow.co.kr/auth_checking";
    public static final String billDomain = "http://54.178.161.178:8808";
    public static final String billIndex = "http://54.178.161.178:8808/approval.php";
    public static final String bookingCancelUrl = "http://api.hotelnow.co.kr/booking/cancel";
    public static final String bookingDetailUrl = "http://api.hotelnow.co.kr/booking/detail";
    public static final String bookingHidelUrl = "http://api.hotelnow.co.kr/booking/hide";
    public static final String bookingListUrl = "http://api.hotelnow.co.kr/booking/list";
    public static final String bookingReserveUrl = "http://api.hotelnow.co.kr/booking/reservation";
    public static final String bookingSuccessUrl = "http://api.hotelnow.co.kr/booking/confirm";
    public static final String captchaUrl = "http://api.hotelnow.co.kr/captcha";
    public static final String csPhoneNum = "070-4481-5541";
    public static int default_reserve_money = 0;
    public static final String detailUrl = "http://api.hotelnow.co.kr/product_detail";
    public static final String detailWebUrl = "http://api.hotelnow.co.kr/product_detail_web";
    public static final String domain = "http://api.hotelnow.co.kr";
    public static final String domainssl = "https://api.hotelnow.co.kr";
    public static final String eventApplyUrl = "http://api.hotelnow.co.kr/booking/event";
    public static final String eventWebUrl = "http://api.hotelnow.co.kr/popup_event";
    public static final String favoriteListUrl = "http://api.hotelnow.co.kr/zzim/list";
    public static final String favoriteUrl = "http://api.hotelnow.co.kr/zzim/save";
    public static final String fontPath = "fonts/nanumgothic.ttf";
    public static final String googleProjectid = "369003584286";
    public static JSONObject hotelObj = null;
    public static final String info_provide1 = "http://api.hotelnow.co.kr/terms_booking/booking";
    public static final String info_provide2 = "http://api.hotelnow.co.kr/terms_booking/thirdparty";
    public static final String kimgisaKey = "c1be8498949d40bdb9b9fa38acd54737";
    public static final String loadingUrl = "http://api.hotelnow.co.kr/config";
    public static final String locationUrl = "http://api.hotelnow.co.kr/product/location";
//    public static final String loginUrl = "https://api.hotelnow.co.kr/login";
    public static final String logoutUrl = "https://api.hotelnow.co.kr/logout";
    public static final String mainListUrl = "http://api.hotelnow.co.kr/products";
    public static final String marketUrl = "http://www.hotelnow.co.kr/market";
    public static final String newrelicKey = "AAb598d6751668162b9954ab68b628a549d1b3d0e7";
    public static final String notiSettingUrl = "http://api.hotelnow.co.kr/notification/setting";
    public static final String notiStatusUrl = "http://api.hotelnow.co.kr/notification/status";
    public static String open_sell_time = null;
    public static final String passresetUrl = "https://api.hotelnow.co.kr/password/remind";
    public static final String phonePayDomain = "http://54.178.161.178:8088";
    public static final String phonePayIndex = "http://54.178.161.178:8088/mc_web.php";
    public static final String portraitUrl = "http://api.hotelnow.co.kr/product_big_image";
//    public static final String promotionListUrl = "http://api.hotelnow.co.kr/promotion/list";
    public static final String promotionUrl = "http://api.hotelnow.co.kr/promotion/receive";
    public static final String recommendCheckUrl = "http://api.hotelnow.co.kr/recommendation/check";
    public static final String recommendSaveUrl = "http://api.hotelnow.co.kr/recommendation/save";
    public static boolean reloadFlag = false;
    public static final String reserveUrl = "http://api.hotelnow.co.kr/product/available";
    public static final String reservemoneyUrl = "http://api.hotelnow.co.kr/recommendation/list";
    public static final String reviewCreateUrl = "http://api.hotelnow.co.kr/review/create";
    public static boolean saleAvailable = false;
    public static String sel_area = null;
    public static String sel_category = null;
    public static String sel_checkin = null;
    public static String sel_checkout = null;
    public static String sel_date = null;
    public static String sel_keyword = null;
    public static int sel_priceidx = 0;
    public static String sel_subarea = null;
    public static final String setting_agree1 = "http://121.78.66.29/bktonight/html_service.php";
    public static final String setting_agree2 = "http://121.78.66.29/bktonight/html_privacy.php";
    public static final String setting_agree3 = "http://121.78.66.29/bktonight/html_provision.php";
    public static final String setting_email = "cs@hotelnow.co.kr";
    public static final String setting_facebook = "https://www.facebook.com/hotelnowkr";
    public static final String setting_notice = "http://hotelnowapp.blogspot.kr/";
    public static final String signoutUrl = "https://api.hotelnow.co.kr/signout";
    public static final String signupUrl = "https://api.hotelnow.co.kr/signup";
    public static String svr_time;
    public static boolean useCaption;
    public static boolean useLocation;

    public static final String strFAQ = "http://121.78.66.29/bktonight/html_faq.php";

    static {
        useLocation = false;
        reloadFlag = false;
        useCaption = true;
        saleAvailable = true;
        default_reserve_money = 5000;
        hotelObj = null;
        sel_priceidx = 0;
        svr_time = null;
        open_sell_time = "30";
    }
}
