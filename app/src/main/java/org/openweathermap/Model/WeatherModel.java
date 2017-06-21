package org.openweathermap.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 19-06-2017.
 */

public class WeatherModel {

    @SerializedName("dt")
    public long currentDateTime;
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("base")
    public String base;
    @SerializedName("cod")
    public long cod;


    @SerializedName("coord")
    public Coord coord;
    @SerializedName("weather")
    public Weather weather[];
    @SerializedName("main")
    public Main main;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("sys")
    public Sys sys;

    public long getCurrentDateTime() {
        return currentDateTime;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBase() {
        return base;
    }

    public long getCod() {
        return cod;
    }

    public Coord getCoord() {
        return coord;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Sys getSys() {
        return sys;
    }
}

