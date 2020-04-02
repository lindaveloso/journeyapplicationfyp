package com.example.journeyapplicationfyp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.activity.SearchActivity;

public class Mainline_Fragment extends Fragment {
    private Spinner spinner1;

    public Mainline_Fragment() {

    }
    public static Mainline_Fragment  newInstance() {
        return new Mainline_Fragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_mainline, container, false);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner1 = getActivity().findViewById(R.id.spinner1);
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

    private class OnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String station = parent.getItemAtPosition(position).toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}


