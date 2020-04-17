package com.example.journeyapplicationfyp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.object.Data;

import java.util.ArrayList;
import java.util.List;


public class Adapter2 extends RecyclerView.Adapter<Adapter2.MyViewHolder> {

    private Context context;
    private List<Data> elements = new ArrayList<>();


    public Adapter2(Context context, List<Data> elements) {
        this.context = context;
        this.elements = elements;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.tab_trainlistview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Data data = elements.get(position);
        holder.textview_1destination.setText(data.getDestination());
        holder.textview_2due.setText(data.getDueIn());

    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textview_1destination, textview_2due;

        public MyViewHolder(View view) {
            super(view);
            textview_1destination = view.findViewById(R.id.textview_1destination);
            textview_2due = view.findViewById(R.id.textview_2due);

        }
    }
}







