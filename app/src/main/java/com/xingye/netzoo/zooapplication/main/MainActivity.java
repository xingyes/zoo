package com.xingye.netzoo.zooapplication.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.net.JsonCallback;
import com.xingye.netzoo.xylib.utils.net.OkhttpUtil;
import com.xingye.netzoo.xylib.utils.net.RequestFactory;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.utils.Config;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private RadioGroup tabGroup;
    private FragmentManager fragmentManager;
    private MainFragment       mainFragment;
    private OutcallFragment    outcallFragment;
    private OnlineFragment     onlineFragment;
    private MyCenterFragment   myCenterFragment;
    private int   lastCheckId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = this.getSupportFragmentManager();
        tabGroup = (RadioGroup)findViewById(R.id.main_tab);
        tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(lastCheckId == checkedId)
                    return;

                FragmentTransaction ft = fragmentManager.beginTransaction();
                switch (checkedId)
                {
                    case R.id.tab_main:
                        if(mainFragment == null)
                            mainFragment = new MainFragment();
                        ft.replace(R.id.main_frame,mainFragment);
                        break;
                    case R.id.tab_outcall:
                        if(outcallFragment == null)
                            outcallFragment = new OutcallFragment();
                        ft.replace(R.id.main_frame,outcallFragment);
                        break;
                    case R.id.tab_online:
                        if(onlineFragment == null)
                            onlineFragment = new OnlineFragment();
                        ft.replace(R.id.main_frame,onlineFragment);
                        break;
                    case R.id.tab_mycenter:
                        if(myCenterFragment == null)
                            myCenterFragment = new MyCenterFragment();
                        ft.replace(R.id.main_frame,myCenterFragment);
                        break;
                }
                ft.commitAllowingStateLoss();
                lastCheckId = checkedId;
            }
        });
    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onClick(View v) {

    }
}
