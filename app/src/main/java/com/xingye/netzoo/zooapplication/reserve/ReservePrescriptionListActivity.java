package com.xingye.netzoo.zooapplication.reserve;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.RecycleViewDivider;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.hospital.HospitalGuideActivity;
import com.xingye.netzoo.zooapplication.hospital.TitleImgActivity;
import com.xingye.netzoo.zooapplication.login.LoginUser;
import com.xingye.netzoo.zooapplication.mycenter.MedicRecordModel;
import com.xingye.netzoo.zooapplication.mycenter.ReportListActivity;
import com.xingye.netzoo.zooapplication.mycenter.Result3tvAdapter;

import java.util.ArrayList;

import io.realm.Realm;


public class ReservePrescriptionListActivity extends Activity{

    private Realm   realm;
    private LoginUser loginUser;
    private RecyclerView resultRecyclerV;
    private ArrayList<MedicRecordModel> results = new ArrayList<MedicRecordModel>();
    private Result3tvAdapter resultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null)
        {
            finish();
            return;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        NaviBar naviBar = (NaviBar)findViewById(R.id.result_list_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });


        naviBar.setTitle(R.string.china_prescirption);

        resultRecyclerV = (RecyclerView)findViewById(R.id.result_list);
        resultAdapter = new Result3tvAdapter(new Result3tvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {
                if(null == tag || !(tag instanceof Integer))
                    return;
                int pos = (Integer)tag;

                Bundle bundle = new Bundle();
                bundle.putString(TitleImgActivity.NAV_TXT,getString(R.string.china_prescirption));
                bundle.putString(TitleImgActivity.TITLE_TXT,
                        (results.get(pos).get1Txt() + " " + results.get(pos).get3Txt()));
                bundle.putInt(TitleImgActivity.TITLE_IMG,R.mipmap.icon_calendar);

                if(pos%3==0)
                    bundle.putString(TitleImgActivity.IMG_URL,"http://www.sybct.com/Ngwbl/Case25-A.jpg");
                else if(pos%3 == 1)
                    bundle.putString(TitleImgActivity.IMG_URL,"http://s4.sinaimg.cn/large/006fURXhzy6XRvsVJBNb3&690");
                else
                    bundle.putString(TitleImgActivity.IMG_URL,"https://gss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/55e736d12f2eb9386ce6c408d4628535e4dd6f45.jpg");

                UiUtils.startActivity(ReservePrescriptionListActivity.this, ReseverTitleImvSubmitActivity.class,bundle,true);
            }
        });
        resultRecyclerV.setAdapter(resultAdapter);
        resultRecyclerV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        resultRecyclerV.addItemDecoration(new RecycleViewDivider(
                this, DividerItemDecoration.VERTICAL,3,Color.GRAY));



    }


    @Override
    protected void onResume()
    {
        super.onResume();
        if(realm == null)
            realm = Realm.getDefaultInstance();
        loginUser = realm.where(LoginUser.class).findFirst();
        if(null==loginUser || !loginUser.isValid())
        {
            UiUtils.makeToast(this,"请重新登录");
            finish();
        }
        queryMedicRecords();
    }


    private void queryMedicRecords()
    {

        results.clear();
        for(int i=0; i < 8;i++)
        {
            MedicRecordModel record = new MedicRecordModel();
            record.category = "胸内科" +i;
            record.time = System.currentTimeMillis() - 86400000*i;
            results.add(record);
        }
        resultAdapter.setDataset(results);

        resultAdapter.notifyDataSetChanged();
    }
}
