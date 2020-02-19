package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.object.Train;
import com.google.firebase.auth.FirebaseAuth;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SearchActivity_Train extends AppCompatActivity {

    private ArrayList<Train> trains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        trains = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadIrishRail();
    }

    private void LoadIrishRail() {
        XmlPullParserFactory xmlPullParserFactory;

        try{
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            InputStream inputStream = getAssets().open("http://api.irishrail.ie/realtime/realtime.asmx/getCurrentTrainsXML");
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);
           // Parseme(xmlPullParser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 /*   private void Parseme(XmlPullParser xmlPullParser)throws IOException, XmlPullParserException{
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

                  // if ("objTrainPositions".equals(name)) {
                       // currentTrain = new Train();
                        // trains.add(currentTrain);


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

       /// printPlayers(players);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_exit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.signmeout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivityLogin.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
