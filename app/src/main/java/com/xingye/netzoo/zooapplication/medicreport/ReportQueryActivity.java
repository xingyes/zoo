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
import com.xingye.netzoo.zooapplication.hospital.TitleImgActivity;

import java.util.ArrayList;


public class ReportQueryActivity extends FragmentActivity implements View.OnClickListener{


    private ArrayList<ReportModel> resultListData;
    private RadioGroup reportGp;
    private int lastCheckId = -1;

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
                int pos = (Integer)tag;
                ReportModel reportitem = resultListData.get(pos);
                Bundle bundle = new Bundle();
                switch (lastCheckId)
                {
                    case R.id.e_prescription:
                        bundle.putString(TitleImgActivity.NAV_TXT,getString(R.string.e_prescription));
                        break;
                    case R.id.check_report:
                        bundle.putString(TitleImgActivity.NAV_TXT,getString(R.string.check_report));
                        break;
                    case R.id.inspection_report:
                    default:
                        bundle.putString(TitleImgActivity.NAV_TXT,getString(R.string.inspection_report));
                        break;

                }
                bundle.putString(TitleImgActivity.TITLE_TXT,reportitem.formTitle());
                bundle.putInt(TitleImgActivity.TITLE_IMG,R.mipmap.icon_calendar);

                if(pos%3==0)
                    bundle.putString(TitleImgActivity.IMG_URL,"http://www.sybct.com/Ngwbl/Case25-A.jpg");
                else if(pos%3 == 1)
                    bundle.putString(TitleImgActivity.IMG_URL,"http://s4.sinaimg.cn/large/006fURXhzy6XRvsVJBNb3&690");
                else
                    bundle.putString(TitleImgActivity.IMG_URL,"https://gss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/55e736d12f2eb9386ce6c408d4628535e4dd6f45.jpg");
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
        resultListData = new ArrayList<ReportModel>();
        switch (checkedId)
        {
            case R.id.e_prescription:
                resultListData.clear();
                for(int i=1;i<4;i++)
                {
                    rp = new ReportModel();
                    rp.time = System.currentTimeMillis()- 86400000*i;
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
                    rp.time = System.currentTimeMillis()- 86400000*i;
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
                    rp.time = System.currentTimeMillis()- 86400000*i;
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
