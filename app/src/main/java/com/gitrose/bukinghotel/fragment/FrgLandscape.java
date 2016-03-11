package com.gitrose.bukinghotel.fragment;

/**
 * Created by GITRose on 1/20/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.ActDealDetail;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class FrgLandscape extends Fragment {
    private static String HID;
    private static int IDX;

    /* renamed from: com.hotelnow.fragments.FrgLandscape.1 */
    class ViewClick implements OnClickListener {
        ViewClick() {
        }

        public void onClick(View v) {
//            Intent intent = new Intent(FrgLandscape.this.getActivity(), ActPortraitView.class);
//            intent.putExtra("hid", FrgLandscape.HID);
//            intent.putExtra("idx", FrgLandscape.IDX);
//            FrgLandscape.this.startActivity(intent);
//            FrgLandscape.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    public static Fragment newInstance(ActDealDetail context, int pos, String url, String hid) {
        Bundle b = new Bundle();
        b.putString("url", url);
        b.putString("hid", hid);
        return Fragment.instantiate(context, FrgLandscape.class.getName(), b);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        LinearLayout wrap = (LinearLayout) inflater.inflate(R.layout.fragment_port, container, false);
        UrlImageViewHelper.setUrlDrawable((ImageView) wrap.findViewById(R.id.detail_img), getArguments().getString("url"));
        HID = getArguments().getString("hid");
        IDX = ActDealDetail.markNowPosition;
        return wrap;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setOnClickListener(new ViewClick());
    }
}
