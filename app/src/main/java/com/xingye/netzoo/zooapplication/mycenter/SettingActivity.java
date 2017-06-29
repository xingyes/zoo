package com.xingye.netzoo.zooapplication.mycenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ui.CarouseFigureVPAdatper;
import com.xingye.netzoo.xylib.utils.ui.CarouselFigureViewPager;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.hospital.HospitalGuideActivity;
import com.xingye.netzoo.zooapplication.hospital.HospitalInfoActivity;
import com.xingye.netzoo.zooapplication.login.LoginUser;
import com.xingye.netzoo.zooapplication.main.MainActivity;

import io.realm.Realm;


public class SettingActivity extends Activity implements View.OnClickListener{

    private Realm   realm;
    private TextView  quickLoginBtn;
    private LoginUser loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        NaviBar naviBar = (NaviBar)findViewById(R.id.setting_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        View view = findViewById(R.id.version_info);
        TextView textView = (TextView)view.findViewById(R.id.info_key);
        textView.setText(R.string.version_check);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.makeToast(SettingActivity.this,"暂无新版本");
            }
        });

        view = findViewById(R.id.about_us);
        textView = (TextView)view.findViewById(R.id.info_key);
        textView.setText(R.string.about_us);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.startActivity(SettingActivity.this, HospitalInfoActivity.class,true);
            }
        });

        quickLoginBtn = (TextView)findViewById(R.id.submit_btn);
        quickLoginBtn.setOnClickListener(this);

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        if(realm == null)
            realm = Realm.getDefaultInstance();
        loginUser = realm.where(LoginUser.class).findFirst();
        if(null!=loginUser && loginUser.isValid())
            quickLoginBtn.setVisibility(View.VISIBLE);
        else
            quickLoginBtn.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit_btn:
                quickLogin();
                break;
            default:
                break;
        }
    }


    private void quickLogin() {
        if (null != loginUser) {
            realm.beginTransaction();
            loginUser.removeFromRealm();
            realm.commitTransaction();
            if (loginUser == null || !loginUser.isValid())
                finish();
            else
                UiUtils.makeToast(SettingActivity.this, "失败");
        }
    }
}
