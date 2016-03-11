package com.gitrose.bukinghotel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActPortraitView;
import com.gitrose.bukinghotel.sys.CONFIG;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class FrgPortrait extends Fragment {

    class ViewClick implements OnClickListener {
        ViewClick() {
        }

        public void onClick(View v) {
            CONFIG.useCaption = !CONFIG.useCaption;
            LinearLayout caption_area = (LinearLayout) FrgPortrait.this.getActivity().findViewById(R.id.caption_area);
            TextView caption1 = (TextView) FrgPortrait.this.getActivity().findViewById(R.id.caption1);
            TextView caption2 = (TextView) FrgPortrait.this.getActivity().findViewById(R.id.caption2);
            if (CONFIG.useCaption) {
                if (caption1.length() > 0 || caption2.length() > 0) {
//                    caption_area.startAnimation(AnimationUtils.loadAnimation(FrgPortrait.this.getActivity(), 17432576));
                    caption_area.setVisibility(View.VISIBLE);
                }
            } else if (caption1.length() > 0 || caption2.length() > 0) {
//                caption_area.startAnimation(AnimationUtils.loadAnimation(FrgPortrait.this.getActivity(), 17432577));
                caption_area.setVisibility(View.GONE);
            }
        }
    }

    public static Fragment newInstance(ActPortraitView context, int pos, String url) {
        Bundle b = new Bundle();
        b.putString("url", url);
        return Fragment.instantiate(context, FrgPortrait.class.getName(), b);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (LinearLayout) inflater.inflate(R.layout.fragment_port, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UrlImageViewHelper.setUrlDrawable((ImageView) getView().findViewById(R.id.detail_img), getArguments().getString("url"));
        getView().setOnClickListener(new ViewClick());
    }
}
