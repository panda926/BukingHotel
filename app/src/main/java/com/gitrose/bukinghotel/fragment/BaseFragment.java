package com.gitrose.bukinghotel.fragment;

/**
 * Created by GITRose on 1/18/2016.
 */
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.BaseFragmentActivity;

public class BaseFragment extends Fragment {
    protected Dialog baseDialog;
    protected boolean isLandscape;
    protected Toast toast;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == 2) {
            this.isLandscape = true;
        } else {
            this.isLandscape = false;
        }
    }

    protected View getViewById(View view, int id) {
        return view.findViewById(id);
    }

    public void startActivityForNew(Intent intent) {
        startActivity(intent);
        animationForNew();
    }

    public void animationForNew() {
        getActivity().overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }

    public void animationForOld() {
        getActivity().overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
    }

    public BaseFragmentActivity getBaseActivity() {
        return (BaseFragmentActivity) getActivity();
    }

    public void animationForBottom() {
        getActivity().overridePendingTransition(R.anim.main_translatey100to0, R.anim.main_translatey0tof100);
    }

    public void animationForOTop() {
        getActivity().overridePendingTransition(R.anim.main_translateyf100to0, R.anim.main_translatey0to100);
    }

    public void showToast(String str, int i, boolean z) {
        BaseFragmentActivity baseActivity = (BaseFragmentActivity) getActivity();
        if (baseActivity != null) {
            if (z) {
                this.toast = Toast.makeText(baseActivity.getApplicationContext(), str, i);
                this.toast.show();
                return;
            }
            Toast.makeText(baseActivity.getApplicationContext(), str, i).show();
        }
    }

    public void onPause() {
        if (this.toast != null) {
            this.toast.cancel();
        }
        super.onPause();
    }
}