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

import java.util.ArrayList;
import java.util.List;

public class Dart_Fragment extends Fragment {
    private Spinner spinner3;
    private String selectedStop = null;
    private Handlexml obj;
    private RecyclerView ry;
    private String fullurl = "";
    private String url = "http://api.irishrail.ie/realtime/realtime.asmx/getStationDataByNameXML?StationDesc=";
    private Adapter2 adapter2;
    private List<Data> elements;
    private RecyclerView ry3;
    private RecyclerView ry0;
    private Adapter3 adapter3;

    public Dart_Fragment() {

    }

    public static Dart_Fragment newInstance() {
        return new Dart_Fragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_dart, container, false);
        spinner3 = rootView.findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.array_dart_stops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);
        initspinnerfooter();
        elements = new ArrayList<>();
        ry3 = rootView.findViewById(R.id.ry3);
        ry3.setHasFixedSize(true);
        ry3.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ry3.addItemDecoration(new DividerItemDecoration(ry3.getContext(), DividerItemDecoration.VERTICAL));
        ry0 = rootView.findViewById(R.id.ry0);
        ry0.setHasFixedSize(true);
        ry0.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ry0.addItemDecoration(new DividerItemDecoration(ry0.getContext(), DividerItemDecoration.VERTICAL));
        return rootView;
    }

    private void initspinnerfooter() {
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (parent.getId()) {
                    case R.id.spinner3:
                        selectedStop = selectedItem;
                        fullurl = url + selectedItem;
                        Irishrail();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Irishrail() {

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
        if (!elements.isEmpty()) {
            adapter2 = new Adapter2(this.getActivity(), elements);
            adapter3 = new Adapter3(this.getActivity(), elementsArrivals);
            ry3.setAdapter(adapter2);
            ry0.setAdapter(adapter3);
            adapter2.notifyDataSetChanged();
            adapter3.notifyDataSetChanged();

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


