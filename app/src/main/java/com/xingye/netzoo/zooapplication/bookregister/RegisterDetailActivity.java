package com.xingye.netzoo.zooapplication.bookregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.main.MainActivity;
import com.xingye.netzoo.zooapplication.patient.PatientEditActivity;
import com.xingye.netzoo.zooapplication.patient.PatientManageActivity;
import com.xingye.netzoo.zooapplication.patient.PatientModel;


public class RegisterDetailActivity extends Activity implements View.OnClickListener {

    public static int CHOOSE_PATIENT = 123;

    public class TextValueGroup {
        TextView keyv;
        TextView valuev;
//        CheckBox   checkBox;
    }

    public TextValueGroup keshiGp;
    public TextValueGroup posGp;
    public TextValueGroup doctGp;
    public TextValueGroup dateGp;
    public TextValueGroup feeGp;

    private TextValueGroup patientGp;
    private PatientModel patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerdetail);
        NaviBar naviBar = (NaviBar) findViewById(R.id.register_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        keshiGp = new TextValueGroup();
        posGp = new TextValueGroup();
        doctGp = new TextValueGroup();
        dateGp = new TextValueGroup();
        feeGp = new TextValueGroup();
        patientGp = new TextValueGroup();
        View gpview = findViewById(R.id.register_keshi);
        keshiGp.keyv = (TextView) gpview.findViewById(R.id.info_key);
        keshiGp.valuev = (TextView) gpview.findViewById(R.id.info_value);

        gpview = findViewById(R.id.keshi_pos);
        posGp.keyv = (TextView) gpview.findViewById(R.id.info_key);
        posGp.valuev = (TextView) gpview.findViewById(R.id.info_value);
        gpview = findViewById(R.id.doc_name);
        doctGp.keyv = (TextView) gpview.findViewById(R.id.info_key);
        doctGp.valuev = (TextView) gpview.findViewById(R.id.info_value);
        gpview = findViewById(R.id.register_date);
        dateGp.keyv = (TextView) gpview.findViewById(R.id.info_key);
        dateGp.valuev = (TextView) gpview.findViewById(R.id.info_value);
        gpview = findViewById(R.id.register_fee);
        feeGp.keyv = (TextView) gpview.findViewById(R.id.info_key);
        feeGp.valuev = (TextView) gpview.findViewById(R.id.info_value);


        keshiGp.keyv.setText(R.string.register_keshi);
        posGp.keyv.setText(R.string.keshi_pos);
        doctGp.keyv.setText(R.string.register_doc);
        dateGp.keyv.setText(R.string.see_doctor_date);
        feeGp.keyv.setText(R.string.register_fee);

        keshiGp.valuev.setText("心血管内科");
        keshiGp.valuev.setVisibility(View.VISIBLE);
        posGp.valuev.setText("3楼A区");
        posGp.valuev.setVisibility(View.VISIBLE);
        doctGp.valuev.setText("好医生A");
        doctGp.valuev.setVisibility(View.VISIBLE);
        dateGp.valuev.setText("2017-10-20");
        dateGp.valuev.setVisibility(View.VISIBLE);
        feeGp.valuev.setText("40元");
        feeGp.valuev.setVisibility(View.VISIBLE);
        feeGp.valuev.setTextColor(ContextCompat.getColor(RegisterDetailActivity.this, R.color.price_red));


        findViewById(R.id.register_btn).setOnClickListener(this);

        patient = new PatientModel();
        patient.name = "大老婆";
        patient.phone = "18000001133";

        gpview = findViewById(R.id.patient_info);
        patientGp.keyv = (TextView) gpview.findViewById(R.id.info_key);
        patientGp.keyv.setText(patient.getBrief());
        patientGp.valuev = (TextView) gpview.findViewById(R.id.info_btn);
        patientGp.valuev.setText(R.string.change);
        patientGp.valuev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.startActivity(RegisterDetailActivity.this, PatientManageActivity.class, CHOOSE_PATIENT, true);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                UiUtils.makeToast(RegisterDetailActivity.this, R.string.register_succ);
                finish();
                UiUtils.startActivity(RegisterDetailActivity.this, MainActivity.class, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_PATIENT && resultCode == RESULT_OK && data != null) {
            PatientModel patient = data.getParcelableExtra(PatientEditActivity.PATIENT_RESULT);
            patientGp.keyv.setText(patient.getBrief());

        } else
            super.onActivityResult(requestCode, resultCode, data);

    }
}
