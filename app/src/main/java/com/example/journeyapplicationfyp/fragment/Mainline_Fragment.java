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

import com.example.journeyapplicationfyp.Data;
import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.activity.Handlexml;
import com.example.journeyapplicationfyp.activity.SearchActivity;

import java.util.List;

public class Mainline_Fragment extends Fragment {

    private Spinner tab_mainline_spinner;
    private String selectedStop = null;
    TextView txt1, txt2;
    private String fullurl = "";
    private String url = "http://api.irishrail.ie/realtime/realtime.asmx/getStationDataByNameXML?StationDesc=";
    private Handlexml obj;


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
        tab_mainline_spinner.setAdapter(adapter);
        initspinnerfooter();
        // (AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt1 = rootView.findViewById(R.id.txt1);
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
                        //make sure the animal was already selected during the onCreate
                        if (selectedStop != null) {
                            fullurl = url + selectedItem;
                            //  Toast.makeText(parent.getContext(), "STOP selected is " + fullurl,
                            //  Toast.LENGTH_LONG).show();
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
            txt2.setText(elements.get(0).getDirection());
            txt1.setText(elements.get(0).getStationfullname());
        }

    }

}






