package com.xingye.netzoo.zooapplication.bookregister;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.zooapplication.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yx on 17/6/11.
 */

public class DoctorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DoctorModel> doclist = new ArrayList<DoctorModel>();

    public OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener
    {
        public void onItemClick(View view, Object tag);
    };

    public DoctorAdapter(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

    public void setDoclist(ArrayList<DoctorModel> list)
    {
        doclist.addAll(list);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);

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
        ((ViewHolder)holder).namev.setText(doclist.get(position).name);
        ((ViewHolder)holder).titlev.setText(doclist.get(position).title);
        ((ViewHolder)holder).headv.setImageURI(doclist.get(position).head);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return doclist.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namev;
        public TextView titlev;
        public SimpleDraweeView headv;
        public ViewHolder(View view){
            super(view);
            namev = (TextView) view.findViewById(R.id.doc_name);
            titlev = (TextView) view.findViewById(R.id.doc_title);
            headv = (SimpleDraweeView) view.findViewById(R.id.doc_imv);
        }
    }
}
