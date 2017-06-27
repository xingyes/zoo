package com.xingye.netzoo.zooapplication.medicreport;

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

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ReportModel> reportList = new ArrayList<ReportModel>();

    public OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener
    {
        public void onItemClick(View view, Object tag);
    };

    public ReportAdapter(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

    public void setReportList(ArrayList<ReportModel> list)
    {
        if(reportList.size()>0)
            reportList.clear();
        reportList.addAll(list);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_arrow_item, parent, false);

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
        ((ViewHolder)holder).titlev.setText(reportList.get(position).formTitle());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titlev;
        public ViewHolder(View view){
            super(view);
            titlev = (TextView) view.findViewById(R.id.info_key);
        }
    }
}
