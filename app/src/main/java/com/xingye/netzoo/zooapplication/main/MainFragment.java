package com.xingye.netzoo.zooapplication.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xingye.netzoo.xylib.utils.net.JsonCallback;
import com.xingye.netzoo.xylib.utils.net.OkhttpUtil;
import com.xingye.netzoo.xylib.utils.net.RequestFactory;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.utils.Config;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


public class MainFragment extends BaseFragment{


    @Override
    public View initView() {
        mRoot = mContext.getLayoutInflater().inflate(R.layout.fragment_main,null);
        return mRoot;
    }

    @Override
    public void initData() {
//        RequestBody reqBody = new FormBody.Builder().add("username", "aaa").add("password", "bbb").build();
        Request.Builder  rb = RequestFactory.getRequest(Config.URL_ZHIHU,null,null,null);
        if (null == rb) return;

        try {
            OkhttpUtil.request(rb, OkhttpUtil.CACHED_NETWORK_RACE,
                    new JsonCallback<ZhihuModel>(){

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
                        public void onResponse(Call call, ZhihuModel object) throws IOException {
                            int i = 0;
                            i++;
                        }
                    });


//


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
