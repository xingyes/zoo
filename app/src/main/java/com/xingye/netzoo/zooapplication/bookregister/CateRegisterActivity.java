package com.xingye.netzoo.zooapplication.bookregister;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.zooapplication.R;

import java.util.Calendar;


public class CateRegisterActivity extends Activity implements View.OnClickListener{

    private RecyclerView calendarView;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cateregister);
        NaviBar naviBar = (NaviBar)findViewById(R.id.register_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        calendar = Calendar.getInstance();

        calendarView = (RecyclerView)findViewById(R.id.calendar_pick);


    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onClick(View v) {

    }
}
