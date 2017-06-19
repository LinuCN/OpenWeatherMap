package org.openweathermap.openweathermap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.openweather.apis.ApiClient;

public class SelectedLocationListActivity extends AppCompatActivity {

    private static final String API_WEATHER = "weather?lat=20.5937&lon=78.9629&appid=c6e381d8c7ff98f0fee43775817cf6ad&units=metric";
    private static final String TAG = "OPEN_WEATHER_API";
    private static final String API = "http://api.openweathermap.org/data/2.5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_location_list);
        ApiClient apiClient = new ApiClient();
        apiClient.getJSONResponse(this, API+API_WEATHER);
    }
}
