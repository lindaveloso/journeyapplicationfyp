package com.example.journeyapplicationfyp.activity;

import com.example.journeyapplicationfyp.Data;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Handlexml {
    public volatile boolean parsingComplete = true;
    public List<Data> elements = new ArrayList<>();
    private String fullurl = null;
    private XmlPullParserFactory xmlPullParserObject;

    public Handlexml(String fullurl) {
        this.fullurl = fullurl;
    }


    public void pareXML(XmlPullParser pullParser) {
        int event;

        String DueIn = "";
        String Stationfullname = "";
        String Origin = "";
        String Origintime = "";
        String Destination = "";

        //List<Data> elements = new ArrayList<>();
        try {
            event = pullParser.getEventType();
            //Data data = null;
            boolean flag = false;
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = pullParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        name = pullParser.getName();
                        System.out.println(name);
                        //pullParser.nextTag();
                        if (name.equalsIgnoreCase("objStationData")) {
                            //data = new Data();
                            flag = true;
                        }
                        if (name.equalsIgnoreCase("Stationfullname")) {
                            Stationfullname = pullParser.nextText();
//                            data.setStationfullname(Stationfullname);

                        }

                        if (name.equalsIgnoreCase("Origin")) {
                            Origin = pullParser.nextText();
//                            data.setOrigin(Origin);
                        }
                        if (name.equalsIgnoreCase("Destination")) {
                            Destination = pullParser.nextText();
//                            data.setDirection(Direction);
                        }
                        if (name.equalsIgnoreCase("Origintime")) {
                            Origintime = pullParser.nextText();
//                            data.setStatus(Status);
                        }
                        if (name.equalsIgnoreCase("Duein")) {
                            DueIn = pullParser.nextText();
//                            data.setDueIn(DueIn);
                        }
                        break;
//                    case XmlPullParser.TEXT:
//                        text = pullParser.getText();
//                        break;

                    case XmlPullParser.END_TAG:
//                        if (name.equals(("objStationData"))) {
//                            // Stationfullname = text;
//                            Stationfullname = pullParser.getAttributeValue(null, "value");
//                        } else if (name.equals(("DueIn"))) {
//                            DueIn = pullParser.getAttributeValue(null, "value");
//                        } else {
//
//                        }
                        if (flag && pullParser.getName().equalsIgnoreCase("objStationData")) {
                            Data data = new Data();
                            data.setDueIn(DueIn);
                            data.setOrigin(Origin);
                            data.setStationfullname(Stationfullname);
                            data.setOrigintime(Origintime);
                            data.setDestination(Destination);
                            elements.add(data);

                            flag = false;
                        }
                        break;

                }

                event = pullParser.next();
            }
            // parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return elements;
    }

    public void fetch() {
        Thread thread = new Thread((new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(fullurl);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.setReadTimeout(10000);
                    connect.setConnectTimeout(10000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.connect();
                    InputStream stream = connect.getInputStream();
                    xmlPullParserObject = XmlPullParserFactory.newInstance();
                    XmlPullParser pullParser = xmlPullParserObject.newPullParser();
                    pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    pullParser.setInput(stream, null);
                    pareXML(pullParser);
                    stream.close();
                    parsingComplete = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
        thread.start();
    }


}


