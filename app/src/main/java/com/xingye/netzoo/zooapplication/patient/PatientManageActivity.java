package com.xingye.netzoo.zooapplication.patient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xingye.netzoo.xylib.utils.ui.ItemRemoveRecyclerView;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.OnRemoveRecyclerItemClickLisener;
import com.xingye.netzoo.xylib.utils.ui.RecycleViewDivider;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.CateRegisterActivity;

import java.util.ArrayList;


public class PatientManageActivity extends Activity implements View.OnClickListener{

    public static int PATIENT_EDIT = 32100;
    public static int PATIENT_ADD = 32101;

    private ArrayList<PatientModel> patientList = new ArrayList<PatientModel>();
    private ItemRemoveRecyclerView showRecyclerview;
    private PatientAdapter          patientAdapter;
    private PatientModel            pickPatient;

    private int         editPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientmanage);
        setResult(RESULT_CANCELED);
        NaviBar naviBar = (NaviBar)findViewById(R.id.patient_mang_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        findViewById(R.id.submit_btn).setOnClickListener(this);
        showRecyclerview = (ItemRemoveRecyclerView)findViewById(R.id.patient_list_v);
        showRecyclerview.addItemDecoration(new RecycleViewDivider(
                PatientManageActivity.this, DividerItemDecoration.VERTICAL,3, Color.GRAY));
        showRecyclerview.setOnItemClickListener(new OnRemoveRecyclerItemClickLisener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position< patientList.size()) {
                    editPos = position;
                    PatientModel patientModel = patientList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(PatientEditActivity.PATIENT_RESULT, patientModel);
                    UiUtils.startActivity(PatientManageActivity.this, PatientEditActivity.class,
                            bundle, PATIENT_EDIT, true);
                }
            }

            @Override
            public void onDeleteClick(int position) {
                if(position < patientList.size()) {
                    patientList.remove(position);
                    if(patientAdapter.choice == position)
                        patientAdapter.choice = -1;
                    else if(patientAdapter.choice>position)
                        patientAdapter.choice--;
                }
                patientAdapter.notifyDataSetChanged();
            }
        });
        showRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        PatientModel  patientModel = new PatientModel();
        patientModel.name = "小老婆";
        patientModel.phone = "13091910202";
        patientList.add(patientModel);
        patientAdapter = new PatientAdapter(this,patientList);
        showRecyclerview.setAdapter(patientAdapter);

        findViewById(R.id.add_patient_layout).setOnClickListener(this);


    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_patient_layout:
                UiUtils.startActivity(PatientManageActivity.this,PatientEditActivity.class,
                        PATIENT_ADD,true);
                break;
            case R.id.submit_btn:
                finishWithPickPatient();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PATIENT_ADD && resultCode == RESULT_OK && data!=null)
        {
            PatientModel patient = data.getParcelableExtra(PatientEditActivity.PATIENT_RESULT);
            if(patient!=null) {
                patientList.add(patient);
                patientAdapter.notifyDataSetChanged();
            }

        }
        else if(requestCode == PATIENT_EDIT && resultCode == RESULT_OK && data!=null)
        {
            PatientModel patient = data.getParcelableExtra(PatientEditActivity.PATIENT_RESULT);
            if(patient!=null) {
                patientList.set(editPos, patient);
                patientAdapter.notifyDataSetChanged();
            }
        }
        else
            super.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    public void onBackPressed()
    {
        finishWithPickPatient();
    }

    private void finishWithPickPatient()
    {
        if(patientAdapter.choice >=0 && patientList.size()>0 && patientAdapter.choice<patientList.size()) {
            pickPatient = patientList.get(patientAdapter.choice);
            Intent intent = new Intent();
            intent.putExtra(PatientEditActivity.PATIENT_RESULT, pickPatient);
            setResult(RESULT_OK, intent);
            finish();
        }
        else
            UiUtils.makeToast(this,"请至少选择一位就诊人");

    }
}
