package com.finalprojectmp7;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;
import java.util.Arrays;



/**
 * Main screen for our API testing app.
 */
public final class MainActivity extends AppCompatActivity {

    private LineChart lineChart;

    private static final String TAG = "MP7:Main";

    private static RequestQueue requestQueue;

    Button button;
    SearchView searchView;


    private String jsonResult;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.searchbar);
        button = findViewById(R.id.button);
        searchView.setOnQueryTextListener(createSearchQueryListener());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Get Graph Button Clicked");
                createGraph();
            }
        });
    }
     //make API calls
    private String query;
    private void startAPICall() {
        try {
            StringRequest getJson = new StringRequest(
                    Request.Method.GET,
                    "https://www.quandl.com/api/v3/datasets/WIKI/" + query + ".json",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            jsonResult = response;
                            //Log.d(TAG, Arrays.toString(Backend.getClose(jsonResult)));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, "ERROR RESPONSE");
                }
            });
            requestQueue.add(getJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   //Searchbar listener
    private SearchView.OnQueryTextListener createSearchQueryListener() {
        try {
            return new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String input) {
                    query = input;
                    startAPICall();
                    return false;
                }
                @Override
                public boolean onQueryTextChange(final String newText) {
                    return true;
                }
            };
        } catch (NullPointerException e) {
            startAPICall();
        }
        return null;
    }

    public void createGraph() {
        try {
            lineChart = findViewById(R.id.LineChart);
            lineChart.setDragEnabled(true);
            lineChart.setScaleEnabled(false);

            ArrayList<Entry> values = new ArrayList<>();

            //put a for loop here to put in all the values for yAxis (high values within entry dates)
            float[] closingPrices = Backend.getClose(jsonResult);


            values.add(new Entry(1, closingPrices[6]));
            values.add(new Entry(2, closingPrices[5]));
            values.add(new Entry(3, closingPrices[4]));
            values.add(new Entry(4, closingPrices[3]));
            values.add(new Entry(5, closingPrices[2]));
            values.add(new Entry(6, closingPrices[1]));
            values.add(new Entry(7, closingPrices[0]));

            LineDataSet set1 = new LineDataSet(values, "Data set 1");

            set1.setFillAlpha(110);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);

            lineChart.setData(data);


        } catch (NegativeArraySizeException e) {
            Log.d(TAG, "NEGATIVE???");
        }
    }

}
