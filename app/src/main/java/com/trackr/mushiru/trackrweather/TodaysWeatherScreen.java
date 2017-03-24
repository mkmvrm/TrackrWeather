package com.trackr.mushiru.trackrweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class TodaysWeatherScreen extends AppCompatActivity {

    Retrieve5DayForecast retrieve5DayForecast;

    String name;
    String cityNameStripped;
    String queryResponse;

    Button searchBtn;
    TextView cityNameTxt;
    TextView descriptionTxt;
    TextView temperatureTxt;
    TextView humidityTxt;
    TextView windSpeedTxt;

    Button forecastBtn;

    TextView responseTxt;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todays_weather_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Log.e("BLAHBLAH: ", "111111");

        queryResponse = getIntent().getStringExtra("RESPONSE_STRING");

        cityNameStripped = getIntent().getStringExtra("CITY_NAME_STRIPPED");


        cityNameTxt = (TextView) findViewById(R.id.cityNameTxtS2);
        descriptionTxt = (TextView) findViewById(R.id.descriptionTxt);
        temperatureTxt = (TextView) findViewById(R.id.temperatureTxt);
        humidityTxt = (TextView) findViewById(R.id.humidityTxt);
        windSpeedTxt = (TextView) findViewById(R.id.windSpeedTxt);

        forecastBtn = (Button) findViewById(R.id.forecastBtn);

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
//
//
//        responseTxt = (TextView) findViewById(R.id.responseText);
//        searchBtn = (Button) findViewById(R.id.searchBtn);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //FIX SPACING CITYNAMETXT, add 'todays weather' to header, refractor code to better names, like screen2

        try {

            Log.d("RESPONSE", queryResponse);

            JSONObject openWeatherMapQuery = (JSONObject) new JSONTokener(queryResponse).nextValue();
            name = openWeatherMapQuery.getString("name");
            cityNameTxt.setText(name);

            JSONArray weatherArr = openWeatherMapQuery.getJSONArray("weather");

//            String description = openWeatherMapQuery.getString("cord.weather.description");

            String description = weatherArr.getJSONObject(0).getString("description");
            descriptionTxt.setText(description);


//            String temperature = openWeatherMapQuery.getString("list.main.temp");
            String temperature = openWeatherMapQuery.getJSONObject("main").getString("temp");
            temperatureTxt.setText(temperature);

//            String humidityPerCent = openWeatherMapQuery.getString("main.humidity");
            String humidityPerCent = openWeatherMapQuery.getJSONObject("main").getString("humidity");
            humidityTxt.setText(humidityPerCent + "%");

//            String windSpeed = openWeatherMapQuery.getString("wind.speed");
            String windSpeed = openWeatherMapQuery.getJSONObject("wind").getString("speed");
            windSpeedTxt.setText(windSpeed + "mph");

        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        forecastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // FIGURE OUT HOW TO GET THE NAME TO SEND ANOTHER URL REQUEST FROM THE CLASS BEFORE
//                String cityNameStripped = cityNameTxt.getText().toString().replaceAll("\\s+","").toLowerCase();


//                savedInstanceState.putStringArray(new String[] {});

                retrieve5DayForecast = new Retrieve5DayForecast(progressBar2, responseTxt, cityNameStripped, TodaysWeatherScreen.this);
                retrieve5DayForecast.execute();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}