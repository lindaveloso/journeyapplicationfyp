package com.example.journeyapplicationfyp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.journeyapplicationfyp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RTPINavFragment extends Fragment implements  View.OnClickListener {
    private CardView cardView1, cardView2, cardView3;

    public RTPINavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_r_t_p_i_nav, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardView1 = view.findViewById(R.id.card1);
        cardView2 = view.findViewById(R.id.card2);
        cardView3 = view.findViewById(R.id.card3);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.card1:
                Navigation.findNavController(v).navigate(R.id.busSearchFragment);
                break;
            case R.id.card2:

                //   Navigation.findNavController(v).navigate(R.id.searchActivity_Tram);

                break;
            case R.id.card3:
                Navigation.findNavController(v).navigate(R.id.searchActivity_Train);
                break;
        }

    }
}
