package com.gitrose.bukinghotel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActEventPopup;
import com.gitrose.bukinghotel.util.HotelUtil;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

public class FrgEvent extends Fragment {
    private String event_type;
    private String event_yn;
    private String hid;
    private String landscape;
    private String method;
    private String objStr;
    private String thumb_img;
    private String thumb_link_action;
    private String title;
    private String url;
    private RelativeLayout wrapper;

    /* renamed from: com.hotelnow.fragments.FrgEvent.1 */
    class C06311 implements OnClickListener {
        C06311() {
        }

        public void onClick(View v) {
//            HotelUtil.showKakaoLink(FrgEvent.this.getActivity());
        }
    }

    /* renamed from: com.hotelnow.fragments.FrgEvent.2 */
    class C06322 implements OnClickListener {
        C06322() {
        }

        public void onClick(View v) {
//            HotelUtil.showFacebookFeed(FrgEvent.this.getActivity());
        }
    }

    /* renamed from: com.hotelnow.fragments.FrgEvent.3 */
    class C06333 implements OnClickListener {
        C06333() {
        }

        public void onClick(View v) {
//            Intent intent = new Intent(FrgEvent.this.getActivity(), ActDealDetail.class);
//            intent.putExtra("hid", FrgEvent.this.url);
//            intent.putExtra("evt", WiseTracker.MEMBER);
//            FrgEvent.this.startActivity(intent);
//            FrgEvent.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
//            WiseTracker.setPageIdentity("EVENTP");
//            WiseTracker.setContents("^\uc774\ubca4\ud2b8^\ubb34\ub8cc\uc219\ubc15^" + FrgEvent.this.url);
        }
    }

    /* renamed from: com.hotelnow.fragments.FrgEvent.4 */
    class C06344 implements OnClickListener {
        C06344() {
        }

        public void onClick(View v) {
//            ((ActMain) ActMain.mContext).replaceFragment("FrgSetting");
        }
    }

    /* renamed from: com.hotelnow.fragments.FrgEvent.5 */
    class C06355 implements OnClickListener {
        C06355() {
        }

        public void onClick(View v) {
//            ((ActMain) ActMain.mContext).replaceFragment("FrgBooking");
        }
    }

    /* renamed from: com.hotelnow.fragments.FrgEvent.6 */
    class C06366 implements OnClickListener {
        C06366() {
        }

        public void onClick(View v) {
//            Intent intent = new Intent(FrgEvent.this.getActivity(), ActWebView.class);
//            intent.putExtra(KakaoTalkLinkProtocol.ACTION_URL, FrgEvent.this.url);
//            intent.putExtra("title", FrgEvent.this.title);
//            FrgEvent.this.getActivity().startActivity(intent);
//            FrgEvent.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    /* renamed from: com.hotelnow.fragments.FrgEvent.7 */
    class C06377 implements OnClickListener {
        C06377() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(FrgEvent.this.getActivity(), ActEventPopup.class);
            intent.putExtra("obj", FrgEvent.this.hid);
            FrgEvent.this.startActivity(intent);
            FrgEvent.this.getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_down);
        }
    }

    public FrgEvent() {
        this.title = "";
        this.landscape = "";
        this.thumb_img = "";
        this.event_type = "";
        this.thumb_link_action = "";
        this.event_yn = "";
    }

