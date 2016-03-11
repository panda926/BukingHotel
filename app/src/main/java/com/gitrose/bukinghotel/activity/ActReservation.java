package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;
import com.gitrose.bukinghotel.view.DialogBookingCaution;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ActReservation extends BaseActivity {

    private SharedPreferences _preferences;
    private CheckBox agree_policy;
    private DialogBookingCaution bdialog;
    private Button btn_go_payment;
    private Spanned cancel_fee;
    private String cancel_fee_str;
    private String category;
    private TextView caution_txt;
    private TextView check_id_date;
    private TextView check_out_date;
    private String checkin_date;
    private Map<Integer, String> coupon_arr;
    private ArrayList<Integer> coupon_price;
    private Spinner coupon_spinner;
    private TextView coupon_txt;
    private int coupon_value;
    private String ec_date;
    private String ee_date;
    private Boolean flag_btn_clicked;
    private String hid;
    private String hotel_name;
    private NumberFormat nf;
    private View.OnClickListener okClickListener;
    private int paytype;
    private RadioButton paytype3;
    private RadioGroup paytypes;
    private String pcode;
    private String pid;
    private Spinner pnum1;
    private EditText pnum2;
    private EditText pnum3;
    private ImageView res_room_img;
    private ArrayList<String> reserve_arr;
    private int reserve_money;
    private Spinner reserve_money_spinner;
    private TextView reserve_money_txt;
    private int reserve_value;
    private String rid;
    private Boolean sale_available;
    private int sale_price;
    private int selectdCouponIdx;
    private int selectdReserveIdx;
    private View.OnClickListener skipClickListener;
    private LinearLayout sub_products;
    private TextView txt_sale_price;
    private EditText usernameInput;
    private RelativeLayout wrapper;

    public ActReservation() {
        this.flag_btn_clicked = Boolean.valueOf(false);
        this.sale_available = Boolean.valueOf(false);
        this.reserve_money = 0;
        this.nf = NumberFormat.getNumberInstance();
        this.sale_price = 0;
        this.paytype = 0;
        this.reserve_value = 0;
        this.coupon_value = 0;
        this.pcode = "0";
        this.selectdReserveIdx = 0;
        this.selectdCouponIdx = 0;
        this.skipClickListener = new OnSkipClick();
        this.okClickListener = new OnOkClick();
    }

    class OnOkClick implements View.OnClickListener {
        OnOkClick() {
        }

        public void onClick(View v) {
            ActReservation.this.goPayment();
            ActReservation.this.bdialog.dismiss();
        }
    }

    class OnSkipClick implements View.OnClickListener {
        OnSkipClick() {
        }

        public void onClick(View v) {
            ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
            ActReservation.this.bdialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_reservation);

        this.paytype3 = (RadioButton) findViewById(R.id.paytype3);
        this.paytype3.setText(Html.fromHtml(getString(R.string.paytype3) + " <small><font color=\"red\">법인, 일반 카드 결제 가능(해외카드 제외)</font></small>"));
        this.wrapper = (RelativeLayout) findViewById(R.id.wrapper);
        this.pnum1 = (Spinner) findViewById(R.id.pnum1);
        this._preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.btn_go_payment = (Button) findViewById(R.id.btn_go_payment);
        this.res_room_img = (ImageView) findViewById(R.id.res_room_img);
        this.res_room_img.getLayoutParams().height = HotelUtil.getListHeight(this);
        this.res_room_img.setScaleType(ImageView.ScaleType.FIT_XY);
        this.sub_products = (LinearLayout) findViewById(R.id.sub_products);
        this.pid = getIntent().getStringExtra("pid");
        this.ec_date = CONFIG.sel_checkin;
        this.ee_date = CONFIG.sel_checkout;
        this.paytypes = (RadioGroup) findViewById(R.id.paytypes);
        getDealInfo();
    }

    private void getDealInfo() {
        String url;
//        if (this.ec_date == null || this.ee_date == null) {
//            url = "http://api.hotelnow.co.kr/product/available/" + this.pid;
//        } else {
//            url = "http://api.hotelnow.co.kr/product/available/" + this.pid + "?ec_date=" + this.ec_date + "&ee_date=" + this.ee_date + "&consecutive=Y";
//        }

        QGHttpRequest.getInstance().GetDealInfo(this, this.pid, new GetDealInfo(this));
    }

    class GetDealInfo extends QGHttpHandler<String>
    {

        public GetDealInfo(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {

            try {
                ActReservation.this.SetDealInfo(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Toast.makeText(ActReservation.this.getApplicationContext(), ActReservation.this.getString(R.string.error_product_user_info), Toast.LENGTH_LONG).show();
        }
    }

    private void SetDealInfo(String strResult) throws ParseException {
        try {

            JSONObject response = new JSONObject(strResult);
            JSONObject info = response.getJSONObject("data");
            ActReservation.this.wrapper.setVisibility(View.GONE);
            ActReservation.this.hotel_name = info.getString("hotel_name");
            ActReservation.this.sale_available = info.getBoolean("sale_available");
            String str2 = info.getString("room_name");
            String str3 = info.getString("room_img");
            ActReservation.this.checkin_date = info.getString("checkin_date");
            String str1 = info.getString("checkout_date");
            ActReservation.this.hid = info.getString("hotel_id");
            ActReservation.this.rid = info.getString("rid");
            ActReservation.this.category = info.getString("category");
            ActReservation.this.reserve_money = info.getInt("reserve_money");
            int j = info.getInt("normal_price");
            ActReservation.this.sale_price = info.getInt("sale_price");
            int k = ActReservation.this.sale_price;
            SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat localSimpleDateFormat3 = new SimpleDateFormat(ActReservation.this.getString(R.string.year_month_day_weekday_format3), Locale.KOREAN);
            SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat(ActReservation.this.getString(R.string.year_month_day_weekday_format2), Locale.KOREAN);
            JSONArray localJSONArray;
            Typeface localTypeface;
            if (info.has("products"))
            {
                localJSONArray = info.getJSONArray("products");
                localTypeface = Typeface.createFromAsset(ActReservation.this.getAssets(), "fonts/nanumgothic.ttf");

                for(int i = 0; i < localJSONArray.length(); i++) {
                    JSONObject localJSONObject = localJSONArray.getJSONObject(i);
                    String strDate = localSimpleDateFormat3.format(localSimpleDateFormat1.parse(localJSONObject.getString("checkin_date")));
                    Object localObject1 = "<font color='gray'>" + strDate + "</font>";
                    JSONObject objJSon = (JSONObject)localObject1;
                    if (localJSONObject.getInt("breakfast") > 0) {
                        strDate = localObject1 + "<font color='gray'>(" + localJSONObject.getString("breakfast") + "인 조식)</font>";
                    }

                    strDate = strDate + "<font color='black'><b>" + HotelUtil.number_format(localJSONObject.getInt("sale_price")) + "원</b></font>";
                    TextView tvTemp = new TextView(ActReservation.this);
                    ((TextView)tvTemp).setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                    ((TextView)tvTemp).setPadding(0, 5, 0, 5);
                    ((TextView)tvTemp).setTextSize(13.0F);
                    ((TextView)tvTemp).setTypeface(localTypeface);
                    ((TextView)tvTemp).setText(Html.fromHtml(strDate));
                    ActReservation.this.sub_products.addView((View)tvTemp);
                }
            }

//            for(int i = 0 ; i <= 5000; i+=1000)
//                ActReservation.this.reserve_arr.add(ActReservation.this.nf.format(i) + "원");

            if (!ActReservation.this.sale_available)
            {
                ActReservation.this.btn_go_payment.setBackgroundResource(R.drawable.button_round_reserve_disable);
                ActReservation.this.btn_go_payment.setText(ActReservation.this.getString(R.string.sell_complete));
            }

            ((TextView)ActReservation.this.findViewById(R.id.res_hotel_name)).setText(ActReservation.this.hotel_name);
            ((TextView)ActReservation.this.findViewById(R.id.res_room_name)).setText(str2);
            UrlImageViewHelper.setUrlDrawable(ActReservation.this.res_room_img, str3);
            ActReservation.this.check_id_date = ((TextView)ActReservation.this.findViewById(R.id.check_id_date));
            ActReservation.this.check_out_date = ((TextView)ActReservation.this.findViewById(R.id.check_out_date));

            ActReservation.this.check_id_date.setText(localSimpleDateFormat2.format(localSimpleDateFormat1.parse(ActReservation.this.checkin_date)));
            ActReservation.this.check_out_date.setText((CharSequence)localSimpleDateFormat2.format(localSimpleDateFormat1.parse(str1)));
            ActReservation.this.cancel_fee_str = info.getString("fee");
            ActReservation.this.cancel_fee_str = (ActReservation.this.cancel_fee_str + "\n\n- <font color=#ff0000>체크아웃</font> : " + ActReservation.this.check_out_date.getText().toString());
            ActReservation.this.cancel_fee = Html.fromHtml(ActReservation.this.cancel_fee_str.replaceAll("\n", "<br>"));
            ActReservation.this.usernameInput = ((EditText)ActReservation.this.findViewById(R.id.username));
            ActReservation.this.pnum2 = ((EditText)ActReservation.this.findViewById(R.id.pnum2));
            ActReservation.this.pnum3 = ((EditText)ActReservation.this.findViewById(R.id.pnum3));

            if (ActReservation.this.reserve_money > 0)
            {
                ((LinearLayout)ActReservation.this.findViewById(R.id.reserve_layout)).setVisibility(View.VISIBLE);
                ActReservation.this.reserve_money_txt = ((TextView)ActReservation.this.findViewById(R.id.reserve_money_txt));
                ActReservation.this.reserve_money_txt.setText(ActReservation.this.reserve_money_txt.getText() + "(" + ActReservation.this.nf.format(ActReservation.this.reserve_money) + " 원)");
                ActReservation.this.reserve_money_spinner = ((Spinner)ActReservation.this.findViewById(R.id.reserve_money_use));
                ActReservation.this.reserve_arr = new ArrayList();
                ActReservation.this.reserve_arr.add("0원");

                if (ActReservation.this.sale_price < ActReservation.this.reserve_money) {
                    int i = 1000;
                    while (i <= ActReservation.this.reserve_money && i <= 50000) {
                        ActReservation.this.reserve_arr.add(ActReservation.this.nf.format((long) i) + "원");
                        i += 1000;
                    }
                }
                else {
                    int i = 1000;
                    while (i <= ActReservation.this.sale_price && i <= 50000) {
                        ActReservation.this.reserve_arr.add(ActReservation.this.nf.format((long) i) + "원");
                        i += 1000;
                    }
                }

                ActReservation.this.reserve_money_spinner.setAdapter(new ArrayAdapter(ActReservation.this, android.R.layout.simple_spinner_dropdown_item, ActReservation.this.reserve_arr));
                ActReservation.this.reserve_money_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> paramAnonymous2AdapterView, View paramAnonymous2View, int paramAnonymous2Int, long paramAnonymous2Long) {
                        ActReservation.this.reserve_value = Integer.valueOf(((String) ActReservation.this.reserve_arr.get(paramAnonymous2Int)).replace(",", "").replace("원", "").replace("-", "")).intValue();
                        ActReservation.this.selectdReserveIdx = paramAnonymous2Int;
                        paramAnonymous2Int = ActReservation.this.sale_price - ActReservation.this.reserve_value - ActReservation.this.coupon_value;
                        if (paramAnonymous2Int < 0) {
                            int i = (int) Math.ceil(Math.abs(paramAnonymous2Int) / 1000.0D);
                            ActReservation.this.reserve_money_spinner.setSelection(ActReservation.this.selectdReserveIdx - i);
                            ActReservation.this.txt_sale_price.setText(ActReservation.this.getString(R.string.price, new Object[]{ActReservation.this.nf.format(ActReservation.this.sale_price - Math.abs(paramAnonymous2Int))}));
                            return;
                        }
                        ActReservation.this.txt_sale_price.setText(ActReservation.this.getString(R.string.price, new Object[]{ActReservation.this.nf.format(paramAnonymous2Int)}));
                    }

                    public void onNothingSelected(AdapterView<?> paramAnonymous2AdapterView) {
                    }
                });
            }

            if (info.getJSONArray("promotion_code").length() > 0)
            {
                ((LinearLayout)ActReservation.this.findViewById(R.id.coupon_layout)).setVisibility(View.VISIBLE);
                ActReservation.this.coupon_txt = ((TextView)ActReservation.this.findViewById(R.id.coupon_txt));
                ActReservation.this.coupon_txt.setText(ActReservation.this.coupon_txt.getText() + "(" + ActReservation.this.nf.format(info.getJSONArray("promotion_code").length()) + " 장)");
                ActReservation.this.coupon_spinner = ((Spinner)ActReservation.this.findViewById(R.id.coupon_use));
                ActReservation.this.coupon_arr = new HashMap();
                ActReservation.this.coupon_price = new ArrayList();
                ActReservation.this.coupon_arr.put(Integer.valueOf(0), "0");
                ActReservation.this.coupon_price.add(Integer.valueOf(0));

                List arr = new ArrayList();
                arr.add("사용안함");
                ActReservation.this.coupon_spinner.setAdapter(new ArrayAdapter(ActReservation.this, android.R.layout.simple_spinner_dropdown_item, arr));
                ActReservation.this.coupon_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> paramAnonymous2AdapterView, View paramAnonymous2View, int paramAnonymous2Int, long paramAnonymous2Long) {
                        ActReservation.this.coupon_value = ((Integer) ActReservation.this.coupon_price.get(paramAnonymous2Int)).intValue();
                        ActReservation.this.pcode = ((String) ActReservation.this.coupon_arr.get(Integer.valueOf(paramAnonymous2Int)));
                        ActReservation.this.selectdCouponIdx = paramAnonymous2Int;
                        paramAnonymous2Int = ActReservation.this.sale_price - ActReservation.this.reserve_value - ActReservation.this.coupon_value;
                        if (paramAnonymous2Int < 0) {
                            int i = (int) Math.ceil(Math.abs(paramAnonymous2Int) / 1000.0D);
                            ActReservation.this.reserve_money_spinner.setSelection(ActReservation.this.selectdReserveIdx - i);
                            ActReservation.this.txt_sale_price.setText(ActReservation.this.getString(R.string.price, new Object[]{ActReservation.this.nf.format(ActReservation.this.sale_price - paramAnonymous2Int)}));
                            return;
                        }
                        ActReservation.this.txt_sale_price.setText(ActReservation.this.getString(R.string.price, new Object[]{ActReservation.this.nf.format(paramAnonymous2Int)}));
                    }

                    public void onNothingSelected(AdapterView<?> paramAnonymous2AdapterView) {
                    }
                });
            }

            ActReservation.this.pnum1.setAdapter((SpinnerAdapter) new ArrayAdapter(ActReservation.this, android.R.layout.simple_spinner_item, ActReservation.this.getResources().getStringArray(R.array.phone_prefix)));

            String strUserName = ActReservation.this._preferences.getString("username", null);
            String strPhone = ActReservation.this._preferences.getString("phone", null);
            String[] phonePrefixs = ActReservation.this.getResources().getStringArray(R.array.phone_prefix);
            if ((strUserName != null) && (strPhone != null))
            {
                String[] arrPhone = ((String)strPhone).split("-");
                if (strUserName != null) {
                    ActReservation.this.usernameInput.setText((CharSequence)strUserName);
                }
                ActReservation.this.pnum2.setText(arrPhone[1]);
                ActReservation.this.pnum3.setText(arrPhone[2]);

                for (int i = 0; i < phonePrefixs.length; i++) {
                    if (arrPhone[0].equals(phonePrefixs[i])) {
                        ActReservation.this.pnum1.setSelection(i);
                        break;
                    }
                }
            }

            ActReservation.this.txt_sale_price = ((TextView)ActReservation.this.findViewById(R.id.txt_sale_price));
            ((TextView)ActReservation.this.findViewById(R.id.txt_real_price)).setText(ActReservation.this.getString(R.string.price, new Object[] { ActReservation.this.nf.format(j) }));
            ((TextView)ActReservation.this.findViewById(R.id.txt_save_price)).setText("-" + ActReservation.this.getString(R.string.price, new Object[] { ActReservation.this.nf.format(j - k) }));
            ActReservation.this.txt_sale_price.setText(ActReservation.this.getString(R.string.price, new Object[]{ActReservation.this.nf.format(ActReservation.this.sale_price)}));
            ActReservation.this.agree_policy = ((CheckBox)ActReservation.this.findViewById(R.id.agree_policy));

            ActReservation.this.agree_policy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton paramAnonymous2CompoundButton, boolean paramAnonymous2Boolean) {
                    if (paramAnonymous2Boolean) {
                        AlertDialog alertDialog = HotelUtil.getAlertTheme(ActReservation.this).setTitle(ActReservation.this.getString(R.string.cancel_rule)).setMessage(ActReservation.this.cancel_fee).setCancelable(false).setPositiveButton(ActReservation.this.getString(R.string.not_agree), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramAnonymous3DialogInterface, int paramAnonymous3Int) {
                                ActReservation.this.agree_policy.setChecked(false);
                                paramAnonymous3DialogInterface.dismiss();
                            }
                        }).setNegativeButton(ActReservation.this.getString(R.string.agree), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramAnonymous3DialogInterface, int paramAnonymous3Int) {
                                ActReservation.this.agree_policy.setChecked(true);
                                paramAnonymous3DialogInterface.dismiss();
                            }
                        }).show();

                        if (ActReservation.this.cancel_fee_str.startsWith("*")) {
                            ((TextView) alertDialog.findViewById(android.R.id.message)).setTextSize(14.0F);
                        }
                        else
                            ((TextView) alertDialog.findViewById(android.R.id.message)).setTextSize(17.0F);

                    } else {
                        return;
                    }
                }
            });

            ActReservation.this.paytypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup paramAnonymous2RadioGroup, int paramAnonymous2Int)
                {
                    ActReservation.this.paytype = 3;

                    switch (paramAnonymous2Int)
                    {
                        default:
                            return;
                        case R.id.paytype1:
                            ActReservation.this.paytype = 1;
                            return;
                        case R.id.paytype2:
                            ActReservation.this.paytype = 2;
                            return;
                    }
                }
            });

            ((TextView)ActReservation.this.findViewById(R.id.show_policy)).setOnClickListener(new View.OnClickListener() {

                public void onClick(View paramAnonymous2View) {
                    Intent intent = new Intent(ActReservation.this, ActWebView.class);
                    intent.putExtra("url", "http://api.hotelnow.co.kr/terms_booking/booking");
                    intent.putExtra("title", ActReservation.this.getString(R.string.caution_agreement_checkbox));
                    ActReservation.this.startActivity(intent);
                    ActReservation.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
            });

            ActReservation.this.paymentFormCheck();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ActReservation.this.getApplicationContext(), ActReservation.this.getString(R.string.error_product_user_info), Toast.LENGTH_LONG).show();
        }
    }

    private void paymentFormCheck() {
        if (this.sale_available.booleanValue()) {
            this.btn_go_payment.setOnClickListener(new paymentClick());
        }
    }

    class paymentClick implements View.OnClickListener {

        class C05951 implements DialogInterface.OnClickListener {
            C05951() {
            }

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        }

        paymentClick() {
        }

        public void onClick(View v) {
            if (!ActReservation.this.flag_btn_clicked.booleanValue()) {
                ActReservation.this.flag_btn_clicked = Boolean.valueOf(true);
                if (ActReservation.this.usernameInput.getText().toString().trim().length() < 2) {
                    Toast.makeText(ActReservation.this.getApplicationContext(), ActReservation.this.getString(R.string.validator_name_length), Toast.LENGTH_LONG).show();
                    ActReservation.this.usernameInput.requestFocus();
                    ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
                } else if (ActReservation.this.pnum2.getText().toString().trim().length() < 3) {
                    Toast.makeText(ActReservation.this.getApplicationContext(), ActReservation.this.getString(R.string.validator_pnum), Toast.LENGTH_LONG).show();
                    ActReservation.this.pnum2.requestFocus();
                    ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
                } else if (ActReservation.this.pnum3.getText().toString().trim().length() < 4) {
                    Toast.makeText(ActReservation.this.getApplicationContext(), ActReservation.this.getString(R.string.validator_pnum), Toast.LENGTH_LONG).show();
                    ActReservation.this.pnum3.requestFocus();
                    ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
                } else if (ActReservation.this.paytype <= 0) {
                    Toast.makeText(ActReservation.this.getApplicationContext(), "\uacb0\uc81c\ubc29\uc2dd\uc744 \uc120\ud0dd\ud558\uc138\uc694.", Toast.LENGTH_LONG).show();
                    ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
                } else if (ActReservation.this.agree_policy.isChecked()) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    Date dt = cal.getTime();
                    SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
                    SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    if ((CurHourFormat.format(dt).equals("00") || CurHourFormat.format(dt).equals("01")) && ActReservation.this.checkin_date.startsWith(CurDateFormat.format(dt))) {
                        int nowSec = (((cal.get(Calendar.HOUR_OF_DAY) * 60) * 60) + (cal.get(Calendar.MINUTE) * 60)) + cal.get(Calendar.SECOND);
                        int limitHour = (int) Math.floor((double) (((10800 - nowSec) / 60) / 60));
                        int limitMin = ((int) Math.floor((double) ((10800 - nowSec) / 60))) - (limitHour * 60);
                        Date dt2 = Calendar.getInstance().getTime();
                        String msg = "선택하신 상품의 체크인 시간이 <font color=#ff0000>" + limitHour + "시간 " + limitMin + "분</font> 남았습니다. " + new SimpleDateFormat("MM월 dd일").format(dt2) + " 오후에 투숙하실 상품을 구매하시려면 아래의 안내에 따라 상품을 선택해 주세요. <br/><br/>1. 상품 리스트 상단의 돋보기 아이콘 선택<br/>2. 투숙할 날짜 선택 후 검색 버튼 선택<br/>3. 검색 결과에서 원하는 숙소 선택 후 결제<br/><br/>현재 선택하신 상품을 결제하시겠습니까?";
                        ActReservation.this.bdialog = new DialogBookingCaution(ActReservation.this, ActReservation.this.skipClickListener, ActReservation.this.okClickListener);
                        ActReservation.this.bdialog.setCancelable(false);
                        ActReservation.this.bdialog.show();
                        ActReservation actReservation = ActReservation.this;
                        ActReservation.this.caution_txt = (TextView) ActReservation.this.bdialog.findViewById(R.id.caution_txt);
                        ActReservation.this.caution_txt.setText(Html.fromHtml(msg));
                        return;
                    }
                    ActReservation.this.goPayment();
                } else {
                    HotelUtil.getAlertTheme(ActReservation.this).setTitle(ActReservation.this.getString(R.string.alert_notice)).setMessage(ActReservation.this.getString(R.string.need_caution_agree)).setPositiveButton(ActReservation.this.getString(R.string.alert_confrim), new C05951()).show();
                    ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
                }
            }
        }
    }

    private void goPayment() {
        String phone_number = this.pnum1.getSelectedItem() + "-" + this.pnum2.getText().toString() + "-" + this.pnum3.getText().toString();
        this.wrapper.setVisibility(View.VISIBLE);

        HashMap<String, String> params = new HashMap();
        params.put("pid", this.pid);
        params.put("un", Base64.encodeToString(this.usernameInput.getText().toString().getBytes(), 2));
        params.put("up", Base64.encodeToString(phone_number.getBytes(), 2));
        params.put("pcode", this.pcode);
        params.put("reserve_money", String.valueOf(this.reserve_value));
        if (!(this.ec_date == null || this.ee_date == null)) {
            params.put("ec_date", this.ec_date);
            params.put("ee_date", this.ee_date);
            params.put("consecutive", "Y");
        }

        QGHttpRequest.getInstance().BookingReserve(this, params, new BookingReservation(this));

//        JSONObject params = new JSONObject();
//        try {
//            params.put("pid", this.pid);
//            params.put("un", Base64.encodeToString(this.usernameInput.getText().toString().getBytes(), 2));
//            params.put("up", Base64.encodeToString(phone_number.getBytes(), 2));
//            params.put("pcode", this.pcode);
//            params.put("reserve_money", this.reserve_value);
//            if (!(this.ec_date == null || this.ee_date == null)) {
//                params.put("ec_date", this.ec_date);
//                params.put("ee_date", this.ee_date);
//                params.put("consecutive", "Y");
//            }
//        } catch (JSONException e) {
//        }
//        VolleyUtil.getRequestQueue().add(new JsonObjectRequest(1, CONFIG.bookingReserveUrl, params, new C11686(), new C11697()));
    }

    class BookingReservation extends QGHttpHandler<String>
    {

        public BookingReservation(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(String s) {
            ActReservation.this.PerformBookingReserve(s);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            ActReservation.this.wrapper.setVisibility(View.GONE);
            Toast.makeText(ActReservation.this.getApplicationContext(), ActReservation.this.getString(R.string.error_booking_fail), Toast.LENGTH_LONG).show();
            ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
        }
    }

    private void PerformBookingReserve(String strResult)
    {
        try {
            JSONObject response = new JSONObject(strResult);
            if (response.getString("result").equals("success")) {
                ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
                Intent intent = new Intent(ActReservation.this, ActAllatWebView.class);
                intent.putExtra("paytype", ActReservation.this.paytype);
                intent.putExtra("bid", response.getString("bid"));
                intent.putExtra("hotel_name", ActReservation.this.hotel_name);
                intent.putExtra("pid", ActReservation.this.pid);
                ActReservation.this.startActivity(intent);
                ActReservation.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            } else {
                Toast.makeText(ActReservation.this.getApplicationContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
            }
            ActReservation.this.wrapper.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ActReservation.this.getApplicationContext(), ActReservation.this.getString(R.string.error_booking_fail), Toast.LENGTH_LONG).show();
            ActReservation.this.wrapper.setVisibility(View.GONE);
            ActReservation.this.flag_btn_clicked = Boolean.valueOf(false);
        }
    }
}
