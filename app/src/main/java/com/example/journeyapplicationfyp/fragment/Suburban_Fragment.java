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




  /*  private void LoadIrishRail() {
        XmlPullParserFactory xmlPullParserFactory;
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            InputStream inputStream = getAssets().open("http://api.irishrail.ie/realtime/realtime.asmx/getCurrentTrainsXML");
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);
            Parseme(xmlPullParser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void Parseme(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        ArrayList<Train> trains = new ArrayList<>();
        int eventType = xmlPullParser.getEventType();
        Train currentTrain = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    trains = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = xmlPullParser.getName();
                    if ("objTrainPositions".equals(name)) {
                        // currentTrain = new Train(direction, publicMessage, trainStatus);
                        trains.add(currentTrain);
                    } else if (currentTrain != null) {
                        if ("direction".equals(name)) {
                            currentTrain.direction = xmlPullParser.nextText();
                        } else if ("PublicMessage".equals(name)) {
                            currentTrain.PublicMessage = xmlPullParser.nextText();
                        } else if ("TrainStatus".equals(name)) {
                            currentTrain.TrainStatus = xmlPullParser.nextText();
                        }
                    }
                    break;
            }
            eventType = xmlPullParser.next();
        }
        //  printPlayers(players);
    }
    private void printPlayers(ArrayList<Train> trains) {
        StringBuilder builder = new StringBuilder();
        for (Train train : trains) {
            builder.append(train.TrainStatus).append("\n").
                    append(train.direction).append("\n").
                    append(train.PublicMessage).append("\n\n");
        }
        //textview
        // txt.setText(builder.toString());
    }*/

   /* }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

