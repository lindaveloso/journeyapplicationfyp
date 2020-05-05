package com.example.journeyapplicationfyp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.util.SessionManager;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment implements View.OnClickListener {


    public static final String mypreference = "mypref";
    TextView text1, text2, text3;
    SharedPreferences sharedpreferences;
    SessionManager sessionManager;
    ListView list1;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;


    public FavouritesFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_timetables, container, false);
        text2 = rootView.findViewById(R.id.text2);
        text1 = rootView.findViewById(R.id.text1);
        list1 = rootView.findViewById(R.id.list1);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1,
                arrayList);
        list1.setAdapter(adapter);

        text2 = rootView.findViewById(R.id.text2);
        text2.setOnClickListener(this);
        sessionManager = new SessionManager();
        text2.setText(sessionManager.getFavourties());


       /* if (!arrayList.isEmpty()) {
            text2.setVisibility(View.GONE);
            text1.setVisibility(View.GONE);

        }


        sessionManager = new SessionManager();
        arrayList.add(sessionManager.getFavourties());
        adapter.notifyDataSetChanged();*/

        return rootView;
    }


    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.text2:

            case R.id.add:
                Toast.makeText(requireActivity(), "Should appear here", Toast.LENGTH_SHORT).show();

        }

    }
}