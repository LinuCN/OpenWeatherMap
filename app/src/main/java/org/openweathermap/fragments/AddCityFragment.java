package org.openweathermap.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

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

public class AddCityFragment extends Fragment {

    private View view;
    private ListView lv;
    ArrayAdapter<String> adapter;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> cityList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_city, container, false);
        ((SelectedLocationListActivity)getActivity()).setCollapsingToolbarLayoutTitle("Add City");
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor spEditor = sp.edit();
        List cities = new ArrayList<String>(AppConstants.getAllCityDetails().keySet());
        lv = (ListView) view.findViewById(R.id.list_view);
        inputSearch = (EditText) view.findViewById(R.id.inputSearch);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.search_list_item, R.id.city_name, cities);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String listItem = (String) lv.getItemAtPosition(position);
                String cityList = sp.getString("citylist", "Bangalore,");
                if (cityList.contains(listItem)) {
                    Toast.makeText(getActivity(), "Its Already Added!", Toast.LENGTH_LONG).show();
                } else {
                    cityList += listItem + ",";
                    spEditor.putString("citylist", cityList);
                    spEditor.commit();
                    Toast.makeText(getActivity(), listItem + " Added! ", Toast.LENGTH_LONG).show();
                }
            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        return view;
    }

}
