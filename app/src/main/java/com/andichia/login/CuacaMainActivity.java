package com.andichia.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class CuacaMainActivity extends AppCompatActivity {

    private RecyclerView _recyclerView2;
//    private RootModel _rootModel;
    private SwipeRefreshLayout _swipeRefreshLayout2;
//    private TextView _totalTextView;
//    private TextView _textViewCityInfo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuaca_main);
        _recyclerView2 =  findViewById(R.id.recyclerView2);
//        _totalTextView = findViewById(R.id.totalTextView);
//        _textViewCityInfo = findViewById(R.id.textView_cityInfo);
        _swipeRefreshLayout2 = findViewById(R.id.swipeRefreshLayout2);

        initRecyclerView2();
        initSwipeRefreshLayout();

//        _textViewCityInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                if (_rootModel != null && _rootModel.getCityModel()!= null && _rootModel.getCityModel().getCoordModel() != null){
//                    double latitude = _rootModel.getCityModel().getCoordModel().getLat();
//                    double longitude = _rootModel.getCityModel().getCoordModel().getLon();
//
//                    Log.d("MainActivity", "Latitude: " + latitude )
//                }
//            }
//        });
    }

    private void initSwipeRefreshLayout() {
        _swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView2();
//                _swipeRefreshLayout2.setRefreshing(false);
            }
        });
    }


    private void initRecyclerView2() {
        _swipeRefreshLayout2.setRefreshing(true);
        String url = "https://api.openweathermap.org/data/2.5/forecast?id=1630789&appid=ec9b1318ed140f87a319d015fa0dbeaa";
        AsyncHttpClient ahc = new AsyncHttpClient();

        ahc.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("*tw*", new String(responseBody));

                Gson gson = new Gson();
                CuacaRootModel rm = gson.fromJson(new String(responseBody), CuacaRootModel.class);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(CuacaMainActivity.this);
                CuacaAdapter ca = new CuacaAdapter(CuacaMainActivity.this, rm);

                _recyclerView2.setLayoutManager(lm);
                _recyclerView2.setAdapter(ca);

                _swipeRefreshLayout2.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                _swipeRefreshLayout2.setRefreshing(false);
            }
        });
    }



}