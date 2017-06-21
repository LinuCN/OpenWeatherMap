package org.openweathermap.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.openweathermap.openweathermap.R;
import org.openweathermap.openweathermap.SelectedLocationListActivity;
import org.openweathermap.utils.AppConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LENOVO on 19-06-2017.
 */

public class EditCityFragment extends Fragment {

    private View view;
    private ListView lv;
    ArrayAdapter<String> adapter;
    String cityList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_city, container, false);

        ((SelectedLocationListActivity)getActivity()).setCollapsingToolbarLayoutTitle("Remove City");
        TextView textView = (TextView) view.findViewById(R.id.noResults);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor spEditor = sp.edit();
        String cityListArray[] = sp.getString("citylist", "").split(",");
        cityList = sp.getString("citylist", "");
        if (cityListArray.length == 0) {
            textView.setVisibility(View.VISIBLE);
            return view;
        }
        List cities = Arrays.asList(cityListArray);
        lv = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.edit_city_list_item, R.id.city_name, cities);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String listItem = (String) lv.getItemAtPosition(position);

                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete " + listItem + "?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getActivity(), listItem + " Removed!", Toast.LENGTH_SHORT).show();
                                cityList = cityList.replace(listItem + ",", "");
                                spEditor.putString("citylist", cityList);
                                spEditor.commit();
                                List cities = Arrays.asList(cityList.split(","));
                                adapter = new ArrayAdapter<String>(getActivity(), R.layout.search_list_item, R.id.city_name, cities);
                                lv.setAdapter(adapter);

                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });
        return view;
    }

}
