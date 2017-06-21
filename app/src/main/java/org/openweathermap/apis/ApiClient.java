package org.openweathermap.apis;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.openweathermap.Model.ForecastModel;
import org.openweathermap.Model.WeatherModel;
import org.openweathermap.fragments.WeatherFragment;
import org.openweathermap.utils.AppConstants;


public class ApiClient {

    public void getWeatherDetails(final WeatherFragment weatherFragment, double latitude, double longitide) {
        final Activity activity = weatherFragment.getActivity();
        String URL = AppConstants.API + AppConstants.API_WEATHER + AppConstants.KEY_LAT + latitude + AppConstants.KEY_LON
                + longitide + AppConstants.KEY_APPID + AppConstants.KEY_UNITS+AppConstants.getSelectedUnit(activity);
        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AppConstants.TAG, "Response " + response);
                if (response == null || response.equals("")) {
                    Log.d(AppConstants.TAG, "Error String is null");
                    Toast.makeText(activity, "Error String is null " + response, Toast.LENGTH_LONG).show();
                } else {
                    getWeatherString(weatherFragment.setWeatherDetails(response));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(AppConstants.TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    public void getForecastDetails(final WeatherFragment weatherFragment, double latitude, double longitide) {
        final Activity activity = weatherFragment.getActivity();
        String URL = AppConstants.API + AppConstants.API_FORECAST + AppConstants.KEY_LAT + latitude + AppConstants.KEY_LON
                + longitide + AppConstants.KEY_APPID + AppConstants.KEY_UNITS+AppConstants.getSelectedUnit(activity);
        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AppConstants.TAG, "Response " + response);
                if (response == null || response.equals("")) {
                    Log.d(AppConstants.TAG, "Error String is null");
                    Toast.makeText(activity, "Error String is null " + response, Toast.LENGTH_LONG).show();
                } else {
                    getForecastString(weatherFragment.setForecastDetails(response));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(AppConstants.TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    String getWeatherString(WeatherModel weatherModel) {
        String returnSring;
        returnSring =
                "WEATHER " + weatherModel.getId() +
                        weatherModel.getCurrentDateTime() +
                        weatherModel.getCoord().getLat() + weatherModel.getCoord().getLat() +
                        weatherModel.getWeather()[0].getDescription();
        return returnSring;
    }

    String getForecastString(ForecastModel forecastModel) {
        String returnSring;
        returnSring =
                "FORECAST " + forecastModel.getForecastWeatherList()[0].getWeather()[0].getDescription() +
                        forecastModel.getMessage();
        return returnSring;
    }
}