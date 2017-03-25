package com.trackr.mushiru.trackrweather;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mushiru on 3/17/2017.
 */

public class Retrieve5DayForecast extends AsyncTask<Void, Void, String> {

    private Exception exception;
    final String API_KEY = "71c9847a4dea7b8bf18c4c126f892abf";
    final String API_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";

    private ProgressBar progressBar;
    private String cityNameStripped;

    private Activity activity;

    Retrieve5DayForecast(ProgressBar progressBar, String cityNameStripped, Activity activity){
        this.progressBar = progressBar;
        this.cityNameStripped = cityNameStripped;

        this.activity = activity;
    }

    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected String doInBackground(Void... urls) {

        try {
            URL url = new URL(API_URL + "q=" + cityNameStripped + "&cnt=5&units=imperial&APPID=" + API_KEY);
            // http://api.openweathermap.org/data/2.5/forecast/daily?q=Ojai&cnt=5&units=imperial&appid=71c9847a4dea7b8bf18c4c126f892abf

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                Log.d("QUERY", stringBuilder.toString());
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        progressBar.setVisibility(View.GONE);

        Log.i("INFO", response);

        Intent intent = new Intent(activity, ForecastScreen.class);
        intent.putExtra("RESPONSE_STRING", response);
        activity.startActivity(intent);

    }

}