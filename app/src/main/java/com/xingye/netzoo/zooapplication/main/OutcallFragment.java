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
import android.widget.EditText;
import android.widget.ImageView;

import com.xingye.netzoo.zooapplication.R;


public class OutcallFragment extends BaseFragment{

    private RecyclerView cateListv;
    private ImageView    delv;
    private EditText     searchEdit;

    @Override
    public View initView() {
        mRoot = mContext.getLayoutInflater().inflate(R.layout.fragment_outcall,null);
        delv = (ImageView)mRoot.findViewById(R.id.search_del);
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

        cateListv = (RecyclerView)mRoot.findViewById(R.id.outcall_list);
        return mRoot;
    }

}
