package com.example.journeyapplicationfyp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.object.Bus;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context context;
    private ArrayList<Bus> bList;
    private OnItemClickListener mListener;

    public Adapter(Context context, ArrayList<Bus> bList) {
        this.context = context;
        this.bList = bList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void update(ArrayList<Bus> bList) {
        bList.clear();
        bList.addAll(bList);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_buslistview_item, null);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Bus bus = bList.get(position);
        holder.txt1.setText(bus.getRoutE());
        holder.txt2.setText(bus.getDestinatioN());
        holder.txt3.setText(bus.getDueT());
    }

    @Override
    public int getItemCount() {
        return bList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3;

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            txt3 = itemView.findViewById(R.id.txt3);

            itemView.setOnClickListener(new View.OnClickListener
                    () {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}







