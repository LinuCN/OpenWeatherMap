package org.openweather.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 19-06-2017.
 */
public class Coord {
    @SerializedName("lon")
    public double lon;
    @SerializedName("lat")
    public double lat;

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
