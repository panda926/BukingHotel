package com.gitrose.bukinghotel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.view.FlowLayout;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.roomorama.caldroid.CaldroidListener;
import com.roomorama.caldroid.CaldroidFragment;

public class ActSearch extends BaseFragmentActivity implements View.OnClickListener {

//    private static SharedPreferences _preferences;
//    private static Spinner city;
//    private static EditText keyword;
//    private static Spinner sub_city;
//    private Button apply;
//    private String[] areas;
//    private String[] areasCode;
//    private String[] categoriCodes;
//    private String[] categoriarr;
//    private List<String> categories = new ArrayList();
//    private FlowLayout category;
//    private TextView checkin_txt;
//    private TextView checkout_txt;
//    private List<String> configCategories = new ArrayList();
//    private CaldroidFragment dialogCaldroidFragment;
//    private int fDayLimit;
//    private Typeface font;
//    private Date fromday;
//    private List<String> keywords = new ArrayList();
//    private SpinnerAdapter mSpinnerAdapter;
//    private SpinnerAdapter mSubSpinnerAdapter;
//    private SeekBar price_seekbar;
//    private String sel_area;
//    private String sel_category;
//    private String sel_checkin;
//    private String sel_checkout;
//    private String sel_keyword;
//    private String sel_subarea;
//    private Bundle state;
//    private String[] sub_areas;
//    private String[] sub_areasCode;
//    private Button submit;
//    private FlowLayout tags;
//    private java.util.Date today;
//    private Date untilday;
//    private final String[] week = { "일", "월", "화", "수", "목", "금", "토" };
//
//    private LinearLayout llCheckInBtn;
//    private LinearLayout llCheckOutBtn;

    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;

