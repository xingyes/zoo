package com.xingye.netzoo.zooapplication.login;

import android.app.Activity;
import android.os.Bundle;
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
import com.xingye.netzoo.zooapplication.main.MainActivity;


public class LoginActivity extends Activity implements View.OnClickListener{


    public class EditGroup
    {
        public TextView keyv;
        public EditText editv;
        public ImageView delv;
    }

    private EditGroup nameGp;
    private EditGroup passwdGp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NaviBar naviBar = (NaviBar)findViewById(R.id.login_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nameGp = new EditGroup();
        passwdGp = new EditGroup();
        View view = findViewById(R.id.login_name);
        nameGp.keyv = (TextView)view.findViewById(R.id.info_key);
        nameGp.editv = (EditText)view.findViewById(R.id.info_edit);
        nameGp.delv = (ImageView)view.findViewById(R.id.info_del);
        nameGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameGp.editv.setText("");
            }
        });
        nameGp.keyv.setText(R.string.username);
        nameGp.editv.setHint(R.string.hint_username);
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

        view = findViewById(R.id.login_password);
        passwdGp.keyv = (TextView)view.findViewById(R.id.info_key);
        passwdGp.editv = (EditText)view.findViewById(R.id.info_edit);
        passwdGp.keyv.setText(R.string.password);
        passwdGp.delv = (ImageView)view.findViewById(R.id.info_del);
        passwdGp.delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwdGp.editv.setText("");
            }
        });
        passwdGp.editv.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwdGp.editv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {
                    passwdGp.delv.setVisibility(View.VISIBLE);
                }else
                    passwdGp.delv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
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

        findViewById(R.id.forget_passwd).setOnClickListener(this);
        findViewById(R.id.user_register).setOnClickListener(this);
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
            case R.id.user_register:
                UiUtils.startActivity(LoginActivity.this,UserRegisterActivity.class,true);
                break;
            case R.id.forget_passwd:
                UiUtils.startActivity(LoginActivity.this,ResetPasswdActivity.class,true);
                break;
            case R.id.submit_btn:
                goLogin();
                break;
            case R.id.passwd_visiable:
                if(passwdGp.editv.getTransformationMethod()==PasswordTransformationMethod.getInstance())
                    passwdGp.editv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    passwdGp.editv.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passwdGp.editv.setSelection(passwdGp.editv.length());
            default:
                break;
        }
    }



    private void goLogin()
    {
        String name = nameGp.editv.getText().toString();
        if(TextUtils.isEmpty(name)) {
            UiUtils.makeToast(this, "请输入账号名");
            return;
        }
        String passwd = passwdGp.editv.getText().toString();
        if(TextUtils.isEmpty(passwd)) {
            UiUtils.makeToast(this, "请输入密码");
            return;
        }

        /**
         *  send net request
         */

        UiUtils.startActivity(LoginActivity.this, MainActivity.class,true);
        finish();
    }

}
