package org.openweather.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 19-06-2017.
 */
public class Sys {
    @SerializedName("message")
    double message;
    @SerializedName("sunrise")
    long sunrise;
    @SerializedName("sunset")
    long sunset;

    public double getMessage() {
        return message;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
}
