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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.activity.SearchActivity;

public class Suburban_Fragment extends Fragment {

    private Spinner suburban_spinner;
    private String selectedStop = null;


    public Suburban_Fragment() {

    }

 /*   public static Mainline_Fragment newInstance() {
        return new Mainline_Fragment();
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_suburban, container, false);
        suburban_spinner = rootView.findViewById(R.id.suburban_spinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this.getActivity(), R.array.array_suburban_stops, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suburban_spinner.setAdapter(adapter3);
        initspinnerfooter();
        return rootView;
    }

    private void initspinnerfooter() {

        suburban_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (parent.getId()) {
                    case R.id.suburban_spinner:
                        //make sure the animal was already selected during the onCreate
                        if (selectedStop != null) {
                            Toast.makeText(parent.getContext(), "STOP selected is " + selectedItem,
                                    Toast.LENGTH_LONG).show();
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

//(AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





