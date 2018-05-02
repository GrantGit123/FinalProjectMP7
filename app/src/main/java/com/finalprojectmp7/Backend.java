package com.finalprojectmp7;
import android.util.Log;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Backend  {
    private static final String TAG = "MP7:Main- Backend";


    public static int getClose(final String json) {
        if (json == null) {
            return 0;
        }
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(json).getAsJsonObject();
        JsonObject dataSet = obj.getAsJsonObject("dataset");
        JsonArray data = dataSet.getAsJsonArray("data");
        JsonArray recentDate = data.get(0).getAsJsonArray();
        return recentDate.get(4).getAsInt();
    }
}
