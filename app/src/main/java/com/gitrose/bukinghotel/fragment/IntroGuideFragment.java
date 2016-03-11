package com.gitrose.bukinghotel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActDealDetail;
import com.gitrose.bukinghotel.activity.IntroActivity;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by GITRose on 1/26/2016.
 */
public class IntroGuideFragment extends BaseFragment {
    private int nBackgroud;
    private String strTitle;
    private int nImage;
    private String strDesc;

    private LinearLayout ll_intro_guide;
    private TextView guideTitle;
    private ImageView iv_guide_icon;
    private ImageView tv_guide_desc;


    public static IntroGuideFragment newInstance(IntroActivity act, int i, String str, int i2, String str2) {

        Bundle bundle = new Bundle();
        bundle.putInt("background", i);
        bundle.putString("title", str);
        bundle.putInt("image", i2);
        bundle.putString("desc", str2);

        IntroGuideFragment fragment = new IntroGuideFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout wrap = (LinearLayout) inflater.inflate(R.layout.fragment_intro_guide, container, false);
        wrap.setBackground(this.getResources().getDrawable(getArguments().getInt("background")));
        ((ImageView)wrap.findViewById(R.id.iv_guide_icon)).setImageResource(getArguments().getInt("image"));
        ((TextView)wrap.findViewById(R.id.tv_guide_title)).setText(getArguments().getString("title"));
        ((TextView)wrap.findViewById(R.id.tv_guide_desc)).setText(getArguments().getString("desc"));

        return wrap;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.nBackgroud = getArguments().getInt("background");
        this.strTitle = getArguments().getString("title");
        this.nImage = getArguments().getInt("image");
        this.strDesc = getArguments().getString("desc");
    }
}