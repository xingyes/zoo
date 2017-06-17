package com.xingye.netzoo.zooapplication.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;


public class PatientEditActivity extends Activity implements View.OnClickListener{

    public static final String PATIENT_RESULT = "PATIENT_RESULT";
    public class EditGroup
    {
        public TextView keyv;
        public EditText editv;
    }

    private EditGroup  nameGp;
    private EditGroup  phoneGp;
    private EditGroup  IDGp;
    private EditGroup  medicIdGp;

    private PatientModel patientModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientedit);

        setResult(RESULT_CANCELED);
        NaviBar naviBar = (NaviBar)findViewById(R.id.patient_edit_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        Intent intent = getIntent();
        if(intent==null)
        {
            finish();
            return;
        }
        nameGp = new EditGroup();
        phoneGp = new EditGroup();
        IDGp = new EditGroup();
        medicIdGp = new EditGroup();
        View view = findViewById(R.id.name_info);
        nameGp.keyv = (TextView)view.findViewById(R.id.info_key);
        nameGp.editv = (EditText) view.findViewById(R.id.info_edit);
        view = findViewById(R.id.phone_info);
        phoneGp.keyv = (TextView)view.findViewById(R.id.info_key);
        phoneGp.editv = (EditText) view.findViewById(R.id.info_edit);
        view = findViewById(R.id.personid_info);
        IDGp.keyv = (TextView)view.findViewById(R.id.info_key);
        IDGp.editv = (EditText) view.findViewById(R.id.info_edit);
        view = findViewById(R.id.medicid_info);
        medicIdGp.keyv = (TextView)view.findViewById(R.id.info_key);
        medicIdGp.editv = (EditText) view.findViewById(R.id.info_edit);

        nameGp.keyv.setText(R.string.person_name);
        phoneGp.keyv.setText(R.string.phone_num);
        IDGp.keyv.setText(R.string.person_ID);
        medicIdGp.keyv.setText(R.string.medic_ID);

        nameGp.editv.setHint(R.string.hint_person_name);
        phoneGp.editv.setHint(R.string.hint_phone_num);
        IDGp.editv.setHint(R.string.hint_person_ID);
        medicIdGp.editv.setHint(R.string.hint_medic_ID);

        patientModel = intent.getParcelableExtra(PATIENT_RESULT);
        if(patientModel!=null)
        {
            nameGp.editv.setText(patientModel.name);
            phoneGp.editv.setText(patientModel.phone);
            IDGp.editv.setText(patientModel.personid);
            medicIdGp.editv.setText(patientModel.medicid);
        }

        findViewById(R.id.submit_btn).setOnClickListener(this);

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
            case R.id.submit_btn:
                submitEditPatient();
                break;
            default:
                break;
        }
    }


    private void submitEditPatient()
    {
        String name = nameGp.editv.getText().toString();
        if(TextUtils.isEmpty(name)) {
            UiUtils.makeToast(this, "请输入姓名");
            return;
        }
        String phone = phoneGp.editv.getText().toString();
        if(TextUtils.isEmpty(phone)) {
            UiUtils.makeToast(this, "请输入手机号");
            return;
        }
        String personid = IDGp.editv.getText().toString();
        if(TextUtils.isEmpty(personid)) {
            UiUtils.makeToast(this, "请输入身份证号");
            return;
        }
        String medicid = medicIdGp.editv.getText().toString();
        if(TextUtils.isEmpty(medicid)) {
            UiUtils.makeToast(this, "请输入就诊卡号");
            return;
        }

        if(patientModel == null)
            patientModel = new PatientModel();
        patientModel.name = name;
        patientModel.personid = personid;
        patientModel.phone = phone;
        patientModel.medicid = medicid;

        Intent intent = new Intent();
        intent.putExtra(PATIENT_RESULT,patientModel);
        setResult(RESULT_OK,intent);
        finish();
    }
}
