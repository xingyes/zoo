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
import android.widget.ImageView;
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

    private FragmentManager fragmentManager;
    private MainFragment       mainFragment;
    private OutcallFragment    outcallFragment;
    private OnlineFragment     onlineFragment;
    private MyCenterFragment   myCenterFragment;
    private int   lastCheckId = -1;

    private ImageView   tabimv0;
    private ImageView   tabimv1;
    private ImageView   tabimv2;
    private ImageView   tabimv3;
    private TextView    tabtv0;
    private TextView    tabtv1;
    private TextView    tabtv2;
    private TextView    tabtv3;
    private View.OnClickListener  tabClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabimv0 = (ImageView) findViewById(R.id.tab_imv_0);
        tabimv1 = (ImageView) findViewById(R.id.tab_imv_1);
        tabimv2 = (ImageView) findViewById(R.id.tab_imv_2);
        tabimv3 = (ImageView) findViewById(R.id.tab_imv_3);
        tabtv0 = (TextView) findViewById(R.id.tab_tv_0);
        tabtv1 = (TextView) findViewById(R.id.tab_tv_1);
        tabtv2 = (TextView) findViewById(R.id.tab_tv_2);
        tabtv3 = (TextView) findViewById(R.id.tab_tv_3);

        fragmentManager = this.getSupportFragmentManager();
        tabClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = v.getId();
                if (lastCheckId == checkedId)
                    return;

                switch (lastCheckId) {
                    case R.id.tab_0:
                        tabimv0.setImageResource(R.mipmap.icon_home_n);
                        tabtv0.setTextColor(MainActivity.this.getResources().getColor(R.color.c_929292));
                        break;
                    case R.id.tab_1:
                        tabimv1.setImageResource(R.mipmap.icon_chuzhen_n);
                        tabtv1.setTextColor(MainActivity.this.getResources().getColor(R.color.c_929292));
                        break;
                    case R.id.tab_2:
                        tabimv2.setImageResource(R.mipmap.icon_zixun_n);
                        tabtv2.setTextColor(MainActivity.this.getResources().getColor(R.color.c_929292));
                        break;
                    case R.id.tab_3:
                        tabimv3.setImageResource(R.mipmap.icon_my_n);
                        tabtv3.setTextColor(MainActivity.this.getResources().getColor(R.color.c_929292));
                        break;
                    default:
                        break;
                }
                FragmentTransaction ft = fragmentManager.beginTransaction();
                switch (checkedId) {
                    case R.id.tab_0:
                        if (mainFragment == null)
                            mainFragment = new MainFragment();
                        ft.replace(R.id.main_frame, mainFragment);
                        tabimv0.setImageResource(R.mipmap.icon_home_n);
                        tabtv0.setTextColor(MainActivity.this.getResources().getColor(R.color.c_5E4D3F));
                        break;
                    case R.id.tab_1:
                        if (outcallFragment == null)
                            outcallFragment = new OutcallFragment();
                        ft.replace(R.id.main_frame, outcallFragment);
                        tabimv1.setImageResource(R.mipmap.icon_chuzhen_s);
                        tabtv1.setTextColor(MainActivity.this.getResources().getColor(R.color.c_5E4D3F));
                        break;
                    case R.id.tab_2:
                        if (onlineFragment == null)
                            onlineFragment = new OnlineFragment();
                        ft.replace(R.id.main_frame, onlineFragment);
                        tabimv2.setImageResource(R.mipmap.icon_zixun_s);
                        tabtv2.setTextColor(MainActivity.this.getResources().getColor(R.color.c_5E4D3F));
                        break;
                    case R.id.tab_3:
                        if (myCenterFragment == null)
                            myCenterFragment = new MyCenterFragment();
                        ft.replace(R.id.main_frame, myCenterFragment);
                        tabimv3.setImageResource(R.mipmap.icon_my_s);
                        tabtv3.setTextColor(MainActivity.this.getResources().getColor(R.color.c_5E4D3F));
                        break;
                }
                ft.commitAllowingStateLoss();
                lastCheckId = checkedId;
            }
        };

        findViewById(R.id.tab_0).setOnClickListener(tabClickListener);
        findViewById(R.id.tab_1).setOnClickListener(tabClickListener);
        findViewById(R.id.tab_2).setOnClickListener(tabClickListener);
        findViewById(R.id.tab_3).setOnClickListener(tabClickListener);
    }


    private void shiftTabOpt(final int lastCheckId,final int checkId)
    {

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
