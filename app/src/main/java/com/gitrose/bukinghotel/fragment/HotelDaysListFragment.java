package com.gitrose.bukinghotel.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitrose.bukinghotel.R;
import com.gitrose.bukinghotel.activity.BaseFragmentActivity;
import com.gitrose.bukinghotel.util.SaleTime;

/**
 * Created by GITRose on 1/27/2016.
 */
public class HotelDaysListFragment extends BaseFragment {

    private int nPos;
    private View daysBackgroudview;
    private View daysLayout;
    private ANIMATION_STATUS f3436l;
    private ANIMATION_STATE f3437m;
    private ObjectAnimator f3438n;
    private AlphaAnimation f3439o;
    private View[] ViewDays;
    private View[] ViewDays_1;
    private SaleTime saleTime1;
    private SaleTime saleTime2;
    private TextView f3444t;
    private boolean f3445u;
    private int f3446v;
    private Handler handler;

    enum ANIMATION_STATE {
        START,
        END,
        CANCEL
    }

    enum ANIMATION_STATUS {
        SHOW,
        HIDE,
        SHOW_END,
        HIDE_END
    }

    public HotelDaysListFragment() {
        this.f3436l = ANIMATION_STATUS.HIDE_END;
        this.f3437m = ANIMATION_STATE.END;
        this.handler = new C13071(this);
    }

    class C13071 extends Handler {
        final HotelDaysListFragment f3392a;

        C13071(HotelDaysListFragment hotelDaysListFragment) {
            this.f3392a = hotelDaysListFragment;
        }

        public void handleMessage(Message message) {
//            BaseFragmentActivity baseActivity = (BaseFragmentActivity) this.f3392a.getActivity();
//            if (baseActivity != null && !baseActivity.isFinishing()) {
//                switch (message.what) {
//                    case 1:
//                        this.f3392a.m2388h();
//                    default:
//                }
//            }
        }
    }

    private void m2384f() {
//        m2195c();
//        if (this.f3446v != 3) {
//            this.f3446v = 0;
//            m2383e();
//        }
//        if (this.f3438n != null) {
//            if (this.f3438n.isRunning()) {
//                this.f3438n.cancel();
//            }
//            this.f3438n.removeAllListeners();
//            this.f3438n = null;
//        }
//        this.f3434j.setAnimation(null);
//        this.f3435k.setAnimation(null);
//        this.f3434j.setVisibility(View.GONE);
//        if (Util.isOverAPI12()) {
//            ((RelativeLayout.LayoutParams) this.f3435k.getLayoutParams()).topMargin = 0;
//            this.f3435k.setVisibility(View.INVISIBLE);
//            this.f3435k.setTranslationY((float) (-this.nPos));
//            setActionBarAnimationLock(false);
//        } else {
//            this.f3435k.setVisibility(View.GONE);
//        }
//        this.f3436l = ANIMATION_STATUS.HIDE_END;
    }
    
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (((BaseFragmentActivity) getActivity()) == null) {
            return null;
        }
        View view =  layoutInflater.inflate(R.layout.fragment_hotel, viewGroup, false);
        this.daysBackgroudview = view.findViewById(R.id.daysBackgroundView);
        this.daysLayout = view.findViewById(R.id.daysLayout);
        this.daysBackgroudview.setOnClickListener(new daysBackgroudClick(this));
        m2384f();
        return view;
    }

    class daysBackgroudClick implements View.OnClickListener {
        final HotelDaysListFragment hotelDaysListFrag;
        
        class C13081 implements Runnable {
            final daysBackgroudClick clickListner;

            C13081(daysBackgroudClick c13092) {
                this.clickListner = c13092;
            }

            public void run() {
//                if (this.clickListner.hotelDaysListFrag.h != null) {
//                    this.clickListner.hotelDaysListFrag.h.selectDay(this.clickListner.hotelDaysListFrag.saleTime1, this.clickListner.hotelDaysListFrag.saleTime2, true);
//                }
            }
        }

        daysBackgroudClick(HotelDaysListFragment hotelDaysListFragment) {
            this.hotelDaysListFrag = hotelDaysListFragment;
        }

        public void onClick(View view) {
            switch (this.hotelDaysListFrag.f3446v) {
                case 1:
                    this.hotelDaysListFrag.handler.postDelayed(new C13081(this), 200);
                    break;
            }
//            this.hotelDaysListFrag.m2391i();
        }
    }
}
