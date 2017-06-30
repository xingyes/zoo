package com.xingye.netzoo.zooapplication.medicsuggest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.xylib.utils.ui.DateTimePickerView;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.CateRegisterActivity;
import com.xingye.netzoo.zooapplication.bookregister.MainCategoryAdapter;
import com.xingye.netzoo.zooapplication.bookregister.SubCategoryAdapter;

import java.util.Calendar;


public class SuggestCatelistActivity extends FragmentActivity implements View.OnClickListener{

    private ListView   firstListV;
    private ListView   subListV;
    private MainCategoryAdapter mainAdapter;
    private SubCategoryAdapter  subAdapter;

    private String[] mainCates = {"内科","外科","神经内科","中医学","眼科"};
    private String[] subCates = {"呼吸内科","消化内科","神经内科","心血管内科","分泌内科"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null)
        {
            finish();
            return;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_catelist);
        NaviBar naviBar = (NaviBar)findViewById(R.id.suggest_catelist_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        firstListV = (ListView)findViewById(R.id.main_list);
        subListV = (ListView)findViewById(R.id.sub_list);
        subListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(SuggestDetailActivity.SUG_CATE,subCates[i]);
                UiUtils.startActivity(SuggestCatelistActivity.this,SuggestDetailActivity.class,bundle,true);
            }
        });

        mainAdapter = new MainCategoryAdapter(this,mainCates);
        firstListV.setAdapter(mainAdapter);
        firstListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mainAdapter.getChoice() == position)
                    return;

                if(position == 0) {
                    subAdapter.setInfo(subCates);
                    subListV.setAdapter(subAdapter);
                    subAdapter.notifyDataSetChanged();
                }
                else
                    subListV.setAdapter(null);
                mainAdapter.setChoice(position);
                mainAdapter.notifyDataSetChanged();
            }
        });


        subAdapter = new SubCategoryAdapter(this);

        mainAdapter.setChoice(0);
        subAdapter.setInfo(subCates);
        subListV.setAdapter(subAdapter);

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
