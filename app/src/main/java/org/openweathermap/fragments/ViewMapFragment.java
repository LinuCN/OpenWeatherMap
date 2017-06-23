package org.openweathermap.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.openweathermap.openweathermap.R;
import org.openweathermap.openweathermap.SelectedLocationListActivity;
import org.openweathermap.utils.AppUtils;
import org.openweathermap.utils.GPSTracker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by LENOVO on 19-06-2017.
 */

public class ViewMapFragment extends Fragment {

    private View view;
    GoogleMap googleMap;
    MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        ((SelectedLocationListActivity) getActivity()).setCollapsingToolbarLayoutTitle("Add Location");
        GPSTracker gpsTracker = new GPSTracker(getActivity());
        LatLng position = new LatLng(0.0, 0.0);
        if (gpsTracker.canGetLocation()) {
            position = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        }
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        googleMap = mapView.getMap();
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MarkerOptions options2 = new MarkerOptions();
        options2.position(position);
        options2.snippet("");
        googleMap.addMarker(options2);
        animateMapCamera(position, 10);
        final Marker marker = googleMap.addMarker(new MarkerOptions().position(position)
                .title("Draggable Marker")
                .snippet("Long press and move the marker if needed.")
                .draggable(true));
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDrag(Marker arg0) {
            }

            @Override
            public void onMarkerDragEnd(Marker arg0) {
                final LatLng markerLocation = marker.getPosition();
                final String cityName = getCityNameFromLatLng(markerLocation);

                if (cityName != null) {
                    Toast.makeText(getActivity(), cityName + " \n" + markerLocation.toString(), Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Add " + cityName + " to List ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Toast.makeText(getActivity(), cityName + " Added!", Toast.LENGTH_SHORT).show();
                                    final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    final SharedPreferences.Editor spEditor = sp.edit();
                                    String cityList = AppUtils.getSavedCitiesString(getActivity());
                                    cityList += cityName + ",";
                                    spEditor.putString("citylist", cityList);
                                    spEditor.putString(cityName, markerLocation.latitude + "," + markerLocation.longitude);
                                    spEditor.commit();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }

            @Override
            public void onMarkerDragStart(Marker arg0) {

            }
        });
        return view;
    }

    void animateMapCamera(LatLng latLng, int zoomValue) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(zoomValue)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                latLng, zoomValue);
        googleMap.animateCamera(cameraUpdate);
        googleMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    String getCityNameFromLatLng(LatLng latLng) {
        Geocoder geoCoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getLocality();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}