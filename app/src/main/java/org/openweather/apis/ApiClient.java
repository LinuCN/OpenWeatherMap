package org.openweather.apis;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.openweather.Model.WeatherModel;


public class ApiClient {

    private static final String API_WEATHER = "weather?lat=20.5937&lon=78.9629&appid=c6e381d8c7ff98f0fee43775817cf6ad&units=metric";
    private static final String TAG = "OPEN_WEATHER_API";
    private static final String API = "http://api.openweathermap.org/data/2.5/";

    final Gson gson = new Gson();

    WeatherModel setWeatherDetails(String response) {
        GsonBuilder builder = new GsonBuilder();
        Gson mGson = builder.create();
        if (response != null) {
            WeatherModel weatherModel = mGson.fromJson(response, WeatherModel.class);
            return weatherModel;
        }
        return null;
    }

    public void getJSONResponse(final Activity activity, String URL) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                if (response == null || response.equals("")) {
                    Log.d(TAG, "Error String is null");
                    Toast.makeText(activity, "Error String is null " + response, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, "GOT : " + response, Toast.LENGTH_LONG).show();
                    String toastString = getString(setWeatherDetails(response));
                    Toast.makeText(activity, "PARSING : " + toastString, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    String getString(WeatherModel weatherModel) {
        String returnSring;
        returnSring =
                weatherModel.getId() +
                        weatherModel.getCurrentDateTime()+
        weatherModel.getCoord().getLat() + weatherModel.getCoord().getLat()+
                        weatherModel.getWeather()[0].getDescription();
        return returnSring;
    }
}