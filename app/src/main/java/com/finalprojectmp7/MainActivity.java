package com.finalprojectmp7;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Main screen for our API testing app.
 */
public final class MainActivity extends AppCompatActivity {

    private LineChart lineChart;

    private static final String TAG = "MP7:Main";

    private static RequestQueue requestQueue;

    Button button;
    TextView text;
    SearchView searchView;


    private String jsonResult;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);
        searchView = findViewById(R.id.searchbar);
        searchView.setOnQueryTextListener(createSearchQueryListener());

        try {
            lineChart = findViewById(R.id.LineChart);
            //lineChart.setOnChartValueSelectedListener(MainActivity.this);
            //lineChart.setOnChartGestureListener();
            lineChart.setDragEnabled(true);
            lineChart.setScaleEnabled(false);

            ArrayList<Entry> yValues = new ArrayList<>();

            //put a for loop here to put in all the values for yAxis (high values within entry dates)
            yValues.add(new Entry(0, 60f));
            yValues.add(new Entry(1, 60f));
            yValues.add(new Entry(2, 60f));
            yValues.add(new Entry(3, 60f));
            yValues.add(new Entry(5, 60f));

            LineDataSet set1 = new LineDataSet(yValues, "Data set 1");

            set1.setFillAlpha(110);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);

            lineChart.setData(data);


        } catch (NegativeArraySizeException e) {
            Log.d(TAG, "NEGATIVE???");
        }
        Backend closingPrice = new Backend();
        int help = closingPrice.getClose(jsonResult);
        Log.d(TAG,"FUCK" + help);
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
                            Log.d(TAG, "HAI");
                            Log.d(TAG, response);
                            jsonResult = response;
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, "ERROR RESPONSE");
                    text.setText("ERROR RESPONSE");
                }
            });
            requestQueue.add(getJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        text.setText(jsonResult);
    }


   //Searchbar listener
    private SearchView.OnQueryTextListener createSearchQueryListener() {
        try {
            return new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String input) {
                    query = input;
                    startAPICall();
                    Log.d(TAG, "HERE");
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

    /*
     * ArrayList of dates between two input dates.
     */
    public ArrayList<String> getList(Calendar startDate, Calendar endDate){
        ArrayList<String> list = new ArrayList<String>();
        while(startDate.compareTo(endDate)<=0){
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH,1);
        }
        return list;
    }
    //helper function to getDate
    public String getDate(Calendar cld){
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/"
                +cld.get(Calendar.DAY_OF_MONTH);
        try{
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate =  new SimpleDateFormat("yyyy/MM/dd").format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return curDate;
    }

}
