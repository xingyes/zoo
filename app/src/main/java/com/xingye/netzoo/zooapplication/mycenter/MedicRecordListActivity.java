package com.xingye.netzoo.zooapplication.mycenter;

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
import com.xingye.netzoo.zooapplication.bookregister.DoctorModel;
import com.xingye.netzoo.zooapplication.login.LoginUser;

import java.util.ArrayList;

import io.realm.Realm;


public class MedicRecordListActivity extends Activity{

    public static final String MEDIC_RECORD_TYPE = "MEDIC_RECORD_TYPE";
    public static final int    OUT_MEDIC_RECORD = 0;
    public static final int    STAY_MEDIC_RECORD = 1;
    private int         medicRecordType;

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

        medicRecordType = intent.getIntExtra(MEDIC_RECORD_TYPE,OUT_MEDIC_RECORD);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        NaviBar naviBar = (NaviBar)findViewById(R.id.result_list_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });


        naviBar.setTitle(medicRecordType == OUT_MEDIC_RECORD ? R.string.out_medic_record : R.string.stay_medic_record);

        resultRecyclerV = (RecyclerView)findViewById(R.id.result_list);
        resultAdapter = new Result3tvAdapter(new Result3tvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {

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
