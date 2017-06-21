package org.openweathermap.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 20-06-2017.
 */

public class City {
    @SerializedName("id")
    public long id;
    @SerializedName("name")
    public String name;
    @SerializedName("country")
    public String country;

    @SerializedName("population")
    public long population;

    @SerializedName("coord")
    Coord coord;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public long getPopulation() {
        return population;
    }

    public Coord getCoord() {
        return coord;
    }

}
