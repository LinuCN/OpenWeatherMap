package org.openweathermap.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

/**
 * Created by LENOVO on 19-06-2017.
 */

public class AppConstants {

    public static final String API_WEATHER = "weather";
    public static final String API_FORECAST = "forecast";
    public static final String KEY_LAT = "?lat=";
    public static final String KEY_LON = "&lon=";
    public static final String KEY_APPID = "&appid=c6e381d8c7ff98f0fee43775817cf6ad";
    public static final String KEY_UNITS = "&units=";

    public static final String TAG = "OPEN_WEATHER_API";
    public static final String API = "http://api.openweathermap.org/data/2.5/";

    public static String getSelectedUnit(Context context){
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("unit","metric");
    }

    public static HashMap getAllCityDetails() {
        HashMap m1 = new HashMap<>();
        m1.put("Great Wall Station", "-62.21631997,-58.9666956");
        m1.put("San Javier", "-16.28961424,-62.50001998");
        m1.put("Sao Francisco do Sul", "-26.23960122,-48.59998987");
        m1.put("Boston", "42.32996014,71.07001367");
        m1.put("Philadelphia", "39.99997316,-75.16999597");
        m1.put("Gurdaspur", "32.041943,75.405334");
        m1.put("Oakland", "37.804363,-122.271111");
        m1.put("Domchanch", "24.474380,85.688744");
        m1.put("Barjora", "23.427221,87.287018");
        m1.put("San Francisco", "37.74000775,-122.4599777");
        m1.put("Denver", "39.73918805,-104.984016");
        m1.put("Dharmavaram", "14.413745,77.712616");
        m1.put("Siddipet", "18.101904,78.852074");
        m1.put("Dhanpuri", "23.173939,81.565125");
        m1.put("Chirala", "15.812074,80.355377");
        m1.put("Markapur", "15.764501,79.259491");
        m1.put("Houston", "29.81997438,-95.33997929");
        m1.put("Miami", "25.7876107,-80.22410608");
        m1.put("Atlanta", "33.83001385,-84.39994938");
        m1.put("Chicago", "41.82999066,-87.75005497");
        m1.put("Los Angeles", "33.98997825,-118.1799805");
        m1.put("Washington D.C", "38.89954938,-77.00941858");
        m1.put("New York", "40.74997906,-73.98001693");
        m1.put("Manikchak", "25.077787,87.900375");
        m1.put("Eskişehir", "39.766193,30.526714");
        m1.put("Roorkee", "29.854263,77.888000");
        m1.put("Kavali", "14.913181,79.992981");
        m1.put("Chalakudy", "10.311879,76.331978");
        m1.put("Gondal", "21.961946,70.792297");
        m1.put("Bhimavaram", "16.544893,81.521240");
        m1.put("Jalgaon", "21.049540,76.532028");
        m1.put("Paltan Bazaar", "26.182245,91.754723");
        m1.put("Hodal", "27.897551,77.384117");
        m1.put("Ausa", "18.245655,76.505356");
        m1.put("Mahidpur", "23.486839,75.659157");
        m1.put("Gurdaspur", "32.041943,75.405334");
        m1.put("Oakland", "37.804363,-122.271111");
        m1.put("Domchanch", "24.474380,85.688744");
        m1.put("Barjora", "23.427221,87.287018");
        m1.put("Pune", "18.5204,73.8567");

        return m1;
    }

}