    private String[] strArray1;
    private String[] strArray2;
    private String[] strArray3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_search);

        initView();
    }

    class MySpinnerAdapter extends ArrayAdapter<String> {
        public MySpinnerAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        @Override
        public int getCount() {
            return super.getCount() - 1; // This makes the trick: do not show last item
        }

        @Override
        public String getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

    }

    private void initView()
    {

        this.spinner1 = (Spinner)findViewById(R.id.spinner1);
        this.spinner2 = (Spinner)findViewById(R.id.spinner2);
        this.spinner3 = (Spinner)findViewById(R.id.spinner3);

        this.strArray1 = getResources().getStringArray(R.array.area_select_text);
        MySpinnerAdapter adapter = new MySpinnerAdapter(this, R.layout.menu_area_spinner, strArray1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setSelection(strArray1.length - 1);
        spinner2.setAdapter(adapter);
        spinner2.setSelection(strArray1.length - 1);
        spinner3.setAdapter(adapter);
        spinner3.setSelection(strArray1.length - 1);


//        this.font = Typeface.createFromAsset(getAssets(), "fonts/nanumgothic.ttf");
//        this.checkin_txt = ((TextView)findViewById(R.id.checkin_txt));
//        this.checkout_txt = ((TextView)findViewById(R.id.checkout_txt));
//        keyword = (EditText)findViewById(R.id.keyword);
//        city = (Spinner)findViewById(R.id.city);
//        this.apply = ((Button)findViewById(R.id.apply));
//        this.submit = ((Button)findViewById(R.id.submit));
//        sub_city = (Spinner)findViewById(R.id.sub_city);
//        this.tags = ((FlowLayout)findViewById(R.id.tags));
//        this.category = ((FlowLayout)findViewById(R.id.category));
//        this.price_seekbar = ((SeekBar)findViewById(R.id.price_seekbar));
//        keyword.setTypeface(this.font);
//        this.sel_checkin = CONFIG.sel_checkin;
//        this.sel_checkout = CONFIG.sel_checkout;
//        this.sel_keyword = CONFIG.sel_keyword;
//        this.sel_area = CONFIG.sel_area;
//        this.sel_subarea = CONFIG.sel_subarea;
//        int i;
//
//        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat dateFormat2 = new SimpleDateFormat(getString(R.string.year_month_day_weekday_format2), Locale.KOREAN);
//        if(this.sel_checkin != null && this.sel_checkout != null)
//        {
//            try {
//                String strCheckIn = dateFormat2.format(dateFormat1.parse(this.sel_checkin));
//                String strCheckOut = dateFormat2.format(dateFormat1.parse(this.sel_checkout));
//                this.checkin_txt.setText(strCheckIn);
//                this.checkout_txt.setText(strCheckOut);
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        this.fDayLimit = _preferences.getInt("future_day_limit", 6);
//        SharedPreferences.Editor prefEditor = this._preferences.edit();
//        prefEditor.putBoolean("show_search_pop", true);
//        prefEditor.commit();
//
//        this.llCheckInBtn = (LinearLayout)findViewById(R.id.checkin_btn);
//        this.llCheckOutBtn = (LinearLayout)findViewById(R.id.checkout_btn);
//        llCheckInBtn.setOnClickListener(this);
//        llCheckOutBtn.setOnClickListener(this);
//
//        this.areas = getResources().getStringArray(R.array.area_select_text);
//        this.areasCode = getResources().getStringArray(R.array.area_select_code);
//        this.mSpinnerAdapter = new ArrayAdapter(this, R.layout.menu_area_spinner, this.areas);
//        city.setAdapter(this.mSpinnerAdapter);
//        city.setOnItemSelectedListener(new cityItemOnClick());
//        if(this.sel_subarea != null)
//        {
//            setSubSelection();
//        }
//
//        this.categoriarr = getResources().getStringArray(R.array.category_text);
//        this.categoriCodes = getResources().getStringArray(R.array.category_code);
//
//        for( int j = 0; j < this.categoriarr.length; j++)
//        {
//            CheckBox chkCategory = new CheckBox(this);
//            ((CheckBox)chkCategory).setId(j);
//            ((CheckBox)chkCategory).setTag(this.categoriCodes[j]);
//            ((CheckBox)chkCategory).setText(this.categoriarr[j]);
//            ((CheckBox)chkCategory).setTypeface(this.font);
//            ((CheckBox)chkCategory).setTextSize(2, 13.0F);
////            ((CheckBox)chkCategory).setTextColor(-1);
//            ((CheckBox)chkCategory).setGravity(17);
//            ((CheckBox)chkCategory).setBackgroundResource(R.drawable.checkbox_search_category);
//            ((CheckBox)chkCategory).setButtonDrawable(R.drawable.checkbox_search_category);
//
//            ((CheckBox)chkCategory).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean) {
//                    if (paramAnonymousBoolean) {
//                        ActSearch.this.categories.add(paramAnonymousCompoundButton.getTag().toString());
//                        return;
//                    }
//                    ActSearch.this.categories.remove(paramAnonymousCompoundButton.getTag().toString());
//                }
//            });
//
//            if (this.configCategories.contains(this.categoriCodes[j])) {
//                ((CheckBox)chkCategory).setChecked(true);
//            }
//            this.category.addView((View) chkCategory, new ViewGroup.LayoutParams(25, 20));
//        }



//        String[] listSelKeyword;
//        if (this.sel_keyword != null)
//        {
//            listSelKeyword = this.sel_keyword.split("\\|");
//            i = 0;
//
//            for( i = 0 ; i < listSelKeyword.length; i++) {
//                this.configCategories.add(listSelKeyword[i]);
//            }
//        }
    }

    @Override
    public void onClick(View v) {

    }

//    private void setKeywordTag(String txt) {
//        this.keywords.add(txt);
//        CheckBox ch = new CheckBox(this);
//        ch.setTag(txt);
//        ch.setText(new StringBuilder(String.valueOf(txt)).append("  X").toString());
////        ch.setTextColor(-1);
//        ch.setTypeface(this.font);
//        ch.setBackgroundResource(R.drawable.button_round_search_purple);
//        ch.setButtonDrawable(R.drawable.button_round_search_purple);
//        ch.setPadding(25, 10, 25, 10);
//        ch.setOnClickListener(new TagOnClick());
//        this.tags.addView(ch, new ViewGroup.LayoutParams(10, 10));
//    }
//
//    class TagOnClick implements View.OnClickListener {
//        TagOnClick() {
//        }
//
//        public void onClick(View v) {
//            ((ViewGroup) v.getParent()).removeView(v);
//            ActSearch.this.keywords.remove(v.getTag().toString());
//        }
//    }
//
//    class cityItemOnClick implements AdapterView.OnItemSelectedListener {
//        cityItemOnClick() {
//        }
//
//        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//            ActSearch.this.sel_area = ActSearch.this.areasCode[position];
//            if (ActSearch.this.sel_area.equals("all")) {
//                ActSearch.sub_city.setVisibility(View.GONE);
//                ActSearch.this.sel_subarea = null;
//            } else if (ActSearch.this.sel_area.equals("100_seoul") || ActSearch.this.sel_area.equals("300_busan") || ActSearch.this.sel_area.equals("210_gyeonggi") || ActSearch.this.sel_area.equals("250_incheon")) {
//                ActSearch.this.setSubSelection();
//            } else {
//                ActSearch.sub_city.setVisibility(View.GONE);
//                ActSearch.this.sel_subarea = null;
//            }
//        }
//
//        public void onNothingSelected(AdapterView<?> adapterView) {
//        }
//    }
//
//    private void setSubSelection() {
//        int i;
//        if (this.sel_area.equals("100_seoul")) {
//            this.sub_areas = getResources().getStringArray(R.array.area_seoul_text);
//            this.sub_areasCode = getResources().getStringArray(R.array.area_seoul_code);
//        } else if (this.sel_area.equals("300_busan")) {
//            this.sub_areas = getResources().getStringArray(R.array.area_busan_text);
//            this.sub_areasCode = getResources().getStringArray(R.array.area_busan_code);
//        } else if (this.sel_area.equals("210_gyeonggi")) {
//            this.sub_areas = getResources().getStringArray(R.array.area_gyeonggi_text);
//            this.sub_areasCode = getResources().getStringArray(R.array.area_gyeonggi_code);
//        } else if (this.sel_area.equals("250_incheon")) {
//            this.sub_areas = getResources().getStringArray(R.array.area_incheon_text);
//            this.sub_areasCode = getResources().getStringArray(R.array.area_incheon_code);
//        }
//        this.mSpinnerAdapter = new ArrayAdapter(this, R.layout.menu_area_spinner, this.sub_areas);
//        sub_city.setAdapter(this.mSpinnerAdapter);
//        sub_city.setVisibility(View.VISIBLE);
//        if (this.sel_subarea != null) {
//            for (i = 0; i < this.sub_areasCode.length; i++) {
//                if (this.sel_area.equals(this.sub_areasCode[i])) {
//                    sub_city.setSelection(i);
//                    break;
//                }
//            }
//        }
//        sub_city.setOnItemSelectedListener(new subCityItemOnClick());
//        i = 0;
//        while (i < this.sub_areasCode.length) {
//            if (this.sel_subarea == null || !this.sel_subarea.equals(this.sub_areasCode[i])) {
//                i++;
//            } else {
//                sub_city.setSelection(i);
//                return;
//            }
//        }
//    }
//
//    class subCityItemOnClick implements AdapterView.OnItemSelectedListener {
//        subCityItemOnClick() {
//        }
//
//        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//            ActSearch.this.sel_subarea = ActSearch.this.sub_areasCode[position];
//        }
//
//        public void onNothingSelected(AdapterView<?> adapterView) {
//        }
//    }
//
//    public void showCalendar(int idx) throws ParseException {
//        if (idx == 2 && this.sel_checkin == null) {
//            Toast.makeText(getApplicationContext(), "\uccb4\ud06c\uc778 \ub0a0\uc9dc\ub97c \uba3c\uc800 \uc120\ud0dd\ud558\uc138\uc694.", Toast.LENGTH_LONG).show();
//            return;
//        }
//        String str;
//        String dialogTag;
//        Bundle args;
//        if (CONFIG.svr_time == null) {
//            java.util.Date date = new java.util.Date(System.currentTimeMillis());
//            CONFIG.svr_time = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(date);
//        }
//        java.util.Date dateObj = new java.util.Date();
//        try {
//            String str2 = CONFIG.svr_time;
//            dateObj = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(str2);
//        } catch (Exception e) {
//        }
//        Calendar todayCal = Calendar.getInstance();
//        todayCal.setTime(dateObj);
//        this.today = todayCal.getTime();
//        CaldroidListener listener = new CalListner(idx);
//        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
//        Calendar fromCal = Calendar.getInstance();
//        Calendar untilCal = Calendar.getInstance();
//        fromCal.setTime(this.today);
//        untilCal.setTime(this.today);
//        if (!CurHourFormat.format(this.today).equals("00")) {
//            if (!CurHourFormat.format(this.today).equals("01")) {
//                this.fromday = this.today;
//                untilCal.add(Calendar.DAY_OF_MONTH, this.fDayLimit);
//                this.untilday = untilCal.getTime();
//                if (idx == 2 && this.sel_checkin != null) {
//                    str = this.sel_checkin;
//                    fromCal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(str));
//                    fromCal.add(Calendar.DAY_OF_MONTH, 1);
//                    this.fromday = fromCal.getTime();
//                    untilCal.setTime(this.today);
//                    untilCal.add(Calendar.DAY_OF_MONTH, this.fDayLimit + 1);
//                    this.untilday = untilCal.getTime();
//                }
//                if (idx == 1 && this.sel_checkout != null) {
//                    str = this.sel_checkout;
//                    untilCal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(str));
//                    untilCal.add(Calendar.DAY_OF_MONTH, -1);
//                    this.untilday = untilCal.getTime();
//                }
//                if (!(idx != 1 || this.sel_checkin == null || this.sel_checkout == null)) {
//                    this.fromday = this.today;
//                }
//                this.dialogCaldroidFragment = new CaldroidFragment();
//                this.dialogCaldroidFragment.setCaldroidListener(listener);
//                this.dialogCaldroidFragment.setSelectedDates(this.fromday, this.untilday);
//                dialogTag = "CALDROID_DIALOG_FRAGMENT";
//                if (this.state == null) {
//                    this.dialogCaldroidFragment.restoreDialogStatesFromKey(getSupportFragmentManager(), this.state, "DIALOG_CALDROID_SAVED_STATE", "CALDROID_DIALOG_FRAGMENT");
//                    args = this.dialogCaldroidFragment.getArguments();
//                    if (args == null) {
//                        args = new Bundle();
//                        this.dialogCaldroidFragment.setArguments(args);
//                    }
//                    args.putString(CaldroidFragment.DIALOG_TITLE, null);
//                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(CaldroidFragment.DIALOG_TITLE, null);
//                    this.dialogCaldroidFragment.setArguments(bundle);
//                }
//                this.dialogCaldroidFragment.show(getSupportFragmentManager(), "CALDROID_DIALOG_FRAGMENT");
//            }
//        }
//        fromCal.add(Calendar.DAY_OF_MONTH, -1);
//        this.fromday = fromCal.getTime();
//        untilCal.add(Calendar.DAY_OF_MONTH, this.fDayLimit);
//        this.untilday = untilCal.getTime();
//        try {
//            str = this.sel_checkin;
//            fromCal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(str));
//            fromCal.add(Calendar.DAY_OF_MONTH, 1);
//            this.fromday = fromCal.getTime();
//            untilCal.setTime(this.today);
//            untilCal.add(Calendar.DAY_OF_MONTH, this.fDayLimit + 1);
//            this.untilday = untilCal.getTime();
//        } catch (Exception e2) {
//        }
//        try {
//            str = this.sel_checkout;
//            untilCal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(str));
//            untilCal.add(Calendar.DAY_OF_MONTH, -1);
//            this.untilday = untilCal.getTime();
//        } catch (Exception e3) {
//        }
//        this.fromday = this.today;
//        this.dialogCaldroidFragment = new CaldroidFragment();
//        this.dialogCaldroidFragment.setCaldroidListener(listener);
//        this.dialogCaldroidFragment.setSelectedDates(this.fromday, this.untilday);
//        dialogTag = "CALDROID_DIALOG_FRAGMENT";
//        if (this.state == null) {
//            Bundle bundle2 = new Bundle();
//            bundle2.putString(CaldroidFragment.DIALOG_TITLE, null);
//            this.dialogCaldroidFragment.setArguments(bundle2);
//        } else {
//            this.dialogCaldroidFragment.restoreDialogStatesFromKey(getSupportFragmentManager(), this.state, "DIALOG_CALDROID_SAVED_STATE", "CALDROID_DIALOG_FRAGMENT");
//            args = this.dialogCaldroidFragment.getArguments();
//            if (args == null) {
//                args = new Bundle();
//                this.dialogCaldroidFragment.setArguments(args);
//            }
//            args.putString(CaldroidFragment.DIALOG_TITLE, null);
//        }
//        this.dialogCaldroidFragment.show(getSupportFragmentManager(), "CALDROID_DIALOG_FRAGMENT");
//    }
//
//    class CalListner extends CaldroidListener {
//        private final int val$idx;
//
//        CalListner(int i) {
//            this.val$idx = i;
//        }
//
//        public void onSelectDate(Date date, View view) {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
//            SimpleDateFormat selDateFormat = new SimpleDateFormat("yyyyMMdd");
//            SimpleDateFormat CurDateFormatDay = new SimpleDateFormat(ActSearch.this.getString(R.string.year_month_day_weekday_format2), Locale.KOREAN);
//            Calendar startCal = Calendar.getInstance();
//            startCal.setTime(ActSearch.this.today);
//            if (CurHourFormat.format(ActSearch.this.today).equals("00") || CurHourFormat.format(ActSearch.this.today).equals("01")) {
//                startCal.add(Calendar.DAY_OF_MONTH, -1);
//                ActSearch.this.today = startCal.getTime();
//            }
//            try {
//                if (Integer.valueOf(selDateFormat.format(ActSearch.this.fromday)).intValue() > Integer.valueOf(selDateFormat.format(date)).intValue() || Integer.valueOf(selDateFormat.format(ActSearch.this.untilday)).intValue() < Integer.valueOf(selDateFormat.format(date)).intValue()) {
//                    Toast.makeText(ActSearch.this.getApplicationContext(), ActSearch.this.getString(R.string.invalid_date), Toast.LENGTH_LONG).show();
//                    return;
//                }
//            } catch (Exception e) {
//            }
//            String selDate = CurDateFormatDay.format(date);
//            if (this.val$idx == 1) {
//                ActSearch.this.sel_checkin = formatter.format(date);
//                ActSearch.this.checkin_txt.setText(selDate);
//            } else {
//                ActSearch.this.sel_checkout = formatter.format(date);
//                ActSearch.this.checkout_txt.setText(selDate);
//            }
//            ActSearch.this.dialogCaldroidFragment.dismiss();
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId())
//        {
//            case R.id.checkin_btn:
//                try {
//                    ActSearch.this.showCalendar(1);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                break;
//
//            case R.id.checkout_btn:
//                try {
//                    ActSearch.this.showCalendar(2);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }
}
