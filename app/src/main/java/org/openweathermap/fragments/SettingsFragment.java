package org.openweathermap.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.openweathermap.openweathermap.R;
import org.openweathermap.openweathermap.SelectedLocationListActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LENOVO on 19-06-2017.
 */

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private View view;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        ((SelectedLocationListActivity)getActivity()).setCollapsingToolbarLayoutTitle("Settings");
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        TextView tv_reset = (TextView) view.findViewById(R.id.reset);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor spEditor = sp.edit();
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Reset All?")
                        .setMessage("Current Location will remain!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getActivity(), " Reset Done!", Toast.LENGTH_SHORT).show();
                                spEditor.putString("citylist", "");

                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Metric");
        categories.add("Imperial");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor spEditor = sp.edit();
        if (position == 0) {
            spEditor.putString("unit", "metric");
        } else {
            spEditor.putString("unit", "imperial");
        }
        spEditor.commit();
        Toast.makeText(parent.getContext(), item + " Selected", Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
