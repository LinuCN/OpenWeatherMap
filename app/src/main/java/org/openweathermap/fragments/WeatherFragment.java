package org.openweathermap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.openweathermap.Model.ForecastModel;
import org.openweathermap.Model.WeatherModel;
import org.openweathermap.apis.ApiClient;
import org.openweathermap.openweathermap.R;
import org.openweathermap.openweathermap.SelectedLocationListActivity;
import org.openweathermap.ui.FontTextView;
import org.openweathermap.utils.AppUtils;
import org.openweathermap.utils.Connectivity;
import org.openweathermap.utils.GPSTracker;

/**
 * Created by LENOVO on 19-06-2017.
 */

public class WeatherFragment extends Fragment {

    ForecastModel forecastModel;
    WeatherModel weatherModel;
    ViewPager mViewPager;
    int pagePosition;
    View view;
    final String degree = "°";
    String padded = String.format("%-20s", "");
    private String displayString;
    double latitude = 0;
    double longitude = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(
                R.layout.fragment_weather, container, false);
        Bundle bundle = getArguments();
        pagePosition = bundle.getInt("page_position");
        String latLng = bundle.getString("latLng");

        if (latLng != null) {
            latitude = Double.valueOf(latLng.split(",")[0].trim());
            longitude = Double.valueOf(latLng.split(",")[1].trim());
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Connectivity.isConnected(getActivity())) {
            ApiClient apiClient = new ApiClient();
            apiClient.getWeatherDetails(this, latitude, longitude);
            apiClient.getForecastDetails(this, latitude, longitude);
        } else {
            Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    public void setViewPager(ViewPager mViewPager) {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (weatherModel != null) {
                    ((SelectedLocationListActivity) getActivity()).setHeaderDetails(weatherModel);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void setForecastDataInViews() {
        if (weatherModel == null || forecastModel == null) {
            return;
        }
        FontTextView todayTemp = (FontTextView) view.findViewById(R.id.w_todaydetail_temp);
        FontTextView todayTempMinMax = (FontTextView) view.findViewById(R.id.w_todaydetail_minmax);
        FontTextView todayHumidity = (FontTextView) view.findViewById(R.id.w_now_hum);
        FontTextView todayVisibility = (FontTextView) view.findViewById(R.id.w_now_visibility);
        FontTextView todayRainFall = (FontTextView) view.findViewById(R.id.w_now_rainfall);
        FontTextView todayFeelsLike = (FontTextView) view.findViewById(R.id.w_now_fl);

        TextView day1textView = (TextView) view.findViewById(R.id.w_day1_text);
        TextView day2textView = (TextView) view.findViewById(R.id.w_day2_text);
        TextView day3textView = (TextView) view.findViewById(R.id.w_day3_text);
        TextView day4textView = (TextView) view.findViewById(R.id.w_day4_text);
        TextView day5textView = (TextView) view.findViewById(R.id.w_day5_text);

        FontTextView day1FontContentView = (FontTextView) view.findViewById(R.id.w_day1_content);
        FontTextView day2FontContentView = (FontTextView) view.findViewById(R.id.w_day2_content);
        FontTextView day3FontContentView = (FontTextView) view.findViewById(R.id.w_day3_content);
        FontTextView day4FontContentView = (FontTextView) view.findViewById(R.id.w_day4_content);
        FontTextView day5FontContentView = (FontTextView) view.findViewById(R.id.w_day5_content);
        FontTextView sunRiseFontContentView = (FontTextView) view.findViewById(R.id.w_sun_rise);
        FontTextView sunSetFontContentView = (FontTextView) view.findViewById(R.id.w_sun_set);


        todayTemp.setText("" + weatherModel.getMain().getTemp());
        todayTempMinMax.setText(weatherModel.getMain().getTemp_min() + degree + "  ~  " + weatherModel.getMain().getTemp_max() + degree);
        todayHumidity.setText("" + weatherModel.getMain().getHumidity() + "%");
        todayVisibility.setText("" + weatherModel.getWind().getSpeed() + "mph W");
        todayRainFall.setText("" + weatherModel.getMain().getPressure());
        todayFeelsLike.setText("" + weatherModel.getMain().getTemp_min() + degree);

        int size = forecastModel.getForecastWeatherList().length;
        int unit = size / 5;
        int index = 0;
        long dateTimeMillis = forecastModel.getForecastWeatherList()[index].getDt() * 1000;
        day1textView.setText(AppUtils.extractDayFromDateString(dateTimeMillis));
        String displayString;
        double tempMin = forecastModel.getForecastWeatherList()[index].getMain().getTemp_min();
        double tempMax = forecastModel.getForecastWeatherList()[index].getMain().getTemp_max();
        displayString = Math.round(tempMax) + degree + padded + Math.round(tempMin) + degree;
        day1FontContentView.setText("" + displayString);

        dateTimeMillis = forecastModel.getForecastWeatherList()[index += unit].getDt() * 1000;
        day2textView.setText(AppUtils.extractDayFromDateString(dateTimeMillis));
        tempMin = forecastModel.getForecastWeatherList()[index].getMain().getTemp_min();
        tempMax = forecastModel.getForecastWeatherList()[index].getMain().getTemp_max();
        displayString = Math.round(tempMax) + degree + padded + Math.round(tempMin) + degree;
        day2FontContentView.setText("" + displayString);


        dateTimeMillis = forecastModel.getForecastWeatherList()[index += unit].getDt() * 1000;
        day3textView.setText(AppUtils.extractDayFromDateString(dateTimeMillis));
        tempMin = forecastModel.getForecastWeatherList()[index].getMain().getTemp_min();
        tempMax = forecastModel.getForecastWeatherList()[index].getMain().getTemp_max();
        displayString = Math.round(tempMax) + degree + padded + Math.round(tempMin) + degree;
        day3FontContentView.setText("" + displayString);

        dateTimeMillis = forecastModel.getForecastWeatherList()[index += unit].getDt() * 1000;
        day4textView.setText(AppUtils.extractDayFromDateString(dateTimeMillis));
        tempMin = forecastModel.getForecastWeatherList()[index].getMain().getTemp_min();
        tempMax = forecastModel.getForecastWeatherList()[index].getMain().getTemp_max();
        displayString = Math.round(tempMax) + degree + padded + Math.round(tempMin) + degree;
        day4FontContentView.setText("" + displayString);

        dateTimeMillis = forecastModel.getForecastWeatherList()[index += unit].getDt() * 1000;
        day5textView.setText(AppUtils.extractDayFromDateString(dateTimeMillis));
        tempMin = forecastModel.getForecastWeatherList()[index].getMain().getTemp_min();
        tempMax = forecastModel.getForecastWeatherList()[index].getMain().getTemp_max();
        displayString = Math.round(tempMax) + degree + padded + Math.round(tempMin) + degree;
        day5FontContentView.setText("" + displayString);

        sunRiseFontContentView.setText(AppUtils.convertMilliSecondsToFormattedDate(weatherModel.getSys().getSunrise() * 1000).split(" ")[1]);
        sunSetFontContentView.setText(AppUtils.convertMilliSecondsToFormattedDate(weatherModel.getSys().getSunset() * 1000).split(" ")[1]);
    }

    public WeatherModel setWeatherDetails(String response) {
        GsonBuilder builder = new GsonBuilder();
        Gson mGson = builder.create();
        if (response != null) {
            weatherModel = mGson.fromJson(response, WeatherModel.class);
            if (pagePosition == 0) {
                ((SelectedLocationListActivity) getActivity()).setHeaderDetails(weatherModel);
            }
            return weatherModel;
        }
        return null;
    }

    public ForecastModel setForecastDetails(String response) {
        GsonBuilder builder = new GsonBuilder();
        Gson mGson = builder.create();
        if (response != null) {
            forecastModel = mGson.fromJson(response, ForecastModel.class);
            setForecastDataInViews();
            return forecastModel;
        }
        return null;
    }
}
