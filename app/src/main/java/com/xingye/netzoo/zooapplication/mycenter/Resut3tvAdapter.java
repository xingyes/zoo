package com.xingye.netzoo.zooapplication.mycenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.DoctorModel;

import java.util.ArrayList;

/**
 * Created by yx on 17/6/11.
 */

public class Resut3tvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Base3tvModel > dataset = new ArrayList<Base3tvModel>();

    public OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener
    {
        public void onItemClick(View view, Object tag);
    };

    public Resut3tvAdapter(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

    public void setDataset(ArrayList<? extends Base3tvModel> list)
    {
        if(dataset.size()>0)
            dataset.clear();
        dataset.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_three_txt, parent, false);

        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener)
                    mOnItemClickListener.onItemClick(view,view.getTag());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).tv1.setText(dataset.get(position).get1Txt());
        ((ViewHolder)holder).tv2.setText(dataset.get(position).get2Txt());
        ((ViewHolder)holder).tv3.setText(dataset.get(position).get3Txt());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public ViewHolder(View view){
            super(view);
            tv1 = (TextView) view.findViewById(R.id.txt_1);
            tv2 = (TextView) view.findViewById(R.id.txt_2);
            tv3 = (TextView) view.findViewById(R.id.txt_3);

        }
    }
}
