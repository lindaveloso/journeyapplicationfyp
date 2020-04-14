package com.example.journeyapplicationfyp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.journeyapplicationfyp.Data;
import com.example.journeyapplicationfyp.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter3 extends RecyclerView.Adapter<Adapter3.MyViewHolder> {
    private Context context;
    private List<Data> elements = new ArrayList<>();


    public Adapter3(Context context, List<Data> elements) {
        this.context = context;
        this.elements = elements;

    }

    @Override
    public Adapter3.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.tab_mainline_arrivals, parent, false);
        return new Adapter3.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Adapter3.MyViewHolder holder, int position) {
        final Data data = elements.get(position);
        holder.textview_1destinationA.setText(data.getDestination());
        holder.textview_2dueA.setText(data.getDueIn());
        // holder.textview_1origintime.setText(data.getOrigintime());
        //holder.origin_info.setText(data.getOrigin());


    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textview_1destinationA, textview_2dueA, textview_1origintime, from, origin_info;

        public MyViewHolder(View view) {
            super(view);
            textview_1destinationA = view.findViewById(R.id.textview_1destinationA);//stationfullname
            textview_2dueA = view.findViewById(R.id.textview_2dueA);//duetime
            // textview_1origintime = view.findViewById(R.id.textview_1origintime); //origintime
            //from = view.findViewById(R.id.from);//simple text
            //origin_info = view.findViewById(R.id.origin_info);
        }
    }
}

