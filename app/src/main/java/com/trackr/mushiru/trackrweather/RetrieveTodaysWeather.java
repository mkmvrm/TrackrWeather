package com.trackr.mushiru.trackrweather;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONTokener;

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
    private TextView errorTxt;
    private String cityNameStripped;
    private Class classname;
    private String units;


    private Activity activity;

    //DELETE RESPONSEVIEW
    RetrieveTodaysWeather(ProgressBar progressBar, String units, TextView responseView, TextView errorTxt, String cityNameStripped, Activity activity){
        this.progressBar = progressBar;
        this.errorTxt = errorTxt;
        this.cityNameStripped = cityNameStripped;
        this.units = units;
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
            String urlStr= API_URL + "q=" + cityNameStripped + "&units=" + units + "&APPID=" + API_KEY;
            Log.d("URL TO SEND", urlStr);
            URL url = new URL(urlStr);
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
        progressBar.setVisibility(View.GONE);

        if(response == null) {
            errorTxt.setVisibility(View.VISIBLE);
            response = "City Name Not Found";
        }else {

            Log.i("INFO", response);

            Intent intent = new Intent(activity, TodaysWeatherScreen.class);
            intent.putExtra("RESPONSE_STRING", response);
            intent.putExtra("CITY_NAME_STRIPPED", cityNameStripped);
            intent.putExtra("UNITS", units);

            activity.startActivity(intent);
        }

    }

}