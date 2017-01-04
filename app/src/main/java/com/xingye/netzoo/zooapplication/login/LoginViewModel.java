package com.xingye.netzoo.zooapplication.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.net.JSONRespLogin;
import com.xingye.netzoo.xylib.utils.net.JsonCallback;
import com.xingye.netzoo.xylib.utils.net.OkhttpUtil;
import com.xingye.netzoo.xylib.utils.net.RequestFactory;
import com.xingye.netzoo.zooapplication.BR;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.databinding.LoginHelpBinding;
import com.xingye.netzoo.zooapplication.utils.Config;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by yx on 16/12/27.
 */

public class LoginViewModel extends BaseObservable {

    private static final int VERIFY_CODE_RESEND_TIME = 60;
    private Context mContext;
    private String passwd;

    public ObservableField<Boolean> smsSending;
    public ObservableField<String> user;
    public ObservableField<String> verifycode;
    public ObservableField<String> tips;
    public ObservableField<String> verifyBtn;
    private int  ticDown;
    private Runnable countDownRunnable;
    public static String helpTip;
    public PopupWindow  popupWindow;

    public LoginViewModel(final Context atx)
    {
        mContext = atx;
        user = new ObservableField<String>();
        tips = new ObservableField<String>();
        verifycode = new ObservableField<String>();
        verifyBtn = new ObservableField<String>(mContext.getString(R.string.verify_code_get));
        smsSending = new ObservableField<Boolean>(false);

        passwd = "";
        helpTip = mContext.getString(R.string.get_help_1);
    }

    @Bindable
    public String getPasswd()
    {
        return passwd;
    }
    public void setPasswd(String pwd)
    {
        passwd = pwd;
        notifyPropertyChanged(BR.passwd);
    }

    public void startTic()
    {
        smsSending.set(true);
        ticDown = VERIFY_CODE_RESEND_TIME;
        verifyBtn.set(""+ticDown);
    }


    public boolean ticDown()
    {

        if(ticDown>0)
        {
            ticDown--;
            verifyBtn.set(""+ticDown);
            return true;
        }
        else
        {
            smsSending.set(false);
            ticDown = -1;
            verifyBtn.set(mContext.getString(R.string.verify_code_get));
            return false;
        }
    }


    public void sendSMS(final View view) {
        if (TextUtils.isEmpty(user.get())) {
            tips.set(mContext.getString(R.string.please_input_username));
            return;
        } else if (TextUtils.isEmpty(getPasswd())){
            tips.set(mContext.getString(R.string.pswd_not_empty));
            return;
        }


        RequestBody reqBody = new FormBody.Builder()
                .add("username", user.get())
                .add("password", getPasswd())
                .build();
        Request.Builder rb = RequestFactory.getRequest(Config.URL_SENDSMS, reqBody, null, null);
        if (null == rb) return;



        try {
            OkhttpUtil.request(rb, OkhttpUtil.CACHED_NETWORK_RACE,
                    new JsonCallback<JSONRespLogin>() {

                        @Override
                        public void onFailure(Call call, Exception e) {
                            Object px = Proxy.newProxyInstance(JsonCallback.class.getClassLoader(),
                                    new Class<?>[]{JsonCallback.class},
                                    new InvocationHandler() {
                                        @Override
                                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                            return null;
                                        }
                                    });
                        }

                        @Override
                        public void onResponse(Call call, JSONRespLogin loginResp) throws IOException {
//                            JSONRespLogin base = gson.fromJson(response.body().string(), JSONRespLogin.class);
                            if(loginResp.ext!=null && loginResp.code.equals("0"))
                            {
                                tips.set(loginResp.ext.userName + " " + loginResp.ext.realName +"\n" + loginResp.ext.email);
                                startTickdown(view);
                            }
                            else
                            {
                                tips.set(loginResp.msg);

                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startTickdown(final View view)
    {
        if(null==countDownRunnable)
        {
            countDownRunnable = new Runnable() {
                @Override
                public void run() {
                    if(ticDown())
                    {
                        view.postDelayed(this,1000);
                    }
                }
            };
        }
        startTic();
        view.post(countDownRunnable);
    }


    @BindingAdapter("helptip")
    public static void justSettext(TextView view, String tips)
    {
        int length = tips.length();
        Spannable wordtoSpan = new SpannableString(tips);
        wordtoSpan.setSpan(new ForegroundColorSpan(0xffCE393A), length-3, length-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(wordtoSpan);
    }


/*    popwindow  Databinding about */
    public void showHelpTips(View view,int level) {
        LoginHelpBinding helpbind =  DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.login_help,null,false);
        popupWindow = new PopupWindow(helpbind.getRoot(),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(R.style.DialogAnimation);

        View anchorView = view.getRootView();
        for(int i = 1; i < level && anchorView!=null;i++)
            anchorView = anchorView.getRootView();
        popupWindow.showAtLocation(anchorView,Gravity.BOTTOM,0,0);
    }


    public void dismissHelp()
    {
        popupWindow.dismiss();
        popupWindow = null;
    }

    public void sendEmail()
    {
        Intent ait = new Intent(Intent.ACTION_SEND);
        ait.setData(Uri.parse("mailto:zhouzixiong@jd.com"));
        ait.putExtra(Intent.EXTRA_SUBJECT,"RDM(安卓）登录有问题");
        mContext.startActivity(ait);
    }
}
