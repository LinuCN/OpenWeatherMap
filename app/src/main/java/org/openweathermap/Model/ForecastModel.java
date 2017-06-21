package org.openweathermap.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 20-06-2017.
 */

public class ForecastModel {

    @SerializedName("cod")
    String cod;
    @SerializedName("message")
    double message;
    @SerializedName("cnt")
    long cnt;
    @SerializedName("list")
    ForecastWeatherList forecastWeatherList[];

    public String getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public long getCnt() {
        return cnt;
    }

    public ForecastWeatherList[] getForecastWeatherList() {
        return forecastWeatherList;
    }
}
