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

import com.example.journeyapplicationfyp.Data;
import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.activity.Adapter2;
import com.example.journeyapplicationfyp.activity.Adapter3;
import com.example.journeyapplicationfyp.activity.Handlexml;
import com.example.journeyapplicationfyp.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class Mainline_Fragment extends Fragment {

    TextView textview_1destinationA, txt2;
    private Spinner tab_mainline_spinner;
    private String selectedStop = null;
    private String fullurl = "";
    private String url = "http://api.irishrail.ie/realtime/realtime.asmx/getStationDataByNameXML?StationDesc=";
    private Handlexml obj;
    private RecyclerView ry;
    private Adapter2 adapter2;
    private List<Data> elements;


    //Arrivals Data

    private RecyclerView ry2;
    private Adapter3 adapter3;


    public Mainline_Fragment() {

    }

    public static Mainline_Fragment newInstance() {
        return new Mainline_Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_mainline, container, false);
        tab_mainline_spinner = rootView.findViewById(R.id.tab_mainline_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.array_mainline_stops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tab_mainline_spinner.setPrompt("Select a Stop");
        tab_mainline_spinner.setAdapter(adapter);
        initspinnerfooter();

        //ADAPTER INFORMATION 1
        elements = new ArrayList<>();
        ry = rootView.findViewById(R.id.ry);
        ry.setHasFixedSize(true);
        ry.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ry.addItemDecoration(new DividerItemDecoration(ry.getContext(), DividerItemDecoration.VERTICAL));

        //ADAPTER INFORMATION 2
        ry2 = rootView.findViewById(R.id.ry2);
        ry2.setHasFixedSize(true);
        ry2.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ry2.addItemDecoration(new DividerItemDecoration(ry2.getContext(), DividerItemDecoration.VERTICAL));


        // (AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textview_1destinationA = rootView.findViewById(R.id.textview_1destinationA);
        txt2 = rootView.findViewById(R.id.txt2);
        return rootView;
    }

    private void initspinnerfooter() {

        tab_mainline_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (parent.getId()) {
                    case R.id.tab_mainline_spinner:
                        if (selectedStop != null) {
                            fullurl = url + selectedItem;
                            Irishrail();
                        }
                        selectedStop = selectedItem;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

// Dom xml parser
    // Jaxb Marshal
    // For the network call. Please consider AsyncHttp or other async lib volleys


    private void Irishrail() {
        obj = new Handlexml(fullurl);
        obj.fetch();

        while (obj.parsingComplete) ;

        List<Data> elements = obj.elements;
        if (!elements.isEmpty()) {
            adapter2 = new Adapter2(this.getActivity(), elements);
            ry.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();

        }
    }

}


//if what is selected in the spinner is the same as the destination in array than
//put it in the arrival recyclerview ry2 to display else the rest into the  ry.
//obj.elements holds everything so if obj.elements.getDirection() == selectedStop then put in ry2











