package com.example.quizapplication;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;


public class ClockFragment extends Fragment {

    private LinearLayout mLinearLayout;
    private ClockView mClockView;
    private Context mContext;
    private Handler mHandler;
    private SimpleDateFormat sDateFormat;
    public ClockFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cavas, container, false);
        mLinearLayout = view.findViewById(R.id.canvas_layout);
        int mMargins = getResources().getDimensionPixelSize(R.dimen.padding_margins_value);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(mMargins,mMargins,mMargins,mMargins);
        sDateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        mClockView = new ClockView(mContext,date);
        mHandler = new Handler();
        mHandler.post(updateTime);
        mLinearLayout.addView(mClockView,params);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private Runnable updateTime = new Runnable() {
        public void run() {
            mClockView.refreshClock();
            mHandler.postDelayed(updateTime, 1000);
        }
    };
}
