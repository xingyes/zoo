package com.xingye.netzoo.zooapplication.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingye.netzoo.zooapplication.R;


public class MainActivity extends Activity{

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

    private int tab_idx = R.id.tab_0;
    public static final String TAB_TARGET = "TAB_TARGET";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent =  getIntent();
        if(intent == null)
        {
            finish();
            return;
        }

        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_main);

        tabimv0 = (ImageView) findViewById(R.id.tab_imv_0);
        tabimv1 = (ImageView) findViewById(R.id.tab_imv_1);
        tabimv2 = (ImageView) findViewById(R.id.tab_imv_2);
        tabimv3 = (ImageView) findViewById(R.id.tab_imv_3);
        tabtv0 = (TextView) findViewById(R.id.tab_tv_0);
        tabtv1 = (TextView) findViewById(R.id.tab_tv_1);
        tabtv2 = (TextView) findViewById(R.id.tab_tv_2);
        tabtv3 = (TextView) findViewById(R.id.tab_tv_3);

        fragmentManager = this.getFragmentManager();
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
                        tabimv0.setImageResource(R.mipmap.icon_home_s);
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

        tab_idx = intent.getIntExtra(TAB_TARGET,R.id.tab_0);
        tabClickListener.onClick(findViewById(tab_idx));
    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        if(null == intent)
            super.onNewIntent(intent);
        else
        {
            tab_idx = intent.getIntExtra(TAB_TARGET,R.id.tab_0);
            tabClickListener.onClick(findViewById(tab_idx));
        }

    }
}
