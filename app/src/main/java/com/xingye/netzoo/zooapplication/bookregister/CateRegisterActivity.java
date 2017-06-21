package com.xingye.netzoo.zooapplication.bookregister;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.RecycleViewDivider;
import com.xingye.netzoo.zooapplication.R;

import java.util.ArrayList;
import java.util.Calendar;


public class CateRegisterActivity extends Activity implements View.OnClickListener{

    private boolean onlyToday = false;

    private RecyclerView calendarView;
    private RecyclerView.LayoutManager  layoutManager;
    private Calendar calendar;
    private CalendarAdapter  calAdapter;

    private TextView     registerDate;
    private RecyclerView doctorView;
    private DoctorAdapter doctorAdapter;

    private ArrayList<DoctorModel> docList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null)
        {
            finish();
            return;
        }
        onlyToday = intent.getBooleanExtra(BookRegisterActivity.ONLY_TODAY,false);

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
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        calendarView.setLayoutManager(layoutManager);
        calAdapter = new CalendarAdapter(new CalendarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {
                int pos = (Integer)tag;
                if(pos==calAdapter.getChoice())
                    return;
                int delta = pos - calAdapter.getChoice();
                calAdapter.setChoice(pos);
                calendar.add(Calendar.DATE,delta);
                registerDate.setText(ToolUtil.toDate(calendar.getTimeInMillis(), getString(com.xingye.netzoo.xylib.R.string.x_day)));
                calAdapter.notifyDataSetChanged();
            }
        });
        calendarView.setAdapter(calAdapter);
        if(onlyToday)
            calendarView.setVisibility(View.GONE);

        registerDate = (TextView)findViewById(R.id.register_date);
        registerDate.setText(ToolUtil.toDate(calendar.getTimeInMillis(), getString(com.xingye.netzoo.xylib.R.string.x_day)));

        doctorView = (RecyclerView)findViewById(R.id.doctor_list);
        doctorView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        doctorAdapter = new DoctorAdapter(new DoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {
                ToolUtil.startActivity(CateRegisterActivity.this,DocRegisterActivity.class);
            }
        });
        doctorView.setAdapter(doctorAdapter);
        doctorView.addItemDecoration(new RecycleViewDivider(
                CateRegisterActivity.this, DividerItemDecoration.VERTICAL,3
                , Color.GRAY));

        docList = new ArrayList<DoctorModel>();
        DoctorModel doc = new DoctorModel();
        doc.title = "副主任";
        doc.head = "http://v1.qzone.cc/skin/201508/05/17/32/55c1d81b92542141.jpg!200x200.jpg";
        for(int i = 0; i < 5;i++)
        {
            doc.name = "医生"+i;
            docList.add(doc);
        }
        doctorAdapter.setDoclist(docList);
        doctorAdapter.notifyDataSetChanged();



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
