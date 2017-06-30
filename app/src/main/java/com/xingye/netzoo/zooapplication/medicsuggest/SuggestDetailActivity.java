package com.xingye.netzoo.zooapplication.medicsuggest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.MainCategoryAdapter;
import com.xingye.netzoo.zooapplication.bookregister.SubCategoryAdapter;


public class SuggestDetailActivity extends FragmentActivity implements View.OnClickListener{

    public static final String SUG_CATE = "SUG_CATE";
    private String       sugCate;
    private EditText     symptomEt;
    private EditText     suggestionEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null)
        {
            finish();
            return;
        }

        sugCate = intent.getStringExtra(SUG_CATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_detail);
        NaviBar naviBar = (NaviBar)findViewById(R.id.suggest_detail_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });
        naviBar.setTitle(sugCate);

        suggestionEt = (EditText)findViewById(R.id.suggestion);
        symptomEt = (EditText)findViewById(R.id.symptom);


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
