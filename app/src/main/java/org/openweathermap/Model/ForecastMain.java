package org.openweathermap.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 19-06-2017.
 */
public class ForecastMain {
    @SerializedName("temp")
    double temp;
    @SerializedName("pressure")
    double pressure;
    @SerializedName("humidity")
    double humidity;
    @SerializedName("temp_min")
    double temp_min;
    @SerializedName("temp_max")
    double temp_max;
    @SerializedName("sea_level")
    double sea_level;
    @SerializedName("grnd_level")
    double grnd_level;

    public double getTemp_kf() {
        return temp_kf;
    }

    @SerializedName("temp_kf")
    double temp_kf;

    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getSea_level() {
        return sea_level;
    }

    public double getGrnd_level() {
        return grnd_level;
    }
}
