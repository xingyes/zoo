package com.xingye.netzoo.zooapplication.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
        public ImageView delv;
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
        nameGp.delv = (ImageView)view.findViewById(R.id.info_func);
        nameGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameGp.editv.setText("");
            }
        });

        view = findViewById(R.id.phone_info);
        phoneGp.keyv = (TextView)view.findViewById(R.id.info_key);
        phoneGp.editv = (EditText) view.findViewById(R.id.info_edit);
        phoneGp.delv = (ImageView)view.findViewById(R.id.info_func);
        phoneGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneGp.editv.setText("");
            }
        });

        view = findViewById(R.id.personid_info);
        IDGp.keyv = (TextView)view.findViewById(R.id.info_key);
        IDGp.editv = (EditText) view.findViewById(R.id.info_edit);
        IDGp.delv = (ImageView)view.findViewById(R.id.info_func);
        IDGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IDGp.editv.setText("");
            }
        });

        view = findViewById(R.id.medicid_info);
        medicIdGp.keyv = (TextView)view.findViewById(R.id.info_key);
        medicIdGp.editv = (EditText) view.findViewById(R.id.info_edit);
        medicIdGp.delv = (ImageView)view.findViewById(R.id.info_func);
        medicIdGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicIdGp.editv.setText("");
            }
        });

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
            phoneGp.editv.setText(patientModel.phone);
            IDGp.editv.setText(patientModel.personid);
            medicIdGp.editv.setText(patientModel.medicid);
            nameGp.editv.setText(patientModel.name);
            nameGp.editv.setSelection(patientModel.name.length());
        }
        // 后设置  TextWatcher


        nameGp.editv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {
                    nameGp.delv.setVisibility(View.VISIBLE);
                }else
                    nameGp.delv.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        nameGp.editv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && nameGp.editv.length()>0)
                    nameGp.delv.setVisibility(View.VISIBLE);
                else
                    nameGp.delv.setVisibility(View.INVISIBLE);
            }
        });
        phoneGp.editv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {
                    phoneGp.delv.setVisibility(View.VISIBLE);
                }else
                    phoneGp.delv.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        phoneGp.editv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && phoneGp.editv.length()>0)
                    phoneGp.delv.setVisibility(View.VISIBLE);
                else
                    phoneGp.delv.setVisibility(View.INVISIBLE);
            }
        });
        IDGp.editv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {
                    IDGp.delv.setVisibility(View.VISIBLE);
                }else
                    IDGp.delv.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        IDGp.editv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && IDGp.editv.length()>0)
                    IDGp.delv.setVisibility(View.VISIBLE);
                else
                    IDGp.delv.setVisibility(View.INVISIBLE);
            }
        });
        medicIdGp.editv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {
                    medicIdGp.delv.setVisibility(View.VISIBLE);
                }else
                    medicIdGp.delv.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        medicIdGp.editv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && medicIdGp.editv.length()>0)
                    medicIdGp.delv.setVisibility(View.VISIBLE);
                else
                    medicIdGp.delv.setVisibility(View.INVISIBLE);
            }
        });

        nameGp.editv.requestFocus();
        UiUtils.showSoftInput(PatientEditActivity.this,nameGp.editv);
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
