package com.xingye.netzoo.zooapplication.outcall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.RecycleViewDivider;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;

import java.util.ArrayList;


public class OutcallDocActivity extends Activity implements View.OnClickListener{

    public static final String DOC_CATE = "DOC_CATE";
//    private Calendar calendar;
    private RecyclerView outcallDocListV;
    private OutcallDocAdapter outcallDocAdapter;

    private ArrayList<OutcallDocModel> outcallDocs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null)
        {
            finish();
            return;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcall_doclist);
        NaviBar naviBar = (NaviBar)findViewById(R.id.outcall_doclist_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        naviBar.setTitle(intent.getStringExtra(DOC_CATE));
//        calendar = Calendar.getInstance();
        outcallDocListV = (RecyclerView)findViewById(R.id.outcall_list);
        outcallDocListV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        outcallDocAdapter = new OutcallDocAdapter(new OutcallDocAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {
                Bundle bundle = new Bundle();
                bundle.putString(DocDetailActivity.DOC_ID,"1121212");
                UiUtils.startActivity(OutcallDocActivity.this,DocDetailActivity.class,bundle,true);
            }
        });
        outcallDocListV.setAdapter(outcallDocAdapter);
        outcallDocListV.addItemDecoration(new RecycleViewDivider(
                OutcallDocActivity.this, DividerItemDecoration.VERTICAL,3
                , Color.GRAY));

        outcallDocs = new ArrayList<OutcallDocModel>();
        OutcallDocModel outcall = new OutcallDocModel();
        outcall.doc.title = "副主任";
        outcall.doc.head = "http://v1.qzone.cc/skin/201508/05/17/32/55c1d81b92542141.jpg!200x200.jpg";
        for(int i = 0; i < 5;i++)
        {
            outcall.doc.name = "医生"+i;
            if(i%2==0)
                outcall.timetxt = "周一 上午 10:00-－12:00";
            else
                outcall.timetxt = "周二 下午 13:00-－15:00";
            outcallDocs.add(outcall);
        }
        outcallDocAdapter.setDoclist(outcallDocs);
        outcallDocAdapter.notifyDataSetChanged();



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
