package com.example.journeyapplicationfyp.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
import com.example.journeyapplicationfyp.fragment.Fragment_Faresv;
import com.example.journeyapplicationfyp.fragment.Fragment_Homev;
import com.example.journeyapplicationfyp.object.Bus;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity_Bus extends AppCompatActivity {


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
    BottomNavigationView bottomNavigationView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        bottomNavigationView1 = findViewById(R.id.BNV);
        bottomNavigationView1.setOnNavigationItemSelectedListener(navListener);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        DisplayOption1();


        Button search_button = findViewById(R.id.search_button);

        bList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

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

    private void DisplayOption1() {

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(
                    ContextCompat.getColor(
                            getApplicationContext(),
                            R.color.luas_purple_statusbar
                    )
            );

            window.setNavigationBarColor(
                    ContextCompat.getColor(
                            getApplicationContext(),
                            R.color.luas_purple_statusbar
                    ));
        }

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
                                String routE = result.getString("route");
                                String destinatioN = result.getString("destination");
                                String dueT = result.getString("duetime");



                                bList.add(new Bus(routE,destinatioN,dueT));
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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;


                    switch (menuItem.getItemId()) {

                        case R.id.homenav:
                            selectedFragment = new Fragment_Homev();
                            break;

                        case R.id.searchnav:
                            selectedFragment = new SearchActivity();
                            break;

                        case R.id.routenav:
                            Intent intent = new Intent(SearchActivity_Bus.this, MapActivity_Routesnv.class);
                            startActivity(intent);
                            finish();
                            return true;

                        case R.id.farenav:
                            selectedFragment = new Fragment_Faresv();


                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.Frame_container,
                            selectedFragment).commit();
                    return true;


                }


            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolsbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

           /* case R.id.signmeout:
                FirebaseAuth.getInstance().signOut();
                finish();
                //startActivity(new Intent(this, MainActivityLogin.class));
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }


}

