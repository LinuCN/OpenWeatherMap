package org.openweathermap.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 19-06-2017.
 */
public class Wind {
    @SerializedName("speed")
    double speed;
    @SerializedName("deg")
    double deg;

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }
}
