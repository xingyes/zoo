package com.xingye.netzoo.zooapplication.main.mycenter;

import android.view.View;

import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.login.LoginActivity;
import com.xingye.netzoo.zooapplication.main.BaseFragment;


public class MyCenterFragment extends BaseFragment {


    @Override
    public View initView() {
        mRoot = mContext.getLayoutInflater().inflate(R.layout.fragment_mycenter,null);

        mRoot.findViewById(R.id.go_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.startActivity(MyCenterFragment.this.getActivity(),
                        LoginActivity.class,true);
            }
        });
        return mRoot;
    }
}
