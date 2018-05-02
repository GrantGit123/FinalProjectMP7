package com.finalprojectmp7;
import android.app.MediaRouteButton;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.BreakIterator;

class RetrieveFeedTask extends AsyncTask<Void, Void, String> {
    MediaRouteButton progressBar;
    BreakIterator responseView;
    private Exception exception;

    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        responseView.setText("");
    }

    protected String doInBackground(Void... urls) {
        try {
            URL url = new URL("https://www.quandl.com/api/v3/datasets/WIKI/" +".json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
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
        responseView.setText(response);
    }
}