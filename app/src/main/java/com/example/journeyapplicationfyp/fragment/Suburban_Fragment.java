package com.example.journeyapplicationfyp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.activity.Adapter2;
import com.example.journeyapplicationfyp.activity.Adapter3;
import com.example.journeyapplicationfyp.activity.Handlexml;
import com.example.journeyapplicationfyp.object.Data;
import com.example.journeyapplicationfyp.util.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Suburban_Fragment extends Fragment {

    BottomNavigationView bottomNavigationView1;
    private Spinner suburban_spinner;
    private String selectedStop = null;
    private RecyclerView ry4;
    private Handlexml obj;
    private String fullurl = "";
    private String url = "http://api.irishrail.ie/realtime/realtime.asmx/getStationDataByNameXML?StationDesc=";
    private Adapter2 adapter2;
    private List<Data> elements;
    private TextView tv_no_data;
    private TextView tv_no_data2;
    SessionManager sessionManager;


    //Arrivals Data

    private RecyclerView ry2;
    private Adapter3 adapter3;

    public Suburban_Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_suburban, container, false);
        suburban_spinner = rootView.findViewById(R.id.suburban_spinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this.getActivity(), R.array.array_suburban_stops, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suburban_spinner.setAdapter(adapter3);
        initspinnerfooter();
        bottomNavigationView1 = rootView.findViewById(R.id.BNV);

        //ADAPTER INFORMATION 1
        elements = new ArrayList<>();
        ry4 = rootView.findViewById(R.id.ry4);
        ry4.setHasFixedSize(true);
        ry4.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ry4.addItemDecoration(new DividerItemDecoration(ry4.getContext(), DividerItemDecoration.VERTICAL));
        ry2 = rootView.findViewById(R.id.ry2);
        ry2.setHasFixedSize(true);
        ry2.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ry2.addItemDecoration(new DividerItemDecoration(ry2.getContext(), DividerItemDecoration.VERTICAL));
        tv_no_data = rootView.findViewById(R.id.tv_no_data);
        tv_no_data2 = rootView.findViewById(R.id.tv_no_data2);
        sessionManager = new SessionManager();
        return rootView;
    }

    private void initspinnerfooter() {

        suburban_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (parent.getId()) {
                    case R.id.suburban_spinner:
                        selectedStop = selectedItem;
                        fullurl = url + selectedItem;
                        suburbanRail();
                        sessionManager.setStationName(selectedItem);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void suburbanRail() {

        obj = new Handlexml(fullurl);
        obj.fetch();
        while (obj.parsingComplete) ;
        List<Data> elements = obj.elements;
        List<Data> elementsArrivals = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            if (selectedStop.equalsIgnoreCase(elements.get(i).getDestination())) {
                elementsArrivals.add(elements.get(i));
                elements.remove(i);
            }
        }

        if (elements.isEmpty()) {
            tv_no_data.setVisibility(View.VISIBLE);
            ry4.setVisibility(View.GONE);


        } else {
            tv_no_data.setVisibility(View.GONE);
            ry4.setVisibility(View.VISIBLE);


        }
        if (elementsArrivals.isEmpty()) {
            tv_no_data2.setVisibility(View.VISIBLE);
            ry2.setVisibility(View.GONE);


        } else {
            tv_no_data2.setVisibility(View.GONE);
            ry2.setVisibility(View.VISIBLE);
        }


        if (!elements.isEmpty()) {
            adapter2 = new Adapter2(this.getActivity(), elements);
            adapter3 = new Adapter3(this.getActivity(), elementsArrivals);
            ry4.setAdapter(adapter2);
            ry2.setAdapter(adapter3);
            adapter3.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getActivity(), SearchFragment.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





