package org.openweathermap.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.openweathermap.openweathermap.R;

/**
 * Created by LENOVO on 22-06-2017.
 */

public class MapDialogFragment extends DialogFragment {
    SupportMapFragment googleMap;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_map, container,
                false);
        rootView.findViewById(R.id.dialogClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getActivity().getSupportFragmentManager()
                        .beginTransaction();
                ft2.remove(getActivity().getSupportFragmentManager()
                        .findFragmentByTag("clearmap"));
                getDialog().cancel();
                dismiss();
            }
        });
        googleMap = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        double latitude = getArguments().getDouble("Lat");
        double longitude = getArguments().getDouble("Lng");
        String title = getArguments().getString("title");
        LatLng position = new LatLng(latitude, longitude);
        MarkerOptions options2 = new MarkerOptions();
        options2.position(position);
        options2.title("" + title);
        options2.snippet("");
        googleMap.getMap().addMarker(options2);
        animateMapCamera(latitude, longitude, 10);
        return rootView;
    }

    void animateMapCamera(double latitude, double longitude, int zoomValue) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(zoomValue)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), zoomValue);
        googleMap.getMap().animateCamera(cameraUpdate);
        googleMap.getMap().animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SupportMapFragment f = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }
}