package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends Fragment implements View.OnClickListener {

    private CardView cardView1, cardView2, cardView3;
    BottomNavigationView bottomNavigationView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_searchnv, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActionBar().setTitle("Privet");




        getActivity().findViewById(R.id.card3).setOnClickListener(this);
        getActivity().findViewById(R.id.card2).setOnClickListener(this);
        getActivity().findViewById(R.id.card1).setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.card1:
                intent = new Intent(getActivity(), SearchActivity_Bus.class);
                startActivity(intent);
                break;
            case R.id.card2:
                intent = new Intent(getActivity(), SearchActivity_Tram.class);
                startActivity(intent);
                break;

            case R.id.card3:
                intent = new Intent(getActivity(), SearchActivity_Train.class);
                startActivity(intent);
                break;


        }
    }

}






