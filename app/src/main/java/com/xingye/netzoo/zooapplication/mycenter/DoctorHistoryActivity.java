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
import com.xingye.netzoo.zooapplication.bookregister.DoctorModel;
import com.xingye.netzoo.zooapplication.login.LoginUser;
import com.xingye.netzoo.zooapplication.outcall.DocDetailActivity;
import com.xingye.netzoo.zooapplication.outcall.OutcallDocActivity;

import java.util.ArrayList;

import io.realm.Realm;


public class DoctorHistoryActivity extends Activity{

    private Realm   realm;
    private LoginUser loginUser;
    private RecyclerView doctorRecyclerV;
    private ArrayList<DoctorModel> doctors = new ArrayList<DoctorModel>();
    private Result3tvAdapter doctorAdapter;
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
        naviBar.setTitle(R.string.my_doctors);

        doctorRecyclerV = (RecyclerView)findViewById(R.id.result_list);
        doctorAdapter = new Result3tvAdapter(new Result3tvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {
                Bundle bundle = new Bundle();
                bundle.putString(DocDetailActivity.DOC_ID,"1121212");
                UiUtils.startActivity(DoctorHistoryActivity.this,DocDetailActivity.class,bundle,true);
            }
        });
        doctorRecyclerV.setAdapter(doctorAdapter);
        doctorRecyclerV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        doctorRecyclerV.addItemDecoration(new RecycleViewDivider(
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
        queryMyDoctors();
    }


    private void queryMyDoctors()
    {

        doctors.clear();
        for(int i=0; i < 8;i++)
        {
            DoctorModel doc = new DoctorModel();
            doc.category = "胸内科" +i;
            doc.name = "医生" + i;
            doc.title = "副主任医师";
            doctors.add(doc);
        }
        doctorAdapter.setDataset(doctors);

        doctorAdapter.notifyDataSetChanged();
    }
}
