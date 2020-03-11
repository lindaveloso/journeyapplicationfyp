package com.example.journeyapplicationfyp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;

public class Suburban_Fragment extends Fragment {

    private Suburban_Fragment suburban_fragment;

    public Suburban_Fragment() {

    }

    public static Suburban_Fragment  newInstance() {
        return new Suburban_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_suburban, container, false);

        return root;
    }
}




