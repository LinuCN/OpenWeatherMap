package org.openweathermap.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.openweathermap.openweathermap.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AppUtils {

    public static String changeDateFormat(String inFormat, String outFormat,
                                          String dateString) {

        DateFormat inDateFormat = new SimpleDateFormat(inFormat);
        DateFormat outDateFormat = new SimpleDateFormat(outFormat);
        Date date = null;
        try {
            date = inDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            return outDateFormat.format(date);
        }
        return dateString;
    }

    public static String toTitleCase(String givenString) {
        givenString = givenString.toLowerCase();
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    public static String convertMilliSecondsToFormattedDate(long milliSeconds) {
        final String dateFormat = "dd-MM-yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        milliSeconds += TimeZone.getDefault().getOffset(milliSeconds);
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }
    public static String extractDayFromDateString(long milliSeconds){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE");
        return formatter.format(milliSeconds);
    }

    public static String[] getSavedCities(Context context) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String cityList = sp.getString("citylist", "Pune,");
        return cityList.split(",");
    }
}