package com.finalprojectmp7;
//import android.util.Log;
//import android.widget.SearchView;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Backend  {
    private static final String TAG = "MP7:Main- Backend";


    public static float[] getClose(final String json) {
        if (json == null) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(json).getAsJsonObject();
        JsonObject dataSet = obj.getAsJsonObject("dataset");
        JsonArray data = dataSet.getAsJsonArray("data");
        float[] closingValues = new float[7];
        for (int j = 0; j < 7; j++) {
            closingValues[j] = data.get(j).getAsJsonArray().get(4).getAsFloat();
        }
        return closingValues;
    }
}
