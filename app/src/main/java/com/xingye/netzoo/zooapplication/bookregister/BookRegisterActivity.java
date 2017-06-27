package com.xingye.netzoo.zooapplication.bookregister;

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

import java.util.Calendar;


public class BookRegisterActivity extends FragmentActivity implements View.OnClickListener{

    public static final String ONLY_TODAY = "ONLY_TODAY";
    private boolean    onlyToday = false;
    private ListView   firstListV;
    private ListView   subListV;

    private EditText   searchEdit;
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
        Intent intent = getIntent();
        if(intent == null)
        {
            finish();
            return;
        }
        onlyToday = intent.getBooleanExtra(ONLY_TODAY,false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookregister);
        NaviBar naviBar = (NaviBar)findViewById(R.id.register_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        searchEdit = (EditText)findViewById(R.id.search_keyword);

        hostpitalGroup = (RadioGroup) findViewById(R.id.hospital_group);
        hostpitalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            private int lastCheckId = -1;
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                UiUtils.hideSoftInput(BookRegisterActivity.this,searchEdit);
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
                UiUtils.hideSoftInput(BookRegisterActivity.this,searchEdit);
                Bundle bundle = new Bundle();
                bundle.putBoolean(ONLY_TODAY,onlyToday);
                UiUtils.startActivity(BookRegisterActivity.this,CateRegisterActivity.class,bundle,true);
            }
        });

        mainAdapter = new MainCategoryAdapter(this,mainCates);
        firstListV.setAdapter(mainAdapter);
        firstListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UiUtils.hideSoftInput(BookRegisterActivity.this,searchEdit);
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


        calendar = Calendar.getInstance();
        registerDayV = (TextView)findViewById(R.id.register_date);
        registerDayV.setText(ToolUtil.toDate(calendar.getTimeInMillis(), getString(com.xingye.netzoo.xylib.R.string.x_day)));

        datePicker = (DateTimePickerView) findViewById(R.id.date_picker);
        datePicker.setVisibility(View.GONE);
        datePicker.setDateOnly(true);
        datePicker.setVisibility(View.GONE);
        datePicker.setListener(new DateTimePickerView.OnPickerListener() {

            @Override
            public void onSubmit(String value) {
                registerDayV.setText(value);
            }
        });
        registerDayV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.hideSoftInput(BookRegisterActivity.this, searchEdit);
                if (onlyToday)
                    UiUtils.makeToast(BookRegisterActivity.this, "当日预约不能选择日期");
                else
                    datePicker.setVisibility(View.VISIBLE);
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
