package com.example.journeyapplicationfyp.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.journeyapplicationfyp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusSearchFragment extends Fragment {

    ProgressBar progressBar;
    String url1 = "https://data.smartdublin.ie/cgi-bin/rtpi/realtimebusinformation?stopid=";
    String busstop = "";
    String url2 = "&format=json";
    String fullURL = "";
    private EditText editText;
    private Button search_button;

    public BusSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bus_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        DisplayOption1();
        Button search_button = view.findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationDetails(view)) {
                    fullURL = url1 + busstop + url2;
                    BusSearchFragmentDirections.ActionBusSearchFragmentToSearchActivityBusResults action = BusSearchFragmentDirections.actionBusSearchFragmentToSearchActivityBusResults(fullURL);
                    Navigation.findNavController(v).navigate(action);
                }
            }
        });

    }

    private boolean validationDetails(View view) {
        EditText editText = view.findViewById(R.id.editText);
        busstop = "";
        busstop = editText.getText().toString();

        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Empty Field for Dublin Bus Stop", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editText.getText().toString().equals("0")) {
            Toast.makeText(requireContext(), "Not a valid Dublin Bus stop", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void DisplayOption1() {

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(
                    ContextCompat.getColor(
                            requireContext(),
                            R.color.system
                    )
            );

            window.setNavigationBarColor(
                    ContextCompat.getColor(
                            requireContext(),
                            R.color.system
                    ));
        }

    }
}
