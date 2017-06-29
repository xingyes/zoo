package com.xingye.netzoo.zooapplication.mycenter;

import android.app.Activity;
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
import com.xingye.netzoo.zooapplication.login.LoginUser;

import java.util.ArrayList;

import io.realm.Realm;


public class MedicHistoryActivity extends Activity{

    private Realm   realm;
    private LoginUser loginUser;
    private RecyclerView medicRecyclerV;
    private ArrayList<MedicRecordModel> medicHistory = new ArrayList<MedicRecordModel>();
    private Result3tvAdapter medicAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        NaviBar naviBar = (NaviBar)findViewById(R.id.result_list_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });
        naviBar.setTitle(R.string.my_medic_history);

        medicRecyclerV = (RecyclerView)findViewById(R.id.result_list);
        medicAdapter = new Result3tvAdapter(new Result3tvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {

            }
        });
        medicRecyclerV.setAdapter(medicAdapter);
        medicRecyclerV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        medicRecyclerV.addItemDecoration(new RecycleViewDivider(
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
        queryMedicHistory();
    }


    private void queryMedicHistory()
    {

        medicHistory.clear();
        for(int i=0; i < 8;i++)
        {
            MedicRecordModel model = new MedicRecordModel();
            model.category = "胸内科" +i;
            model.time = System.currentTimeMillis() - 86400000*i;
            medicHistory.add(model);
        }
        medicAdapter.setDataset(medicHistory);

        medicAdapter.notifyDataSetChanged();
    }
}
