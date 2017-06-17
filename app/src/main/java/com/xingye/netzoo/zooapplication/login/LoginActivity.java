package com.xingye.netzoo.zooapplication.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.net.JsonCallback;
import com.xingye.netzoo.xylib.utils.net.OkhttpUtil;
import com.xingye.netzoo.xylib.utils.net.RequestFactory;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.main.MainActivity;
import com.xingye.netzoo.zooapplication.patient.PatientModel;
import com.xingye.netzoo.zooapplication.utils.Config;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends Activity implements View.OnClickListener{


    public class EditGroup
    {
        public TextView keyv;
        public EditText editv;
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
        nameGp.keyv.setText(R.string.username);
        nameGp.editv.setHint(R.string.hint_username);

        view = findViewById(R.id.login_password);
        passwdGp.keyv = (TextView)view.findViewById(R.id.info_key);
        passwdGp.editv = (EditText)view.findViewById(R.id.info_edit);
        passwdGp.keyv.setText(R.string.password);
        passwdGp.editv.setHint(R.string.hint_password);

        findViewById(R.id.forget_passwd).setOnClickListener(this);
        findViewById(R.id.user_register).setOnClickListener(this);
        findViewById(R.id.submit_btn).setOnClickListener(this);
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
                break;
            case R.id.forget_passwd:
                break;
            case R.id.submit_btn:
                goLogin();
                break;
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
