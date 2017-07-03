package com.xingye.netzoo.zooapplication.reserve;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ui.AppDialog;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.RadioDialog;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;

import java.util.ArrayList;


public class ReserveChooseActivity extends Activity implements View.OnClickListener{

    private RadioDialog.RadioAdapter radioAdapter;
    private RadioDialog       radioDialog;

    private ArrayList<String> platerStrs;
    private RadioDialog.OnRadioSelectListener plasterRadioListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_choose);


        NaviBar naviBar = (NaviBar)findViewById(R.id.reserve_choose_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });
        View view = findViewById(R.id.meidc_plaster);
        TextView  tv = (TextView)view.findViewById(R.id.info_key);
        tv.setText(R.string.medic_plaster);
        view.setOnClickListener(this);

        view = findViewById(R.id.china_prescirption);
        tv = (TextView)view.findViewById(R.id.info_key);
        tv.setText(R.string.china_prescirption);
        view.setOnClickListener(this);

        view = findViewById(R.id.for_boil);
        tv = (TextView)view.findViewById(R.id.info_key);
        tv.setText(R.string.for_boil);
        view.setOnClickListener(this);

        view = findViewById(R.id.preparation);
        tv = (TextView)view.findViewById(R.id.info_key);
        tv.setText(R.string.preparation);
        view.setOnClickListener(this);

        view = findViewById(R.id.china_patent_medicine);
        tv = (TextView)view.findViewById(R.id.info_key);
        tv.setText(R.string.china_patent_medicine);
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
            case R.id.meidc_plaster:
                if(platerStrs==null) {
                    platerStrs = new ArrayList<String>();
                    platerStrs.add(getString(R.string.spring_plaster));
                    platerStrs.add(getString(R.string.summer_plaster));
                    platerStrs.add(getString(R.string.autumn_plaster));
                    platerStrs.add(getString(R.string.winter_plaster));

                    plasterRadioListener = new RadioDialog.OnRadioSelectListener() {
                        @Override
                        public void onRadioItemClick(int which) {
                            radioAdapter.setPickIdx(-1);
                        Bundle bundle = new Bundle();
                        bundle.putInt(ReserveModel.RESERVE_TYPE,ReserveModel.RESERVE_PLASTER);
                        UiUtils.startActivity(ReserveChooseActivity.this,ReseverDetailActivity.class,bundle,true);
                        }
                    };
                }

                if(null ==radioAdapter)
                    radioAdapter = new RadioDialog.RadioAdapter(this);

                if (null == radioDialog) {
                    radioDialog = new RadioDialog(this,
                            new AppDialog.OnClickListener() {
                                @Override
                                public void onDialogClick(int nButtonId) {
                                    if(nButtonId == Dialog.BUTTON_NEGATIVE)
                                        radioDialog.dismiss();
                                }
                            }, radioAdapter);
                    String title = getString(R.string.medic_plaster);
                    title = getString(R.string.x_choose,title);
                    radioDialog.setProperty(title);
                    radioDialog.setCancelable(true);
                }

                radioDialog.setRadioSelectListener(plasterRadioListener);
                radioAdapter.setList(platerStrs, -1);
                radioAdapter.notifyDataSetChanged();
                radioDialog.show();
                break;
            case R.id.china_prescirption:
                UiUtils.startActivity(ReserveChooseActivity.this,ReservePrescriptionListActivity.class,true);
                break;
            case R.id.for_boil:
//                bundle.putString(TitleImgActivity.NAV_TXT,getString(R.string.to_hospital_map));
//                bundle.putString(TitleImgActivity.TITLE_TXT,getString(R.string.to_hospital_map));
//                bundle.putInt(TitleImgActivity.TITLE_IMG,R.mipmap.map_poi);
//                bundle.putString(TitleImgActivity.BRIEF_TXT,getString(R.string.hospital_addr));
//                bundle.putInt(TitleImgActivity.IMG_RID,R.mipmap.to_hospital);
//                UiUtils.startActivity(ReserveChooseActivity.this,TitleImgActivity.class,bundle,true);
                break;
            case R.id.preparation:
                break;
            case R.id.china_patent_medicine:
                break;
            default:
                break;
        }
    }



}
