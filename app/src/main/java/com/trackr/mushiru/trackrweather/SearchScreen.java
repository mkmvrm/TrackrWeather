package com.trackr.mushiru.trackrweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

public class SearchScreen extends AppCompatActivity {

    //MAKE ALL VARIABLES PRIVATE, ORGANIZE VARIABLES

    RetrieveTodaysWeather retrieveTodaysWeather;

    private Button searchBtn;
    private TextView cityNameTxt;
    private TextView errorTxt;
    private TextView responseTxt;
    private ProgressBar progressBar;
    private Switch unitSwitch;

    private String queryResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        cityNameTxt = (TextView) findViewById(R.id.cityNameTxt);
        errorTxt = (TextView) findViewById(R.id.errorTxt);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        unitSwitch = (Switch) findViewById(R.id.unitSwitch);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityNameStripped = cityNameTxt.getText().toString().replaceAll("\\s+","").toLowerCase();
                String units;

                if(unitSwitch.isChecked())
                    units = "imperial";
                else
                    units = "metric";

                errorTxt.setVisibility(View.GONE);
                retrieveTodaysWeather = new RetrieveTodaysWeather(progressBar, units, responseTxt, errorTxt, cityNameStripped, SearchScreen.this);
                retrieveTodaysWeather.execute();


//                while (queryResponse = retrieveTodaysWeather.getResponse())
//

//                retrieveTodaysWeather.doInBackground();

//                Log.d("BLAH", queryResponse);

//                Intent intent = new Intent(SearchScreen.this, TodaysWeatherScreen.class);
//                intent.putExtra("RESPONSE_STRING", queryResponse);
//                startActivity(intent);

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
