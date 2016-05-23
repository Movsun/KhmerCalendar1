package com.example.movsun.khmercalendar;

import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CalendarAdapter calendarAdapter;
    private Integer monthId[] = {0};
    private ArrayList<JSONObject> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gridView = (GridView) findViewById(R.id.gridView);
        calendarAdapter = new CalendarAdapter(MainActivity.this, data, monthId);
        final RequestQueue queue = Volley.newRequestQueue(this);
        gridView.setAdapter(calendarAdapter);


        // on click;
        data.clear();
        monthId[0] = 6;
        getData(2016, 7, queue);

//        calendarAdapter.notifyDataSetChanged();


    }

    private void getData(int year, int month, RequestQueue queue){
        String url = "http://192.168.43.199/api/v1/calendar/"+year+"/"+month;
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            int len = jsonArray.length();
                            for (int i=0; i<len; i++){
                                data.add(jsonArray.getJSONObject(i));
                            }
                            calendarAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
//        Volley.newRequestQueue(this).add(jsonRequest);
        queue.add(jsonRequest);
    }
}
