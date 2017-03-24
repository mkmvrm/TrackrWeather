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

public class RetrieveTodaysWeather extends AsyncTask<Void, Void, String> {

    private Exception exception;
    final String API_KEY = "71c9847a4dea7b8bf18c4c126f892abf";
    final String API_URL = "http://api.openweathermap.org/data/2.5/weather?";

    private ProgressBar progressBar;
    private TextView responseView;
    private String cityNameStripped;
    private Class classname;


//    private StringBuilder responseSB;

    private Activity activity;

    //DELETE RESPONSEVIEW
    RetrieveTodaysWeather(ProgressBar progressBar, TextView responseView, String cityNameStripped, Activity activity){
        this.progressBar = progressBar;
        this.responseView = responseView;
        this.cityNameStripped = cityNameStripped;
//        this.responseSB = responseSB;
        this.classname = classname;

        this.activity = activity;
    }

    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
//        responseView.setText("");
    }

    protected String doInBackground(Void... urls) {
//        String email = cityNameTxt.getText().toString();
        // Do some validation here

        try {
            URL url = new URL(API_URL + "q=" + cityNameStripped + "&units=imperial&APPID=" + API_KEY);
            // http://api.openweathermap.org/data/2.5/weather?q=SantaBarbara&units=imperial&APPID=71c9847a4dea7b8bf18c4c126f892abf

//            String urlActual = "http://api.openweathermap.org/data/2.5/weather?q=SantaBarbara&units=imperial&APPID=71c9847a4dea7b8bf18c4c126f892abf";
//            String urlActual = "http://api.openweathermap.org/data/2.5/weather?q=SantaBarbara&units=imperial&APPID=71c9847a4dea7b8bf18c4c126f892abf";
//            URL url2 = new URL(urlActual);

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

        Intent intent = new Intent(activity, TodaysWeatherScreen.class);
        intent.putExtra("RESPONSE_STRING", response);
        intent.putExtra("CITY_NAME_STRIPPED", cityNameStripped);
        activity.startActivity(intent);

//        queryResponse = response;
//
//        responseSB.append(response);

//        responseView.setText(response);
    }

//    public String getResponse(){
//        return responseSB.toString();
//    }
}