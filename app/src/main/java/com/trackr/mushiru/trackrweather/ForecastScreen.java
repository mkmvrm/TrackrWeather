package com.trackr.mushiru.trackrweather;

import android.content.Intent;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ForecastScreen extends AppCompatActivity {

    Button backBtn;

    TextView cityNameTxtS3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backBtn = (Button) findViewById(R.id.backBtn);


        String queryResponse = getIntent().getStringExtra("RESPONSE_STRING");


        cityNameTxtS3 = (TextView) findViewById(R.id.cityNameTxtS3);

        // RETURN TO THE PREVIOUS ACTIVITY WHEN THE BACK BUTTON IS PRESSED
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView[] textViewDates = {(TextView) findViewById(R.id.date1Txt),
                (TextView) findViewById(R.id.date2Txt),
                (TextView) findViewById(R.id.date3Txt),
                (TextView) findViewById(R.id.date4Txt),
                (TextView) findViewById(R.id.date5Txt)};

        TextView[] textViewDescs = {(TextView) findViewById(R.id.desc1Txt),
                (TextView) findViewById(R.id.desc2Txt),
                (TextView) findViewById(R.id.desc3Txt),
                (TextView) findViewById(R.id.desc4Txt),
                (TextView) findViewById(R.id.desc5Txt)};

        TextView[] textViewTemps = {(TextView) findViewById(R.id.temp1Txt),
                (TextView) findViewById(R.id.temp2Txt),
                (TextView) findViewById(R.id.temp3Txt),
                (TextView) findViewById(R.id.temp4Txt),
                (TextView) findViewById(R.id.temp5Txt)};


        try {

            Log.d("RESPONSE SCREEN 3", queryResponse);
            JSONObject openWeatherMapQuery = (JSONObject) new JSONTokener(queryResponse).nextValue();

            String name = openWeatherMapQuery.getJSONObject("city").getString("name");
            cityNameTxtS3.setText(name);

            String country = openWeatherMapQuery.getJSONObject("city").getString("country");


            JSONArray forecastArr = openWeatherMapQuery.getJSONArray("list");

            // FILL IN ALL TEXTVIEWS WILL QUERY DATA
            for (int i = 0; i < 5; i++){

                // UPDATE DATE TEXTVIEW
                Long unixTime = Long.parseLong(forecastArr.getJSONObject(i).getString("dt"));
                Date date = new Date(unixTime * 1000);
                DateFormat format = new SimpleDateFormat("MM/dd");
                format.setTimeZone(TimeZone.getTimeZone(country));
                String formatted = format.format(date);
                Log.d("DATE US", formatted);
                textViewDates[i].setText(formatted);

                // UPDATE DESCRIPTION TEXTVIEW
                String description = forecastArr.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
                textViewDescs[i].setText(description);

                // UPDATE TEMPERATURE TEXTVIEW
                String temp = forecastArr.getJSONObject(i).getJSONObject("temp").getString("day");
                textViewTemps[i].setText(temp);
            }

        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
        }

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
