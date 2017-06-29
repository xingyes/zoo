package com.xingye.netzoo.zooapplication.online;

import android.view.View;

import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.main.BaseFragment;


public class OnlineFragment extends BaseFragment {


    @Override
    public View initView() {
        mRoot = mContext.getLayoutInflater().inflate(R.layout.fragment_online,null);
        return mRoot;
    }
}
