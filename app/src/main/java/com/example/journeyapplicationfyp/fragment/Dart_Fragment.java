package com.example.journeyapplicationfyp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;

public class Dart_Fragment extends Fragment {
    private Dart_Fragment dart_fragment;

    public Dart_Fragment() {

    }

    public static Dart_Fragment newInstance() {
        return new Dart_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_dart, container, false);

        return root;
    }
}


