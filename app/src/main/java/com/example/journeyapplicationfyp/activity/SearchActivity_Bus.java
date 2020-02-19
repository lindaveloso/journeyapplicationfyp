package com.example.journeyapplicationfyp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.object.Bus;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity_Bus extends AppCompatActivity {

    //the URL having the json data
    ProgressBar progressBar;
    String url1 = "https://data.smartdublin.ie/cgi-bin/rtpi/realtimebusinformation?stopid=";
    String busstop = "";
    String url2 = "&format=json";
    String fullURL = "";
    private ArrayList<Bus> bList;
    private RecyclerView mRecyclerView;
    private Adapter adapter;
    private RequestQueue mRequestQueue;
    private EditText editText;
    private Button search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);
        //ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button search_button = findViewById(R.id.search_button);

        bList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        //RecyclerView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.search_button:
                        EditText editText = findViewById(R.id.editText);
                        busstop = "";
                        busstop = editText.getText().toString();
                        if (busstop != null) {
                            fullURL = url1 + busstop + url2;
                            loadBusList();
                        } else {
                            Toast.makeText(getApplicationContext(), "BIG ERROR !", Toast.LENGTH_SHORT);
                        }
                        break;
                    default:
                        throw new RuntimeException("Unknow button ID");
                }
            }
        });
    }

    private void loadBusList() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            bList.clear();
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);
                                String dueT = result.getString("duetime");
                                String destinatioN = result.getString("destination");
                                String directioN = result.getString("direction");
                                String routE = result.getString("route");

                                bList.add(new Bus(dueT, destinatioN,
                                        directioN, routE));
                            }
                            adapter = new Adapter(SearchActivity_Bus.this, bList);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

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

