package com.trackr.mushiru.trackrweather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class TodaysWeatherScreen extends AppCompatActivity {

    Retrieve5DayForecast retrieve5DayForecast;
    RetrieveWeatherIcon retrieveWeatherIcon;

    String name;
    String cityNameStripped;
    String units;
    String queryResponse;

    Button searchBtn;
    TextView cityNameTxt;
    TextView descriptionTxt;
    TextView temperatureTxt;
    TextView humidityTxt;
    TextView windSpeedTxt;

    Button forecastBtn;
    Button backBtn1;

    TextView responseTxt;
    ProgressBar progressBar2;

    ImageView weatherImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todays_weather_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // GRAB DATA FROM RetrieveTodaysWeather.java
        queryResponse = getIntent().getStringExtra("RESPONSE_STRING");
        cityNameStripped = getIntent().getStringExtra("CITY_NAME_STRIPPED");
        units = getIntent().getStringExtra("UNITS");

        cityNameTxt = (TextView) findViewById(R.id.cityNameTxtS2);
        descriptionTxt = (TextView) findViewById(R.id.descriptionTxt);
        temperatureTxt = (TextView) findViewById(R.id.temperatureTxt);
        humidityTxt = (TextView) findViewById(R.id.humidityTxt);
        windSpeedTxt = (TextView) findViewById(R.id.windSpeedTxt);

        forecastBtn = (Button) findViewById(R.id.forecastBtn);
        backBtn1 = (Button) findViewById(R.id.backBtn1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        weatherImg = (ImageView) findViewById(R.id.weatherImg);

        backBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            Log.d("RESPONSE_TODAYS_WEATHER", queryResponse);


            JSONObject openWeatherMapQuery = (JSONObject) new JSONTokener(queryResponse).nextValue();

            name = openWeatherMapQuery.getString("name");

            String country = openWeatherMapQuery.getJSONObject("sys").getString("country");
            cityNameTxt.setText(name + ", " + country);

            String iconName = openWeatherMapQuery.getJSONArray("weather").getJSONObject(0).getString("icon");

            retrieveWeatherIcon = new RetrieveWeatherIcon(iconName, weatherImg);
            retrieveWeatherIcon.execute();

//            // GRAB ICON FROM OPEN WEATHER MAP
//            String iconName = openWeatherMapQuery.getJSONArray("weather").getJSONObject(0).getString("icon");
//            String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
//            Log.d("IMAGE URL", imageUrl);
//            try {
//                InputStream is = (InputStream)new URL(imageUrl).getContent();
//                Bitmap bitmap = BitmapFactory.decodeStream(is);
//                weatherImg.setImageBitmap(bitmap);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            String description = openWeatherMapQuery.getJSONArray("weather").getJSONObject(0).getString("description");
            descriptionTxt.setText(description);

            String temperature = openWeatherMapQuery.getJSONObject("main").getString("temp");
            String windSpeed = openWeatherMapQuery.getJSONObject("wind").getString("speed");

            String humidityPerCent = openWeatherMapQuery.getJSONObject("main").getString("humidity");
            humidityTxt.setText(humidityPerCent + "%");

            if(units.equals("imperial")) {
                temperatureTxt.setText(temperature + " (F°)");
                windSpeedTxt.setText(windSpeed + " mph");
            }else {
                temperatureTxt.setText(temperature + " (C°)");
                windSpeedTxt.setText(windSpeed + " m/s");
            }

        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        forecastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // RETRIEVE OPEN WEATHER MAP API RESPONSE BASED OFF OF USER TEXTVIEW INPUT
                retrieve5DayForecast = new Retrieve5DayForecast(progressBar2, units, cityNameStripped, TodaysWeatherScreen.this);
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
