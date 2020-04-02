package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.fragment.Fragment_Faresv;
import com.example.journeyapplicationfyp.fragment.Timetable_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends Fragment implements View.OnClickListener {

    BottomNavigationView bottomNavigationView1;
    private CardView cardView1, cardView2, cardView3;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    Intent intent;

                    switch (menuItem.getItemId()) {

                        case R.id.farenav:
                            selectedFragment = new Fragment_Faresv();

                        case R.id.timetable:
                            intent = new Intent(getActivity(), Timetable_fragment.class);
                            startActivity(intent);
                            getActivity().finish();
                            return true;

                        case R.id.homehere:
                            intent = new Intent(getActivity(), MainActivityHome.class);
                            startActivity(intent);
                            getActivity().finish();
                            return true;
                    }
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Frame_container,
                            selectedFragment).commit();
                    return true;
                }
            };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_searchnv, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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






