package com.xingye.netzoo.zooapplication.outcall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.xylib.utils.DPIUtil;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.RecycleViewDivider;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.DoctorModel;

import java.util.ArrayList;


public class DocDetailActivity extends Activity implements View.OnClickListener{

    public static final String DOC_ID = "DOC_ID";
    public DoctorModel  doctorModel;
    private NaviBar naviBar;

    private SimpleDraweeView  headv;
    private TextView          namev;
    private TextView          titlev;
    private TextView          briefv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null)
        {
            finish();
            return;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docdetail);
        naviBar = (NaviBar)findViewById(R.id.docdetail_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        headv = (SimpleDraweeView)findViewById(R.id.doc_imv);
        ViewGroup.LayoutParams lp = headv.getLayoutParams();
        lp.width = DPIUtil.getWidth();
        lp.height = lp.width;
        headv.setLayoutParams(lp);

        namev = (TextView)findViewById(R.id.doc_name);
        titlev = (TextView)findViewById(R.id.doc_title);
        briefv = (TextView)findViewById(R.id.doc_brief);

        doctorModel = new DoctorModel();
        doctorModel.name = "好医生";
        doctorModel.title = "国家二级教授、主任医师、博士研究生导师";
        doctorModel.brief = "原中国医科大学附属第一医院心内科主任，享受国务院政府特殊津贴待遇。率先在东北地区主持开创世界先进的介入性心脏病学新医疗技术；开创我国现场直播手术表演学术交流会，承担国家科委(973)国家攻关、省重点课题7项。兼任国家心血管中心专家委员会委员、世界高血压联盟(中国)理事等职务。";
        doctorModel.head = "https://doctorimg.quyiyuan.com/prod/doctor/photo/deptimages/1002/deptDoctor201501280214580.JPG";

        naviBar.setTitle(doctorModel.name);
        headv.setImageURI(doctorModel.head);
        namev.setText(doctorModel.name);
        titlev.setText(doctorModel.title);
        briefv.setText(doctorModel.brief);

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
