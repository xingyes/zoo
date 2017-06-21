package com.xingye.netzoo.zooapplication.main;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.BookRegisterActivity;
import com.xingye.netzoo.zooapplication.bookregister.CateRegisterActivity;
import com.xingye.netzoo.zooapplication.bookregister.MainCategoryAdapter;
import com.xingye.netzoo.zooapplication.bookregister.SubCategoryAdapter;


public class OutcallFragment extends BaseFragment{

    private ImageView    delv;
    private EditText     searchEdit;

    private RadioGroup hostpitalGroup;
    private ListView   firstListV;
    private ListView   subListV;
    private MainCategoryAdapter mainAdapter;
    private SubCategoryAdapter  subAdapter;

    private String[] mainCates = {"内科","外科","神经内科","中医学","眼科"};
    private String[] subCates = {"呼吸内科","消化内科","神经内科","心血管内科","分泌内科"};

    @Override
    public View initView() {
        mRoot = mContext.getLayoutInflater().inflate(R.layout.fragment_outcall,null);
        delv = (ImageView)mRoot.findViewById(R.id.search_del);
        delv.setVisibility(View.INVISIBLE);
        searchEdit = (EditText)mRoot.findViewById(R.id.search_keyword);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                    delv.setVisibility(View.VISIBLE);
                else
                    delv.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        delv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEdit.setText("");
            }
        });

        hostpitalGroup = (RadioGroup)mRoot.findViewById(R.id.hospital_group);
        hostpitalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            private int lastCheckId = -1;
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                UiUtils.hideSoftInput(OutcallFragment.this.getActivity(),searchEdit);
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

        firstListV = (ListView)mRoot.findViewById(R.id.main_list);
        subListV = (ListView)mRoot.findViewById(R.id.sub_list);
        subListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UiUtils.hideSoftInput(OutcallFragment.this.getActivity(),searchEdit);
                UiUtils.makeToast(OutcallFragment.this.getActivity(),"去医生介绍 和 出诊介绍");
            }
        });

        mainAdapter = new MainCategoryAdapter(getActivity(),mainCates);
        firstListV.setAdapter(mainAdapter);
        firstListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UiUtils.hideSoftInput(OutcallFragment.this.getActivity(),searchEdit);
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


        subAdapter = new SubCategoryAdapter(getActivity());

        mainAdapter.setChoice(0);
        subAdapter.setInfo(subCates);
        subListV.setAdapter(subAdapter);


        return mRoot;
    }

}
