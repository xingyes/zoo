package com.xingye.netzoo.zooapplication.main.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.main.BaseFragment;


public class OnlineFragment extends BaseFragment {


    @Override
    public View initView() {
        mRoot = mContext.getLayoutInflater().inflate(R.layout.fragment_online,null);
        return mRoot;
    }
}
