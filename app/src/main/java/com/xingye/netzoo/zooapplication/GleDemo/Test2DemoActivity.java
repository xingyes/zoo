package com.xingye.netzoo.zooapplication.GleDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xingye.netzoo.xylib.utils.BaseActivity;
import com.xingye.netzoo.zooapplication.R;


public class Test2DemoActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Test2DemoActivity.this,TestDemoActivity.class));
            }
        });
    }


}
