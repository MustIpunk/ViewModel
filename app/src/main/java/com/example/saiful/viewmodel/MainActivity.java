package com.example.saiful.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.security.Provider;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private WeatherAdapter adapter;
    private ProgressBar progressBar;
    private EditText edtCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getWeathers().observe(this, getWeather);

        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        progressBar = findViewById(R.id.progressBar);
        edtCity = findViewById(R.id.edtCity);

        findViewById(R.id.btn_search).setOnClickListener(myListener);
    }
    private Observer<ArrayList<WeatherItems>> getWeather = new Observer<ArrayList<WeatherItems>>() {
        @Override
        public void onChanged(@Nullable ArrayList<WeatherItems> weatherItems) {
        if (weatherItems != null){
            adapter.setData(weatherItems);
            showLoading(false);
        }

        }
    };
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String city = edtCity.getText().toString();

            if (TextUtils.isEmpty(city))return;

            mainViewModel.setWeather(city);
            showLoading(true);
        }
    };
    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
