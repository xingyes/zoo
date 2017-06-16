package com.xingye.netzoo.zooapplication.hospital;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ui.CarouseFigureVPAdatper;
import com.xingye.netzoo.xylib.utils.ui.CarouselFigureViewPager;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.main.MainFragment;
import com.xingye.netzoo.zooapplication.patient.PatientModel;


public class HospitalInfoActivity extends Activity implements View.OnClickListener{

    private CarouselFigureViewPager  bannerView;
    private PagerAdapter bannerAdapter;
    private TextView     hospitalTv;

    private TextView     speciallistv;
    private TextView     guidev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitalinfo);


        NaviBar naviBar = (NaviBar)findViewById(R.id.hospital_info_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        hospitalTv = (TextView)findViewById(R.id.hospital_brief);
        bannerView = (CarouselFigureViewPager)findViewById(R.id.hospital_banner);
        bannerAdapter = new CarouseFigureVPAdatper(this, true,
                new CarouseFigureVPAdatper.CarouseFigureImageAdapterListener() {
                    @Override
                    public int getCount() {
                        return 2;
                    }

                    @Override
                    public String getImageUrl(int position) {
                        return "res://" + HospitalInfoActivity.this.getPackageName() +" /" + R.mipmap.banner_photo;
                    }

                    @Override
                    public void onClick(int position) {
                    }
                });

        bannerView.setAdapter(bannerAdapter);

        View view = findViewById(R.id.specialist_go);
        view.setOnClickListener(this);
        speciallistv = (TextView) view.findViewById(R.id.info_key);
        speciallistv.setText(R.string.specialist);
        view = findViewById(R.id.guide_go);
        view.setOnClickListener(this);
        guidev = (TextView) view.findViewById(R.id.info_key);
        guidev.setText(R.string.medic_guide);

        hospitalTv.setText(R.string.hospital_info_detail);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.specialist_go:
                break;
            case R.id.guide_go:
                UiUtils.startActivity(this,HospitalGuideActivity.class,true);
                break;
            default:
                break;
        }
    }



}
