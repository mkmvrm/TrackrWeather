package com.trackr.mushiru.trackrweather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mushiru on 3/17/2017.
 */

public class RetrieveWeatherIcon extends AsyncTask<Void, Void, String> {

    private Exception exception;

    private String iconName;
    ImageView weatherImg;

    private Activity activity;

    RetrieveWeatherIcon(String iconName, ImageView weatherImg){
        this.iconName = iconName;
        this.weatherImg = weatherImg;
    }

    protected void onPreExecute() {
//        progressBar.setVisibility(View.VISIBLE);
//        responseView.setText("");
    }

    protected String doInBackground(Void... urls) {

//      GRAB ICON FROM OPEN WEATHER MAP
        String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
        Log.d("IMAGE URL", imageUrl);
        InputStream is = null;
        try {
            is = (InputStream)new URL(imageUrl).getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String bitmapString = Base64.encodeToString(b, Base64.DEFAULT);
            return bitmapString;

        } catch (IOException e) {
            Log.e("ERROR", e.getMessage(), e);
            e.printStackTrace();
            return null;
        }

    }

    protected void onPostExecute(String response) {
        weatherImg.setVisibility(View.VISIBLE);

        Bitmap bitmap;
            if(response != null){
                Log.d("UPDATING IMAGE", iconName);
                Log.d("BITMAP", response);

                try {
                    byte[] encodeByte = Base64.decode(response, Base64.DEFAULT);
                    bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                            encodeByte.length);
                    weatherImg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.d("STRING TO BITMAP FAILED", e.getMessage());
                }
            }
    }

}