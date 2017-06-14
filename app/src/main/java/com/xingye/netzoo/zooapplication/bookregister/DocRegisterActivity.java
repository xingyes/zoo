package com.xingye.netzoo.zooapplication.bookregister;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;

import java.util.ArrayList;
import java.util.Calendar;


public class DocRegisterActivity extends Activity implements View.OnClickListener{

    private TextView    docnamev;
    private TextView    doctitlev;
    private TextView    docinfov;
    private SimpleDraweeView docheadv;
    private DoctorModel doctor;

    private TimeGroup   timeG0 = new TimeGroup();
    private TimeGroup   timeG1 = new TimeGroup();
    public class   TimeGroup
    {
        TextView   keyv;
        TextView   btnv;
//        CheckBox   checkBox;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docregister);
        NaviBar naviBar = (NaviBar)findViewById(R.id.register_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        docnamev = (TextView)findViewById(R.id.doc_name);
        doctitlev = (TextView)findViewById(R.id.doc_title);
        docinfov = (TextView)findViewById(R.id.doc_info);
        docheadv = (SimpleDraweeView)findViewById(R.id.doc_imv);

        View time = findViewById(R.id.time_0);
        timeG0.keyv= (TextView)time.findViewById(R.id.info_key);
        timeG0.btnv= (TextView) time.findViewById(R.id.info_btn);

        time = findViewById(R.id.time_1);
        timeG1.keyv= (TextView)time.findViewById(R.id.info_key);
        timeG1.btnv= (TextView) time.findViewById(R.id.info_btn);
        timeG0.btnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.makeToast(DocRegisterActivity.this,timeG0.keyv.getText().toString());
                ToolUtil.startActivity(DocRegisterActivity.this,RegisterDetailActivity.class);
            }
        });

        timeG1.btnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.makeToast(DocRegisterActivity.this,timeG1.keyv.getText().toString());
                ToolUtil.startActivity(DocRegisterActivity.this,RegisterDetailActivity.class);
            }
        });


        doctor = new DoctorModel();
        doctor.title = "副主任";
        doctor.head = "http://tupian.qqjay.com/u/2011/0813/a0e401332cd034562e85d92339087cb2.jpg";
        doctor.name = "医生A";
        docnamev.setText(doctor.name);
        doctitlev.setText(doctor.title);
        docheadv.setImageURI(Uri.parse(doctor.head));
        docinfov.setText("挂号费 20元");

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
