package com.xingye.netzoo.xylib.utils;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

public class BaseActivity extends FragmentActivity implements SlidingPaneLayout.PanelSlideListener {

    private SlidingPaneLayout mSlidingPaneLayout;
    private FrameLayout       mContainerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            mSlidingPaneLayout = new SlidingPaneLayout(this);

            //属性
            Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            f_overHang.setAccessible(true);
            f_overHang.set(mSlidingPaneLayout, 0);

            mSlidingPaneLayout.setPanelSlideListener(this);
            mSlidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));
        }catch (Exception e)
        {

        }

        super.onCreate(savedInstanceState);

        if(mSlidingPaneLayout==null)
            return;

        View  leftV =  new View(this);
        leftV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mSlidingPaneLayout.addView(leftV,0);

        mContainerLayout = new FrameLayout(this);
        mContainerLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        mSlidingPaneLayout.addView(mContainerLayout,1);
    }


    @Override
    public void setContentView(int id) {
        setContentView(getLayoutInflater().inflate(id, null));
    }

    @Override
    public void setContentView(View v) {
        setContentView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View v, ViewGroup.LayoutParams params) {
        super.setContentView(mSlidingPaneLayout, params);

        mContainerLayout.removeAllViews();
        mContainerLayout.addView(v, params);
    }


    @Override
    public void onPanelSlide(View panel, float slideOffset) {
    }

    @Override
    public void onPanelOpened(View panel) {
        finish();
    }

    @Override
    public void onPanelClosed(View panel) {}
}
