package com.xingye.netzoo.zooapplication.hospital;

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


public class HospitalGuideActivity extends Activity implements View.OnClickListener{

    private TextView  outpatientv;
    private TextView  insidemapv;
    private TextView  tohospitalv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitalguide);


        NaviBar naviBar = (NaviBar)findViewById(R.id.hospital_guide_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        View view = findViewById(R.id.outpatient_guide);
        outpatientv = (TextView)view.findViewById(R.id.info_key);
        outpatientv.setText(R.string.outpatient_guide);
        view.setOnClickListener(this);

        view = findViewById(R.id.inside_map);
        insidemapv = (TextView)view.findViewById(R.id.info_key);
        insidemapv.setText(R.string.inside_map);
        view.setOnClickListener(this);

        view = findViewById(R.id.to_hospital);
        tohospitalv = (TextView)view.findViewById(R.id.info_key);
        tohospitalv.setText(R.string.to_hospital_map);
        view.setOnClickListener(this);

    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId())
        {
            case R.id.outpatient_guide:
                bundle.putString(TitleImgActivity.NAV_TXT,getString(R.string.outpatient_guide));
                bundle.putString(TitleImgActivity.TITLE_TXT,getString(R.string.outpatient_guide));
                bundle.putInt(TitleImgActivity.TITLE_IMG,R.mipmap.icon_outpatient);
                bundle.putInt(TitleImgActivity.IMG_RID,R.mipmap.outpation_detail);
                UiUtils.startActivity(HospitalGuideActivity.this,TitleImgActivity.class,bundle,true);
                break;
            case R.id.inside_map:
                bundle.putString(TitleImgActivity.NAV_TXT,getString(R.string.inside_map));
                bundle.putString(TitleImgActivity.TITLE_TXT,getString(R.string.inside_map));
                bundle.putInt(TitleImgActivity.TITLE_IMG,R.mipmap.guide);
                bundle.putInt(TitleImgActivity.IMG_RID,R.mipmap.hospital_map);
                UiUtils.startActivity(HospitalGuideActivity.this,TitleImgActivity.class,bundle,true);
                break;
            case R.id.to_hospital:
                bundle.putString(TitleImgActivity.NAV_TXT,getString(R.string.to_hospital_map));
                bundle.putString(TitleImgActivity.TITLE_TXT,getString(R.string.to_hospital_map));
                bundle.putInt(TitleImgActivity.TITLE_IMG,R.mipmap.map_poi);
                bundle.putString(TitleImgActivity.BRIEF_TXT,getString(R.string.hospital_addr));
                bundle.putInt(TitleImgActivity.IMG_RID,R.mipmap.to_hospital);
                UiUtils.startActivity(HospitalGuideActivity.this,TitleImgActivity.class,bundle,true);
                break;
            default:
                break;
        }
    }



}
