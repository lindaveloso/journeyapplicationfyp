/*package com.example.journeyapplicationfyp.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;*/
/*import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.activity.Adapter;
import com.example.journeyapplicationfyp.activity.SearchActivityBus_resultsArgs;
import com.example.journeyapplicationfyp.object.Bus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivityBus_results extends Fragment {


    private ArrayList<Bus> bList;
    private RecyclerView mRecyclerView;
    private Adapter adapter;
    private RequestQueue mRequestQueue;
    private EditText editText;
    private String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        SearchActivityBus_resultsArgs args = SearchActivityBus_resultsArgs.fromBundle(bundle);
        url = args.getUrl();
        return inflater.inflate(R.layout.activity_busresults, container, false);
    }*/

  /*  @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(requireContext());
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        jsonparsemethod();
        Settings();
    }

    private void jsonparsemethod() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
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
                                bList.add(new Bus(routE, destinatioN, dueT));
                            }
                            adapter = new Adapter(requireActivity(), bList);
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

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );

        mRequestQueue.add(request);
    }*/


//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                    final int previousItem = bottomNavigationView1.getSelectedItemId();
//                    final int nextItem = menuItem.getItemId();
//                    Intent intent;
//
//                    if (previousItem != nextItem) {
//                        switch (nextItem) {
//                            case R.id.homehere:
//                                intent = new Intent(SearchActivityBus_results.this, MainActivityHome.class);
//                                startActivity(intent);
//                                break;
//
//                            case R.id.timetable:
//                                intent = new Intent(SearchActivityBus_results.this, TimetableFragment.class);
//                                startActivity(intent);
//                                break;
//
//                            case R.id.farenav:
//                                Toast.makeText(SearchActivityBus_results.this, "HELLOOO WORLD", Toast.LENGTH_SHORT).show();
//                                /* selectedFragment = new Fragment_Faresv();
//                                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_container,
//                                        selectedFragment).commit();*/
//                                break;
//
//                        }
//
//                    }
//                    return true;
//                }
//            };


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_busresults);
//        Intent intent = getIntent();
//        String url = intent.getStringExtra("link");
//        bottomNavigationView1 = findViewById(R.id.BNV);
//        bottomNavigationView1.setOnNavigationItemSelectedListener(navListener);
//        bottomNavigationView1.setSelectedItemId(R.id.rtpi);
//        getSupportActionBar().setElevation(0);
//        Settings();
//        bList = new ArrayList<>();
//        mRequestQueue = Volley.newRequestQueue(this);
//        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
//      /*  refresh = findViewById(R.id.refresh);
//        refreshbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Activity_busresults.this, "REFRESH ME", Toast.LENGTH_SHORT).show();
//            }
//        });*/

//getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            bList.clear();
//                            JSONArray jsonArray = response.getJSONArray("results");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject result = jsonArray.getJSONObject(i);
//                                String routE = result.getString("route");
//                                String destinatioN = result.getString("destination");
//                                String dueT = result.getString("duetime");
//                                bList.add(new Bus(routE, destinatioN, dueT));
//                            }
//                            adapter = new Adapter(SearchActivityBus_results.this, bList);
//                            mRecyclerView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//
//                        } catch (JSONException e) {
//
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//
//        });
//
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//        );
//
//        mRequestQueue.add(request);
//    }
/*

    private void Settings() {
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
*/


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.toolsbarmenu, menu);
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                finish();
//                return true;
//
//
//            case R.id.refresh:
//                Toast.makeText(getApplicationContext(), "Hello Javatpoint", Toast.LENGTH_SHORT).show();
//                return true;
//
//
//
//
//           /* case R.id.signmeout:
//                FirebaseAuth.getInstance().signOut();
//                finish();
//                //startActivity(new Intent(this, MainActivityLogin.class));
//                return true;*/
//        }
//
//        return super.onOptionsItemSelected(item);
//    }