    public static FrgEvent newInstance(Serializable event) {
        FrgEvent f = new FrgEvent();
        Map<String, String> evt = (Map) event;
        Bundle args = new Bundle();
        args.putString("is_event", (String) evt.get("is_event"));
        args.putString("id", (String) evt.get("id"));
        args.putString("title", (String) evt.get("title"));
        args.putString("event_type", (String) evt.get("event_type"));
        args.putString("thumb_img", (String) evt.get("thumb_img"));
        args.putString("thumb_link_action", (String) evt.get("thumb_link_action"));
        f.setArguments(args);
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        JSONException e;
        if (container == null) {
            return null;
        }
        this.hid = getArguments().getString("id");
        this.title = getArguments().getString("title");
        this.event_yn = getArguments().getString("is_event");
        this.event_type = getArguments().getString("event_type");
        this.thumb_img = getArguments().getString("thumb_img");
        this.thumb_link_action = getArguments().getString("thumb_link_action");

//        try {
//            JSONObject evtObj = new JSONObject(getArguments().getString("obj"));
//            JSONObject jSONObject;
//            try {
//                this.title = evtObj.has("title") ? evtObj.getString("title") : "\ubb34\ub8cc \uc219\ubc15 \uc774\ubca4\ud2b8";
//                this.landscape = evtObj.has("landscape") ? evtObj.getString("landscape") : "";
//                this.thumb_img = evtObj.has("thumb_img") ? evtObj.getString("thumb_img") : "";
//                this.event_type = evtObj.has("evt_type") ? evtObj.getString("evt_type") : "";
//                this.thumb_link_action = evtObj.has("thumb_link_action") ? evtObj.getString("thumb_link_action") : "";
//                jSONObject = evtObj;
//            } catch (JSONException e2) {
//                e = e2;
//                jSONObject = evtObj;
//                e.printStackTrace();
//                if (this.event_yn == "Y") {
//                    this.wrapper = (RelativeLayout) inflater.inflate(R.layout.row_free_evt, container, false);
//                } else {
//                    this.wrapper = (RelativeLayout) inflater.inflate(R.layout.row_free_other, container, false);
//                }
//                return this.wrapper;
//            }
//        } catch (JSONException e3) {
//            e = e3;
//            e.printStackTrace();
//            if (this.event_yn == "Y") {
//                this.wrapper = (RelativeLayout) inflater.inflate(R.layout.row_free_other, container, false);
//            } else {
//                this.wrapper = (RelativeLayout) inflater.inflate(R.layout.row_free_evt, container, false);
//            }
//            return this.wrapper;
//        }


        if (this.event_yn == "Y") {
            this.wrapper = (RelativeLayout) inflater.inflate(R.layout.row_free_evt, container, false);
        } else {
            this.wrapper = (RelativeLayout) inflater.inflate(R.layout.row_free_other, container, false);
        }
        return this.wrapper;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UrlImageViewHelper.setUrlDrawable((ImageView) this.wrapper.findViewById(R.id.event_img), this.thumb_img);
        ((TextView) this.wrapper.findViewById(R.id.event_title)).setText(this.title);
        if (this.event_type.equals("a")) {
            try {
                JSONObject obj = new JSONObject(HotelUtil.stringToHTMLString(this.thumb_link_action.split("otelnowevent://")[1]));
                this.method = obj.getString("method");
                this.url = obj.getString("param");
                if (this.method.equals("social_open")) {
                    if (this.url.equals("kakao")) {
                        this.wrapper.setOnClickListener(new C06311());
                    } else if (this.url.equals("facebook")) {
                        this.wrapper.setOnClickListener(new C06322());
                    }
                } else if (this.method.equals("move_hotel")) {
                    this.wrapper.setOnClickListener(new C06333());
                } else if (this.method.equals("move_page")) {
//                    ActMain.changeActionBar(this.url);
                    if (this.url.equals("account")) {
                        this.wrapper.setOnClickListener(new C06344());
                    } else if (this.url.equals("booking")) {
                        this.wrapper.setOnClickListener(new C06355());
                    }
//                    WiseTracker.setPageIdentity("EVENTP");
//                    WiseTracker.setContents("^\uc774\ubca4\ud2b8^" + this.title);
                } else if (this.method.equals("outer_link")) {
                    this.wrapper.setOnClickListener(new C06366());
//                    WiseTracker.setContents("^\uc774\ubca4\ud2b8^ALONE\uc774\ubca4\ud2b8^" + this.title);
                }
//                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("ANDROID_CLICK", "EVENT_BANNER", this.title, null).build());
            } catch (Throwable th) {
//                Toast.makeText(getActivity(), "\uc62c\ubc14\ub978 \ud615\uc2dd\uc758 \uc8fc\uc18c\uac00 \uc544\ub2d9\ub2c8\ub2e4.", Toast.LENGTH_LONG).show();
            }
        } else if (this.event_type.equals("p")) {
            this.wrapper.setOnClickListener(new C06377());
        }
    }
}
