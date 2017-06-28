package com.xingye.netzoo.zooapplication.medicreport;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.RecycleViewDivider;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.BookRegisterActivity;
import com.xingye.netzoo.zooapplication.bookregister.CateRegisterActivity;
import com.xingye.netzoo.zooapplication.bookregister.DocRegisterActivity;
import com.xingye.netzoo.zooapplication.bookregister.DoctorAdapter;
import com.xingye.netzoo.zooapplication.bookregister.DoctorModel;
import com.xingye.netzoo.zooapplication.hospital.TitleImgActivity;

import java.util.ArrayList;


public class ReportQueryActivity extends FragmentActivity implements View.OnClickListener{


    private RadioGroup reportGp;
    private RecyclerView reportList;
    private ReportAdapter  reportAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null)
        {
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportquery);
        NaviBar naviBar = (NaviBar)findViewById(R.id.report_query_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        reportGp = (RadioGroup)findViewById(R.id.report_group);
        reportGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            private int lastCheckId = -1;
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(lastCheckId==checkedId)
                    return;

                fakeReport(checkedId);
                lastCheckId = checkedId;
            }
        });

        reportList = (RecyclerView)findViewById(R.id.report_list);
        reportAdapter = new ReportAdapter(new ReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {
                if(tag==null || !(tag instanceof Integer))
                    return;
                Bundle bundle = new Bundle();
                UiUtils.startActivity(ReportQueryActivity.this, TitleImgActivity.class,bundle,true);

            }
        });
        reportList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        reportList.addItemDecoration(new RecycleViewDivider(
                ReportQueryActivity.this, DividerItemDecoration.VERTICAL,3
                , Color.GRAY));

        reportList.setAdapter(reportAdapter);

        reportGp.check(R.id.e_prescription);

    }

    private void fakeReport(int checkedId)
    {
        ReportModel rp;
        ArrayList<ReportModel> resultListData = new ArrayList<ReportModel>();
        switch (checkedId)
        {
            case R.id.e_prescription:
                resultListData.clear();
                for(int i=1;i<4;i++)
                {
                    rp = new ReportModel();
                    rp.date = "2017-07-0" +i;
                    rp.title = getString(R.string.e_prescription) + i;
                    resultListData.add(rp);
                }
                reportAdapter.setReportList(resultListData);
                break;
            case R.id.check_report:
                resultListData.clear();
                for(int i=1;i < 5;i++)
                {
                    rp = new ReportModel();
                    rp.date = "2017-08-0" +i;
                    rp.title = getString(R.string.check_report) + i;
                    resultListData.add(rp);
                }
                reportAdapter.setReportList(resultListData);
                break;
            case R.id.inspection_report:
                resultListData.clear();
                for(int i=3;i<7;i++)
                {
                    rp = new ReportModel();
                    rp.date = "2017-09-0" +i;
                    rp.title = getString(R.string.inspection_report) + i;
                    resultListData.add(rp);
                }
                reportAdapter.setReportList(resultListData);
            default:
                break;
        }

        reportAdapter.notifyDataSetChanged();

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
