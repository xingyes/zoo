package com.xingye.netzoo.zooapplication.bookregister;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.xylib.utils.ui.DateTimePickerView;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.main.MainFragment;
import com.xingye.netzoo.zooapplication.main.MyCenterFragment;
import com.xingye.netzoo.zooapplication.main.OnlineFragment;
import com.xingye.netzoo.zooapplication.main.OutcallFragment;

import java.util.Calendar;


public class BookRegisterActivity extends FragmentActivity implements View.OnClickListener{


    private ListView   firstListV;
    private ListView   subListV;

    private MainCategoryAdapter mainAdapter;
    private SubCategoryAdapter  subAdapter;
    private RadioGroup hostpitalGroup;

    private String[] mainCates = {"内科","外科","神经内科","中医学","眼科"};
    private String[] subCates = {"呼吸内科","消化内科","神经内科","心血管内科","分泌内科"};

    private TextView    registerDayV;
    private DateTimePickerView datePicker;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookregister);
        NaviBar naviBar = (NaviBar)findViewById(R.id.register_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        hostpitalGroup = (RadioGroup) findViewById(R.id.hospital_group);
        hostpitalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            private int lastCheckId = -1;
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(lastCheckId==checkedId)
                    return;
                switch (checkedId)
                {
                    case R.id.hospital_north:
                        break;
                    case R.id.hospital_south:
                        break;
                    case R.id.hospital_mid:
                    default:
                        break;
                }
                lastCheckId = checkedId;
            }
        });

        hostpitalGroup.check(R.id.hospital_mid);

        firstListV = (ListView)findViewById(R.id.main_list);
        subListV = (ListView)findViewById(R.id.sub_list);
        subListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToolUtil.startActivity(BookRegisterActivity.this,CateRegisterActivity.class);
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

        registerDayV = (TextView)findViewById(R.id.register_date);
        registerDayV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
            }
        });

        calendar = Calendar.getInstance();
        registerDayV.setText(ToolUtil.toDate(calendar.getTimeInMillis(), getString(com.xingye.netzoo.xylib.R.string.x_day)));
        datePicker = (DateTimePickerView)findViewById(R.id.date_picker);
        datePicker.setDateOnly(true);
        datePicker.setVisibility(View.GONE);
        datePicker.setListener(new DateTimePickerView.OnPickerListener() {

            @Override
            public void onSubmit(String value) {
                registerDayV.setText(value);
            }
        });

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
