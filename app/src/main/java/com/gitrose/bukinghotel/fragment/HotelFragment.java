package com.gitrose.bukinghotel.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActDealDetail;
import com.gitrose.bukinghotel.activity.SelectDateActivity;
import com.gitrose.bukinghotel.adapter.ArrayEventList;
import com.gitrose.bukinghotel.adapter.ArrayMainList;
import com.gitrose.bukinghotel.adapter.EventEntry;
import com.gitrose.bukinghotel.adapter.MainListAdapter;
import com.gitrose.bukinghotel.adapter.MainListEntry;
import com.gitrose.bukinghotel.http.QGHttpHandler;
import com.gitrose.bukinghotel.http.QGHttpRequest;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.gitrose.bukinghotel.util.HotelUtil;
import com.gitrose.bukinghotel.view.BaseDialog;
import com.gitrose.bukinghotel.view.MyCalendar;
import com.gitrose.bukinghotel.view.ToughViewPager;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class HotelFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Location mLocation;

    private TextView txt_todayHotel;
    private TextView txt_tommorrowHotel;
    private TextView txt_selectDate;

    private View header;
    private View footer;

    private String selArea;
    private String selCategory;
    private String selCheckin;
    private String selCheckout;
    private String selKeyword;
    private int selPriceidx;
    private String selSortType;
    private String selSubCity;

    private String[] arrKeyword;
    private String[] arrCategory;

    private SharedPreferences _preferences;
    private ProgressBar progressbar;
    private PullToRefreshListView mMainListView;
    private ListView tmpListview;
    private LinearLayout header_wrap;
    private RelativeLayout evt_wrap;
    private RelativeLayout fixed_wrap;
    private Runnable mRunnable;
    private Handler mHandler;

    private ArrayList<MainListEntry> mEntries;
    private ArrayList<MainListEntry> fixEntries;
    private ArrayList<EventEntry> evtEntries;

    private MainListAdapter mAdapter;

    private String locLat;
    private String locLng;

    private boolean mIsFinishRequest;

    public static ToughViewPager mEventPager;
    public static ToughViewPager mFixedPager;

    public static int EVT_CNT;
    public static int FIRST_PAGE;
    public static int FIRST_PAGE_EVT;
    public static int FIX_CNT;
    public static int LOOPS;
    public static int LOOPS_EVT;
    public static int PAGES;
    public static int PAGES_EVT;

    public static int markNowPosition;
    public static int markPrevPosition;
    public static int nowPosition;

    private EventAdapter mEventAdapter;
    private MyPagerAdapter mFixedAdapter;

    private Runnable Update;
    private Timer swipeTimer;
    private Handler handler;

    private View vSelectDate;
    private BaseDialog mDialogDown;

    public static HotelFragment newInstance(String param1, String param2) {
        HotelFragment fragment = new HotelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HotelFragment() {
        // Required empty public constructor
        this.selArea = null;
        this.selKeyword = null;
        this.selSubCity = null;
        this.selSortType = null;
        this.selCategory = null;
        this.selCheckin = null;
        this.selCheckout = null;
        this.selPriceidx = 0;
        this.mEntries = new ArrayList();
        this.fixEntries = new ArrayList();
        this.evtEntries = new ArrayList();
        mIsFinishRequest = true;
        this.Update = null;
        this.swipeTimer = null;
        this.handler = new Handler();
    }

    static {
        PAGES = 0;
        PAGES_EVT = 0;
        LOOPS = 1000;
        LOOPS_EVT = 1000;
        FIRST_PAGE = 0;
        FIRST_PAGE_EVT = 0;
        FIX_CNT = 0;
        EVT_CNT = 0;
        nowPosition = 0;
        markNowPosition = 0;
        markPrevPosition = 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_hotel, container, false);
        this.header = inflater.inflate(R.layout.header_main_list, null, false);
        this.footer = inflater.inflate(R.layout.footer_main_list, null, false);
        initView(view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null) {

            this.selArea = getArguments().getString("selArea", "");
            this.selSubCity = getArguments().getString("selSubCity", "");
            this.selSortType = getArguments().getString("selSortType", "");
            this.selCategory = getArguments().getString("selCategory", "");
            this.selCheckin = getArguments().getString("selCheckin", "");
            this.selCheckout = getArguments().getString("selCheckout", "");
            this.selPriceidx = getArguments().getInt("selPriceidx", 0);
            this.selKeyword = getArguments().getString("selKeyword", "");
        }

        if (this.selKeyword != null) {
            this.arrKeyword = this.selKeyword.split("\\|");
        }
        if (this.selCategory != null) {
            this.arrCategory = this.selCategory.split("\\|");
        }

        this._preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        this.progressbar = (ProgressBar) getView().findViewById(R.id.loading_progress);
        this.mMainListView = (PullToRefreshListView) getView().findViewById(R.id.main_list);
        this.tmpListview = (ListView) this.mMainListView.getRefreshableView();
        this.tmpListview.addHeaderView(this.header);
        this.tmpListview.addFooterView(this.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.header_wrap = (LinearLayout) this.header.findViewById(R.id.header_wrap);
        this.evt_wrap = (RelativeLayout) this.header.findViewById(R.id.evt_wrap);
        this.fixed_wrap = (RelativeLayout) this.header.findViewById(R.id.slide_wrap);
        this.mAdapter = new MainListAdapter(getActivity(), 0, this.mEntries, this.selSortType);
        this.mMainListView.setAdapter(this.mAdapter);
        this.mMainListView.setOnRefreshListener(new ListViewRefreshHandler());
        this.mMainListView.setOnItemClickListener(new ListViewItemClickHandler());

        this.initEventPapaer();
        this.initFixedPaper();

        if (System.currentTimeMillis() - this._preferences.getLong("last_loading_time", 0) > 300000) {
            SharedPreferences.Editor prefEditor = this._preferences.edit();
            prefEditor.putLong("last_loading_time", System.currentTimeMillis());
            prefEditor.commit();
        }
        this.mRunnable = new LoadPageThread();
        this.mHandler = new Handler();
        this.mHandler.postDelayed(this.mRunnable, 300);

        initDialog();
    }

    private void initDialog()
    {
        this.mDialogDown = new BaseDialog(this.getActivity(), R.style.Transparent);
    }

    class ListViewItemClickHandler implements AdapterView.OnItemClickListener {
        ListViewItemClickHandler() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            TextView tv = (TextView) view.findViewById(R.id.hid);
            TextView evt = (TextView) view.findViewById(R.id.event);

            if (tv != null) {
                Intent intent = new Intent(HotelFragment.this.getActivity(), ActDealDetail.class);
                intent.putExtra("hid", tv.getText().toString());
                intent.putExtra("evt", evt.getText().toString());

                if (!(HotelFragment.this.selCheckin == null || HotelFragment.this.selCheckout == null)) {
                    intent.putExtra("ec_date", HotelFragment.this.selCheckin);
                    intent.putExtra("ee_date", HotelFragment.this.selCheckout);
                }
                HotelFragment.this.startActivity(intent);
                HotelFragment.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        }
    }

    @Override
    public void onPause() {
        stopTimer();
        super.onPause();

//        try {
//            this.pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(getActivity(), RefreshReceiver.class), 0);
//            ((AlarmManager) getActivity().getSystemService("alarm")).cancel(this.pendingIntent);
//        } catch (Exception e) {
//        }
    }

    private void setTimer() {
        if (mEventPager != null) {
            try {
                this.Update = new UpdateThread();
                this.swipeTimer = new Timer();
                this.swipeTimer.schedule(new SchduleHandler(), 1000, 3500);
            } catch (Exception e) {
//                HotelUtil.doRestart(getActivity());
            }
        }
    }

    class SchduleHandler extends TimerTask {
        SchduleHandler() {
        }

        public void run() {
            HotelFragment.this.handler.post(HotelFragment.this.Update);
        }
    }

    class UpdateThread implements Runnable {
        UpdateThread() {
        }

        public void run() {
            try {
                ToughViewPager toughViewPager = HotelFragment.mEventPager;
                int i = HotelFragment.nowPosition;
                HotelFragment.nowPosition = i + 1;
                toughViewPager.setCurrentItem(i, true);
            } catch (Exception e) {
//                HotelUtil.doRestart(FrgMainList.this.getActivity());
            }
        }
    }

    private void stopTimer() {
        if (this.Update != null || this.swipeTimer != null) {
            this.swipeTimer.cancel();
            this.swipeTimer.purge();
            this.swipeTimer = null;
            this.Update = null;
        }
    }

    class ListViewRefreshHandler implements PullToRefreshBase.OnRefreshListener<ListView> {
        ListViewRefreshHandler() {
        }

        public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            new GetDataTask().execute(new Void[0]);
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
        private GetDataTask() {
        }

        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            return new String[0];
        }

        protected void onPostExecute(String[] result) {
            
            HotelFragment.this.footer.setVisibility(View.INVISIBLE);
            HotelFragment.this.progressbar.setVisibility(View.VISIBLE);
            HotelFragment.this.fixed_wrap.setVisibility(View.GONE);
            HotelFragment.this.evt_wrap.setVisibility(View.GONE);
            if (HotelFragment.this.mFixedAdapter != null) {
                HotelFragment.this.mFixedAdapter.removePages();
            }
            if (HotelFragment.this.mEventAdapter != null) {
                HotelFragment.this.mEventAdapter.removePages();
            }
            HotelFragment.this.mAdapter.clear();
            CONFIG.hotelObj = null;
            HotelFragment.this.loadPage();
            HotelFragment.this.mMainListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private void checkLocation() {
        if (this._preferences.getBoolean("flag_use_location", false)) {
            if (HotelUtil.userLocation != null) {
                this.mLocation = HotelUtil.userLocation;
            }
            if (this.mLocation != null) {
                this.locLat = String.valueOf(this.mLocation.getLatitude());
                this.locLng = String.valueOf(this.mLocation.getLongitude());
                CONFIG.useLocation = true;
                return;
            }
            this.locLat = null;
            this.locLng = null;
            CONFIG.useLocation = false;
            return;
        }
        this.locLat = null;
        this.locLng = null;
    }

    private void loadPage() {
        int i;
        this.progressbar.setVisibility(View.VISIBLE);
        checkLocation();
        String useLoc = (this.locLat == null || this.locLng == null) ? "N" : "Y";
        String url = "http://api.hotelnow.co.kr/products/1/999?useloc=" + useLoc;
        if (this.locLat == null || this.locLng == null) {
            CONFIG.useLocation = false;
        } else {
            url = new StringBuilder(String.valueOf(url)).append("&lat=").append(this.locLat).append("&lng=").append(this.locLng).toString();
            CONFIG.useLocation = true;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        Calendar startCal = Calendar.getInstance();
        if (CurHourFormat.format(today).equals("00") || CurHourFormat.format(today).equals("01")) {
            startCal.add(Calendar.DAY_OF_MONTH, -1);
            today = startCal.getTime();
        }
        if (!(this.selCheckin == null || this.selCheckout == null)) {
            url = new StringBuilder(String.valueOf(url)).append("&ec_date=").append(this.selCheckin).append("&ee_date=").append(this.selCheckout).append("&consecutive=Y").toString();
            long diffDays = HotelUtil.diffOfDate(this.selCheckin.replace("-", ""), this.selCheckout.replace("-", ""));
            if (this.selCheckin.equals(formatter.format(today)) && diffDays != 1) {
                url = new StringBuilder(String.valueOf(url)).append("&is_future=Y").toString();
            }
            if (!this.selCheckin.equals(formatter.format(today)) && diffDays == 1) {
                url = new StringBuilder(String.valueOf(url)).append("&is_future=Y").toString();
            }
        }

        initEventPapaer();
        initFixedPaper();

        RequestData();
    }

    String strFromDate = "";
    String strToDate = "";
    String strOrder = "";

    public void RequestData()
    {
        if (this.mIsFinishRequest) {
            this.mIsFinishRequest = false;

            if(this.txt_todayHotel.isSelected())
            {
                strFromDate = HotelUtil.GetToday();
                strToDate = "";
                strOrder = "";
            }
            else if(this.txt_tommorrowHotel.isSelected())
            {
                strFromDate = HotelUtil.GetTomorrow();
                strToDate = "";
                strOrder = "";

            }
            else if(this.txt_selectDate.isSelected())
            {

            }

            QGHttpRequest.getInstance().getMainList(getActivity(), strFromDate, strToDate, strOrder, new GetMainList(getActivity()));
            strFromDate = "";
            strToDate = "";
            strOrder = "";
        }
    }

    class GetMainList extends QGHttpHandler<String> {

        GetMainList(Context $anonymous0) {

            super($anonymous0);

        }

        public void onGetDataSuccess(String strResponse) {


            Gson gson = new Gson();
            String content = strResponse;
            ArrayMainList data = gson.fromJson(content, ArrayMainList.class);
            ArrayEventList eventData = gson.fromJson(content, ArrayEventList.class);

            List<EventEntry> events = eventData.getList();
            if(events.size() > 0) {

                HotelFragment.this.header_wrap.setVisibility(View.VISIBLE);
                HotelFragment.this.evt_wrap.setVisibility(View.VISIBLE);
                HotelFragment.this.evtEntries.clear();
                HotelFragment.this.evtEntries.addAll(events);
                HotelFragment.this.PAGES_EVT = events.size();
                HotelFragment.this.initPageMark();
                HotelFragment.this.mEventAdapter.notifyDataSetChanged();
                HotelFragment.this.setTimer();
            }

            if (data != null) {

                List<MainListEntry> topics = data.getList();
                int requestSize = topics.size();
                if (requestSize > 0) {

                    List<MainListEntry> hotelList = new ArrayList<MainListEntry>();
                    List<MainListEntry> fixedList = new ArrayList<MainListEntry>();

                    for(int i = 0; i < topics.size(); i++)
                    {
                        if(topics.get(i).getFixed().equals("N"))
                        {
                            hotelList.add(topics.get(i));
                        }
                        else if(topics.get(i).getFixed().equals("Y"))
                        {
                            fixedList.add(topics.get(i));
                        }
                    }

                    HotelFragment.this.footer.setVisibility(View.VISIBLE);
                    HotelFragment.this.header_wrap.setVisibility(View.VISIBLE);
                    HotelFragment.this.fixed_wrap.setVisibility(View.VISIBLE);

                    HotelFragment.this.fixEntries.clear();
                    HotelFragment.this.fixEntries.addAll(fixedList);
                    HotelFragment.this.PAGES = fixedList.size();
                    HotelFragment.this.mFixedPager.setCurrentItem(0, false);
                    HotelFragment.this.mFixedPager.setOffscreenPageLimit(1);

                    float fDensity = HotelFragment.this.getActivity().getResources().getDisplayMetrics().density;
                    int nListHeight = HotelUtil.getListHeight(HotelFragment.this.getActivity());

                    HotelFragment.this.mFixedPager.getLayoutParams().height = nListHeight + (int)(fDensity * 50);

                    HotelFragment.this.mFixedAdapter.notifyDataSetChanged();

                    HotelFragment.this.mFixedPager.setCurrentItem(1, false);


                    HotelFragment.this.mEntries.clear();
                    HotelFragment.this.mEntries.addAll(hotelList);
                    HotelFragment.this.mAdapter.notifyDataSetChanged();
                }
            }
        }

        public void onFinish() {
            HotelFragment.this.mIsFinishRequest = true;
            HotelFragment.this.progressbar.setVisibility(View.INVISIBLE);
            super.onFinish();
        }
    }

    private void initEventPapaer()
    {
        mEventPager = (ToughViewPager) this.header.findViewById(R.id.event_pager);
        mEventPager.setOnPageChangeListener(new PageChangeHandler());
        mEventAdapter = new EventAdapter(getChildFragmentManager(), mEventPager, this.evtEntries);

        mEventPager.setAdapter(mEventAdapter);
    }

    private boolean isFirstOrLastPage;
    private int currentPageIndex = 0;

    private void initFixedPaper()
    {
        mFixedPager = (ToughViewPager) this.header.findViewById(R.id.fixed_pager);
        mFixedAdapter = new MyPagerAdapter(getChildFragmentManager(), mFixedPager, this.fixEntries);
        mFixedPager.setClipToPadding(false);

        mFixedPager.setAdapter(mFixedAdapter);

        mFixedPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    class LoadPageThread implements Runnable {
        LoadPageThread() {
        }

        public void run() {
            HotelFragment.this.loadPage();
        }
    }

    private void initView(View view)
    {
        txt_todayHotel = (TextView)view.findViewById(R.id.txt_today_hotel);
        txt_tommorrowHotel = (TextView)view.findViewById(R.id.txt_tommorrow_hotel);
        txt_selectDate = (TextView)view.findViewById(R.id.txt_select_date);

        this.txt_todayHotel.setText("오늘의 호텔(" + HotelUtil.GetTodayDate() + ")");
        this.txt_tommorrowHotel.setText("내일의 호텔(" + HotelUtil.GetTomorrowDate() + ")");

        txt_todayHotel.setOnClickListener(this);
        txt_tommorrowHotel.setOnClickListener(this);
        txt_selectDate.setOnClickListener(this);

        txt_todayHotel.setSelected(true);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.txt_today_hotel:
                this.txt_selectDate.setSelected(false);
                this.txt_tommorrowHotel.setSelected(false);
                this.txt_todayHotel.setSelected(true);
                RequestData();
                break;

            case R.id.txt_tommorrow_hotel:
                this.txt_selectDate.setSelected(false);
                this.txt_tommorrowHotel.setSelected(true);
                this.txt_todayHotel.setSelected(false);
                RequestData();
                break;

            case R.id.txt_select_date:
                this.txt_todayHotel.setSelected(false);
                this.txt_tommorrowHotel.setSelected(false);
                this.txt_selectDate.setSelected(true);
                ShowWeekDateDialog();
                break;

            case R.id.dialog_close:
                this.mDialogDown.dismiss();
                break;
        }
    }

    private TextView YearMonthTv;
    private MyCalendar mMcCalendar;


    private boolean bIsFirst;
    private void ShowWeekDateDialog()
    {
        bIsFirst = false;
        if(vSelectDate == null)
        {
            this.vSelectDate = View.inflate(this.getActivity(), R.layout.dialog_select_date, null);
            mMcCalendar = (MyCalendar)this.vSelectDate.findViewById(R.id.mc_calendar);
            mMcCalendar.setOnItemClickLitener(new MyCalendar.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
//                    Toast.makeText(MainActivity.this, mMcCalendar.getTheDayOfSelected(), Toast.LENGTH_SHORT).show();

                    if(bIsFirst == false)
                    {
                        bIsFirst = true;
                        strFromDate = mMcCalendar.getTheDayOfSelectedByRose();
                        Toast.makeText(HotelFragment.this.getActivity(), "CheckOut날짜를 선택해주세요.", Toast.LENGTH_LONG).show();
                    }
                    else if(bIsFirst == true)
                    {
                        strToDate = mMcCalendar.getTheDayOfSelectedByRose();
                        HotelFragment.this.mDialogDown.dismiss();

                        HotelFragment.this.RequestData();
                    }
                }
            });

            this.vSelectDate.findViewById(R.id.dialog_close).setOnClickListener(this);
        }

        this.mDialogDown.show(this.vSelectDate);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class EventAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private ArrayList<EventEntry> evtEntries;
        private ToughViewPager evtPager;


        public EventAdapter(FragmentManager frgm, ToughViewPager mEvtPager, ArrayList<EventEntry> evt_entries) {
            super(frgm);
            this.evtEntries = evt_entries;
            this.evtPager = mEvtPager;
        }

        public android.support.v4.app.Fragment getItem(int position) {

            position %= HotelFragment.PAGES_EVT;
            Map<String, String> evtItem = new HashMap();
            evtItem.put("is_event", ((EventEntry) this.evtEntries.get(position)).getIsEvt());
            evtItem.put("id", ((EventEntry) this.evtEntries.get(position)).getHid());
            evtItem.put("title", ((EventEntry) this.evtEntries.get(position)).getTitle().toString());
            evtItem.put("event_type", ((EventEntry) this.evtEntries.get(position)).getEventType().toString());
            evtItem.put("thumb_img", ((EventEntry) this.evtEntries.get(position)).getThumbImg().toString());
            evtItem.put("thumb_link_action", ((EventEntry) this.evtEntries.get(position)).getThumbImgAction().toString());

            return FrgEvent.newInstance((Serializable) evtItem);
        }

        public int getCount() {
            if (HotelFragment.PAGES_EVT == 1) {
                return 1;
            }
            return HotelFragment.PAGES_EVT * HotelFragment.LOOPS_EVT;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }

        public void removePages() {
            HotelFragment.PAGES_EVT = 0;
            this.evtPager.removeAllViews();
            this.evtEntries.clear();
            this.evtPager.setAdapter(null);
            HotelFragment.this.mEventAdapter = null;
        }
    }

    class PageChangeHandler implements ViewPager.OnPageChangeListener {
        PageChangeHandler() {
        }

        public void onPageSelected(int position) {
            try {
                HotelFragment.nowPosition = position;
                HotelFragment.markNowPosition = position % HotelFragment.PAGES_EVT;
                ((LinearLayout) HotelFragment.this.header.findViewById(R.id.page_mark)).getChildAt(HotelFragment.markPrevPosition).setBackgroundResource(R.drawable.dot_off);
                ((LinearLayout) HotelFragment.this.header.findViewById(R.id.page_mark)).getChildAt(HotelFragment.markNowPosition).setBackgroundResource(R.drawable.dot_on);
                HotelFragment.markPrevPosition = HotelFragment.markNowPosition;
            } catch (Exception e) {
//                HotelUtil.doRestart(FrgMainList.this.getActivity());
            }
        }

        public void onPageScrolled(int position, float positionOffest, int positionOffsetPixels) {
            if (HotelFragment.PAGES_EVT != 0) {
                HotelFragment.markNowPosition = position % HotelFragment.PAGES_EVT;
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    private void initPageMark() {
        ((LinearLayout) this.header.findViewById(R.id.page_mark)).removeAllViews();
        int i = 0;
        while (i < PAGES_EVT) {
            try {
                ImageView iv = new ImageView(getActivity());
                iv.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                if (i == markNowPosition) {
                    iv.setBackgroundResource(R.drawable.dot_on);
                } else {
                    iv.setBackgroundResource(R.drawable.dot_off);
                }
                ((LinearLayout) this.header.findViewById(R.id.page_mark)).addView(iv);
                i++;
            } catch (Exception e) {
//                HotelUtil.doRestart(getActivity());
            }
        }
        markPrevPosition = markNowPosition;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private ArrayList<MainListEntry> fixEntries;
        private ToughViewPager fixedPager;

        public MyPagerAdapter(FragmentManager frgm, ToughViewPager paramFixedPager, ArrayList<MainListEntry> fix_entries) {
            super(frgm);
            this.fixEntries = fix_entries;
            this.fixedPager = paramFixedPager;
        }

        public android.support.v4.app.Fragment getItem(int position) {
            position %= HotelFragment.PAGES;
            Map<String, String> fixItem = new HashMap();
            fixItem.put("hid", ((MainListEntry) this.fixEntries.get(position)).getHid());
            fixItem.put("name", ((MainListEntry) this.fixEntries.get(position)).getName());
            fixItem.put("street1", ((MainListEntry) this.fixEntries.get(position)).getStreet1());
            fixItem.put("landscape", ((MainListEntry) this.fixEntries.get(position)).getLandscape());
            fixItem.put("distance", ((MainListEntry) this.fixEntries.get(position)).getDistance());
            fixItem.put("street2", ((MainListEntry) this.fixEntries.get(position)).getStreet2());
            fixItem.put("code", ((MainListEntry) this.fixEntries.get(position)).getCode());
            fixItem.put("category", ((MainListEntry) this.fixEntries.get(position)).getCategory());
            fixItem.put("event", ((MainListEntry) this.fixEntries.get(position)).getIsEvent());
            fixItem.put("normal_price", ((MainListEntry) this.fixEntries.get(position)).getNormalPrice());
            fixItem.put("sale_price", ((MainListEntry) this.fixEntries.get(position)).getSalePrice());
            fixItem.put("normal_price_avg", ((MainListEntry) this.fixEntries.get(position)).getNormalPriceAvg());
            fixItem.put("sold_out", String.valueOf(((MainListEntry) this.fixEntries.get(position)).getSoldout()));
            fixItem.put("items_quantity", String.valueOf(((MainListEntry) this.fixEntries.get(position)).getItemsQuantity()));
            fixItem.put("sale_rate", ((MainListEntry) this.fixEntries.get(position)).getSaleRate());
            fixItem.put("fthumb1", ((MainListEntry) this.fixEntries.get(position)).getThumb1());
            fixItem.put("fthumb2", ((MainListEntry) this.fixEntries.get(position)).getThumb2());
            return FrgMainFixed.newInstance((Serializable) fixItem);
        }

        public int getCount() {
            if (HotelFragment.PAGES == 1) {
                return 1;
            }
            return HotelFragment.PAGES * HotelFragment.LOOPS;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }

        public void removePages() {
            HotelFragment.PAGES = 0;
            this.fixedPager.removeAllViews();
            this.fixEntries.clear();
            this.fixedPager.setAdapter(null);
            HotelFragment.this.mFixedAdapter = null;
        }
    }
}
