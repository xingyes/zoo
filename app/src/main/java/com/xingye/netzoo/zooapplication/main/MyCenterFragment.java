package com.xingye.netzoo.zooapplication.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingye.netzoo.zooapplication.R;


public class MyCenterFragment extends BaseFragment{


    @Override
    public View initView() {
        mRoot = mContext.getLayoutInflater().inflate(R.layout.fragment_mycenter,null);
        return mRoot;
    }
}
