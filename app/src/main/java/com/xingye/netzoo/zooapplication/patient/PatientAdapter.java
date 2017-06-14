package com.xingye.netzoo.zooapplication.patient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.xingye.netzoo.xylib.utils.ui.RemoveItemRecyclerViewHolder;
import com.xingye.netzoo.xylib.utils.ui.RemoveRecyclerViewAdapter;
import com.xingye.netzoo.zooapplication.R;

import java.util.ArrayList;

/**
 * Created by yx on 17/6/11.
 */

public class PatientAdapter extends RemoveRecyclerViewAdapter<PatientModel>{

    public int choice = 0;
    public PatientAdapter(Context context, ArrayList<PatientModel> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_check_item, parent, false);
        RemoveItemRecyclerViewHolder parentVH = (RemoveItemRecyclerViewHolder)super.onCreateViewHolder(parent,viewType);

        PatientViewHolder viewHolder = new PatientViewHolder(parentVH,view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PatientViewHolder viewHolder = (PatientViewHolder) holder;

        viewHolder.infov.setText(mList.get(position).getBrief());

        viewHolder.checkv.setTag(position);
        holder.itemView.setTag(position);

        viewHolder.checkv.setOnCheckedChangeListener(null);
        viewHolder.checkv.setChecked(position == choice);
        viewHolder.checkv.setOnCheckedChangeListener(checkListener);
    }


    public CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Object obj = compoundButton.getTag();
            if(!b) {
                choice = -1;
                notifyDataSetChanged();
            }
            else if(null!=obj && obj instanceof Integer)
            {
                int newpos = (Integer)obj;
                if(newpos!=choice) {
                    choice = newpos;
                    notifyDataSetChanged();
                }
            }
        }
    };
}
