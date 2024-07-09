package com.andichia.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class ForexMainActivity extends AppCompatActivity {

    private SwipeRefreshLayout _swipeRefreshLayout1;
    private RecyclerView _recyclerView1;
    private TextView _timestampTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forex_main);

        initSwipeRefreshLayout();
        _recyclerView1 = findViewById(R.id.recyclerView1);
//        _timestampTextView = findViewById(R.id.timestampTextView);

        bindRecyclerView();

    }

    private void bindRecyclerView(){
        String url = "https://openexchangerates.org/api/latest.json?app_id=b5200fa5d1a940c9a3f0b7199c34073d";
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                JSONObject root;

                try {
                    root = new JSONObject(jsonString);
                } catch (JSONException e){
                    Toast.makeText(ForexMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject rates;

                try{
                    rates = root.getJSONObject("rates");
                }catch (JSONException e){
                    Toast.makeText(ForexMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ForexMainActivity.this);
                ForexAdapter adapter = new ForexAdapter(rates);
                _recyclerView1.setLayoutManager(layoutManager);
                _recyclerView1.setAdapter(adapter);

                _swipeRefreshLayout1.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(ForexMainActivity.this, new String(responseBody), Toast.LENGTH_LONG).show();
            }
        });
    }




    private void setTimestamp(long timestamp){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String dateTime = format.format(new Date(timestamp * 1000));

        _timestampTextView.setText("Tanggal dan Waktu (UTC): " + dateTime);
    }


    private void initSwipeRefreshLayout(){
        _swipeRefreshLayout1 = findViewById(R.id.swipeRefreshLayout1);
        _swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {bindRecyclerView();}
        });
    }
}