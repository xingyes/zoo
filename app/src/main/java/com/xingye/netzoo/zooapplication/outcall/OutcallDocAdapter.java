package com.xingye.netzoo.zooapplication.outcall;

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

public class OutcallDocAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<OutcallDocModel> outcallDocs = new ArrayList<OutcallDocModel>();

    public OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener
    {
        public void onItemClick(View view, Object tag);
    };

    public OutcallDocAdapter(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

    public void setDoclist(ArrayList<OutcallDocModel> list)
    {
        if(outcallDocs.size()>0)
            outcallDocs.clear();
        outcallDocs.addAll(list);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outcall_doctor, parent, false);

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
        ((ViewHolder)holder).namev.setText(outcallDocs.get(position).doc.name);
        ((ViewHolder)holder).titlev.setText(outcallDocs.get(position).doc.title);
        ((ViewHolder)holder).headv.setImageURI(outcallDocs.get(position).doc.head);
        ((ViewHolder)holder).timev.setText(outcallDocs.get(position).timetxt);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return outcallDocs.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namev;
        public TextView titlev;
        public TextView timev;
        public SimpleDraweeView headv;
        public ViewHolder(View view){
            super(view);
            namev = (TextView) view.findViewById(R.id.doc_name);
            titlev = (TextView) view.findViewById(R.id.doc_title);
            headv = (SimpleDraweeView) view.findViewById(R.id.doc_imv);
            timev = (TextView)view.findViewById(R.id.outcall_time);
        }
    }
}
