package com.example.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesFragment extends Fragment{
    private static final String TAG = "HeadlinesFragment";

    private static final String URL_HEADLINES_IND = "https://newsapi.org/v2/top-headlines?country=in&apiKey=aa4130e9df3a4ac6adb48e4f78d26edf";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<News> newsList;
    private ProgressBar progressBar;
    private FloatingActionButton searchButton;
    private Button tryAgainButton;
    private LinearLayout layoutWithTryAgain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.headlines_fragment,container,false);
        progressBar = view.findViewById(R.id.prgressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchButton =  view.findViewById(R.id.searchButton);
        tryAgainButton = view.findViewById(R.id.tryAgainButton);
        layoutWithTryAgain = view.findViewById(R.id.layoutWithTryAgain);

        layoutWithTryAgain.setVisibility(View.GONE);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList = new ArrayList<>();


        loadRecyclerViewData();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), SearchActivity.class);
                startActivity(myIntent);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && searchButton.getVisibility() == View.VISIBLE) {
                    searchButton.hide();
                } else if (dy < 0 && searchButton.getVisibility() != View.VISIBLE) {
                    searchButton.show();
                }
            }
        });

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRecyclerViewData();
            }
        });
        return view;
    }

    private void loadRecyclerViewData() {
        progressBar.setVisibility(View.VISIBLE);

        if(isNetworkAvailable())
        {
            layoutWithTryAgain.setVisibility(View.GONE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_HEADLINES_IND,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.GONE);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("articles");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject j = jsonArray.getJSONObject(i);
                                    News news = new News(
                                            j.getString("title"),
                                            j.getString("urlToImage"),
                                            j.getString("url"));
                                    newsList.add(news);
                                }
                                adapter = new MyAdapter(newsList, getContext());
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //progressDialog.dismiss();
                            progressBar.setVisibility(View.GONE);
                            //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            layoutWithTryAgain.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
