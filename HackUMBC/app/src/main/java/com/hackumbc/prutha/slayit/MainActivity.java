package com.hackumbc.prutha.slayit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTemperature;
    private TextView mPrediction;
    private Button sendLocation;
    private ImageView imageView;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private List<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTemperature = (TextView) findViewById(R.id.temperature);
        mPrediction = (TextView) findViewById(R.id.prediction);
        sendLocation = (Button) findViewById(R.id.sendLocation);
        imageView = (ImageView) findViewById(R.id.imageView);
        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sendLocation.setOnClickListener(this);
        imageList = new ArrayList<String>();

        requestImages();

    }

    @Override
    public void onClick(View v) {
        requestImages();
    }

    public void requestImages()
    {
        LocationTracker locationTracker = new LocationTracker(this);
        if (locationTracker.canGetLocation())
        {
            double latitude = locationTracker.getLatitude();
            double longitude = locationTracker.getLongitude();
            BigDecimal bd = new BigDecimal(latitude);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            latitude = bd.doubleValue();
            BigDecimal bdlong = new BigDecimal(longitude);
            bdlong = bdlong.setScale(2, RoundingMode.HALF_UP);
            longitude = bdlong.doubleValue();
            String url = "http://climegarm.herokuapp.com/get_weather/"+ latitude + "/" + longitude;
            String response = null;
            try {
                response = new HandleRequestAsyncTask().execute(url).get();
                if(response!=null)
                {
                    parseJson(response);
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    Log.d("Resp",response);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            locationTracker.showSettingsAlert();
        }
    }

    public void parseJson(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String currentDynamicValue = null;
            Iterator keys = jsonObject.keys();
            imageList.clear();
            while (keys.hasNext())
            {
                String currentDynamicKey = (String)keys.next();
                // get the value of the dynamic key
                currentDynamicValue = jsonObject.optString(currentDynamicKey).toString();
//                imageList.add(currentDynamicValue);
                Log.d("JSON", currentDynamicValue);

            }
            imageList.add(currentDynamicValue);
            adapter = new MyRecyclerAdapter(this,imageList);
            mRecyclerView.setAdapter(adapter);
//            Picasso.with(this)
//                    .load("file:///android_asset" + currentDynamicValue)
//                    .into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
