package org.openweather.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 19-06-2017.
 */
public class Clouds {
    @SerializedName("all")
    long all;

    public long getAll() {
        return all;
    }
}
