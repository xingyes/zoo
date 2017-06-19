package com.xingye.netzoo.zooapplication.login;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;


public class ResetPasswdActivity extends Activity implements View.OnClickListener{


    public class EditGroup
    {
        public TextView keyv;
        public EditText editv;
        public TextView btn;
        public ImageView delv;
    }

    private EditGroup phoneGp;
    private EditGroup verifyGp;

    private EditGroup passwdGp;
    private EditGroup repasswdGp;

    public static final int MSG_COUNTDOWN = 102;
    public int  countdown = -1;
    private Handler handler = new Handler(Looper.myLooper())
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MSG_COUNTDOWN)
            {
                if(countdown==0) {
                    verifyGp.btn.setEnabled(true);
                    verifyGp.btn.setText(R.string.verify_code_get);
                    return;
                }


                if(countdown<0) {
                    countdown = 60;
                    verifyGp.btn.setEnabled(false);
                }

                verifyGp.btn.setText(getResources().getString(R.string.verify_code_resend_x,""+countdown));
                countdown--;
                this.sendEmptyMessageDelayed(MSG_COUNTDOWN,1000);

            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpasswd);
        NaviBar naviBar = (NaviBar)findViewById(R.id.reset_passwd_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        phoneGp = new EditGroup();
        verifyGp = new EditGroup();
        passwdGp = new EditGroup();
        repasswdGp = new EditGroup();

        View view = findViewById(R.id.user_phone);
        phoneGp.keyv = (TextView)view.findViewById(R.id.info_key);
        phoneGp.editv = (EditText)view.findViewById(R.id.info_edit);
        phoneGp.keyv.setText(R.string.phone_num);
        phoneGp.editv.setHint(R.string.hint_phone_num);
        phoneGp.delv = (ImageView)view.findViewById(R.id.info_del);
        phoneGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneGp.editv.setText("");
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



        view = findViewById(R.id.verifycode);
        verifyGp.keyv = (TextView)view.findViewById(R.id.info_key);
        verifyGp.editv = (EditText)view.findViewById(R.id.info_edit);
        verifyGp.btn = (TextView) view.findViewById(R.id.info_del);
        verifyGp.btn.setText(R.string.verify_code_get);
        verifyGp.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessage(MSG_COUNTDOWN);
            }
        });
        verifyGp.keyv.setText(R.string.verify_code);

        view = findViewById(R.id.password);
        passwdGp.keyv = (TextView)view.findViewById(R.id.info_key);
        passwdGp.editv = (EditText)view.findViewById(R.id.info_edit);
        passwdGp.keyv.setText(R.string.password);
        passwdGp.editv.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwdGp.delv = (ImageView)view.findViewById(R.id.info_del);
        passwdGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwdGp.editv.setText("");
            }
        });
        passwdGp.editv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {
                    passwdGp.delv.setVisibility(View.VISIBLE);
                }else
                    passwdGp.delv.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        passwdGp.editv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && passwdGp.editv.length()>0)
                    passwdGp.delv.setVisibility(View.VISIBLE);
                else
                    passwdGp.delv.setVisibility(View.INVISIBLE);
            }
        });
        passwdGp.delv = (ImageView)view.findViewById(R.id.info_del);
        passwdGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwdGp.editv.setText("");
            }
        });

        view = findViewById(R.id.re_password);
        repasswdGp.keyv = (TextView)view.findViewById(R.id.info_key);
        repasswdGp.editv = (EditText)view.findViewById(R.id.info_edit);
        repasswdGp.keyv.setText(R.string.re_password);
        repasswdGp.editv.setTransformationMethod(PasswordTransformationMethod.getInstance());
        repasswdGp.delv = (ImageView)view.findViewById(R.id.info_del);
        repasswdGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repasswdGp.editv.setText("");
            }
        });
        repasswdGp.editv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {
                    repasswdGp.delv.setVisibility(View.VISIBLE);
                }else
                    repasswdGp.delv.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        repasswdGp.editv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && repasswdGp.editv.length()>0)
                    repasswdGp.delv.setVisibility(View.VISIBLE);
                else
                    repasswdGp.delv.setVisibility(View.INVISIBLE);
            }
        });




        findViewById(R.id.submit_btn).setOnClickListener(this);
        findViewById(R.id.passwd_visiable).setOnClickListener(this);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.passwd_visiable:
                if(passwdGp.editv.getTransformationMethod()==PasswordTransformationMethod.getInstance())
                {
                    passwdGp.editv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    repasswdGp.editv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    passwdGp.editv.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    repasswdGp.editv.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                passwdGp.editv.setSelection(passwdGp.editv.length());
                repasswdGp.editv.setSelection(repasswdGp.editv.length());
                break;
            case R.id.submit_btn:
                changePasswd();
                break;
            default:
                break;
        }
    }



    private void changePasswd()
    {
        String phone = phoneGp.editv.getText().toString();
        if(TextUtils.isEmpty(phone)) {
            UiUtils.makeToast(this, "请输入有效手机号码");
            return;
        }
        String verifycode = verifyGp.editv.getText().toString();
        if(TextUtils.isEmpty(verifycode)) {
            UiUtils.makeToast(this, "验证码不能为空");
            return;
        }
        String passwd = passwdGp.editv.getText().toString();
        if(TextUtils.isEmpty(passwd)) {
            UiUtils.makeToast(this, "请输入密码");
            return;
        }
        String repasswd = repasswdGp.editv.getText().toString();
        if(TextUtils.isEmpty(repasswd) || !repasswd.equals(passwd)) {
            UiUtils.makeToast(this, "两次密码输入不一致");
            return;
        }


        /**
         *  send net request
         */

        UiUtils.startActivity(ResetPasswdActivity.this, LoginActivity.class,true);
        finish();
    }

}
