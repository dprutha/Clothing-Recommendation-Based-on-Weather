package com.hackumbc.prutha.slayit;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Prutha on 10/1/2016.
 */

public class HandleRequestAsyncTask extends AsyncTask<String, String, String> {

    private Exception exception;

    protected String doInBackground(String... urls) {
        String TAG = "SendRequest";
        String resultJSON = "";
        HttpURLConnection myHttpURLConnection = null;
        String urlString = urls[0];
        Log.d(TAG, "url: " + urlString);
        try {
            URL myUrl = new URL(urlString);
            myHttpURLConnection = (HttpURLConnection) myUrl.openConnection();
            InputStream myInputStream = myHttpURLConnection.getInputStream();
            BufferedReader myBufferedReader = new BufferedReader(new InputStreamReader(myInputStream));
            String line = myBufferedReader.readLine();
            resultJSON += line;

            while (line != null) {
                line = myBufferedReader.readLine();
                resultJSON += line;
            }

            Log.d(TAG, "getInformation: " + resultJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getInformation: " + 3);
        return resultJSON;

    }
}
