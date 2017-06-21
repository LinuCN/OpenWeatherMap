package org.openweathermap.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 20-06-2017.
 */

public class ForecastWeatherList {
    @SerializedName("dt")
    public long dt;
    @SerializedName("dt_txt")
    public String currentDateTime;

    @SerializedName("weather")
    public Weather weather[];
    @SerializedName("main")
    public ForecastMain main;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("rain")
    public ForecastRain forecastRain;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("sys")
    public ForecastSys sys;

    public long getDt() {
        return dt;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public ForecastMain getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public ForecastRain getForecastRain() {
        return forecastRain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public ForecastSys getSys() {
        return sys;
    }
}
