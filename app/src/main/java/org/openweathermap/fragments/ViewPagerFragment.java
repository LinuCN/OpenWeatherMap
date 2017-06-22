package org.openweathermap.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import org.openweathermap.openweathermap.R;
import org.openweathermap.openweathermap.SelectedLocationListActivity;
import org.openweathermap.utils.AppConstants;
import org.openweathermap.utils.AppUtils;
import org.openweathermap.utils.GPSTracker;

/**
 * Created by LENOVO on 19-06-2017.
 */

public class ViewPagerFragment extends Fragment {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_container, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), mViewPager);
        mViewPager.setPageTransformer(false, new FadePageTransformer());
        mViewPager.setAdapter(mCustomPagerAdapter);
        return view;
    }

    public class FadePageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {

            if (position < -1 || position > 1) {
                view.setAlpha(0);
            } else if (position <= 0 || position <= 1) {
                // Calculate alpha. Position is decimal in [-1,0] or [0,1]
                float alpha = (position <= 0) ? position + 1 : 1 - position;
                view.setAlpha(alpha);
            } else if (position == 0) {
                view.setAlpha(1);
            }
        }
    }

    class CustomPagerAdapter extends FragmentPagerAdapter {
        Context mContext;
        String userSavedCities[];
        ViewPager mViewPager;

        public CustomPagerAdapter(FragmentManager fm, Context context, ViewPager mViewPager) {
            super(getChildFragmentManager());
            mContext = context;
            this.mViewPager = mViewPager;
            userSavedCities = AppUtils.getSavedCities(context);
        }

        @Override
        public Fragment getItem(int position) {
            WeatherFragment fragment = new WeatherFragment();
            fragment.setViewPager(mViewPager);
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            if (position == 0) {
                GPSTracker gpsTracker = new GPSTracker(getActivity());
                if (gpsTracker.canGetLocation()) {
                    String latLng = gpsTracker.getLatitude() + "," + gpsTracker.getLongitude();
                    args.putString("latLng", "" + latLng);
                } else {
                    Toast.makeText(getActivity(), "Current Location not found!", Toast.LENGTH_LONG).show();
                    args.putString("latLng", AppUtils.getLatLngString(getActivity(),userSavedCities[position - 1]));
                }
            } else {
                args.putString("latLng", AppUtils.getLatLngString(getActivity(),userSavedCities[position - 1]));
            }
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return userSavedCities.length + 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "" + (position + 1);
        }
    }
}
