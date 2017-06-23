package org.openweathermap.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v4.app.Fragment;

import org.openweathermap.adapters.RecyclerViewAdapter;
import org.openweathermap.openweathermap.R;
import org.openweathermap.openweathermap.SelectedLocationListActivity;
import org.openweathermap.utils.AppConstants;
import org.openweathermap.utils.AppUtils;

/**
 * Created by LENOVO on 19-06-2017.
 */

public class SelectCitiesFragment extends Fragment {

    RecyclerView recyclerView;
    List<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout;
    View ChildView, view;
    int RecyclerViewItemPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_cities, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview1);
        ((SelectedLocationListActivity) getActivity()).setCollapsingToolbarLayoutTitle("Select City");
        view.findViewById(R.id.txt_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.setAppInitializedProperly(getActivity());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    String name = fm.getBackStackEntryAt(0).getName();
                    fm.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                ((SelectedLocationListActivity) getActivity()).showAppBarAsExpanded(true, true);
                addFragment(R.id.frame_container, new ViewPagerFragment(), "ViewPagerFragment", "ViewPagerBackFragment");
            }
        });

        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        AddItemsToRecyclerViewArrayList();

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Number);

        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor spEditor = sp.edit();
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);
                    String listItem = Number.get(RecyclerViewItemPosition);
                    String cityList = AppUtils.getSavedCitiesString(getActivity());
                    if (cityList.contains(listItem)) {
                        Toast.makeText(getActivity(), "Its Already Added!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), listItem.replace("-", ",") + " Added! ", Toast.LENGTH_LONG).show();
                        cityList += listItem + ",";
                        spEditor.putString("citylist", cityList);
                        spEditor.commit();
                    }
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return view;
    }

    public void AddItemsToRecyclerViewArrayList() {
        Number = new ArrayList<String>(AppConstants.getSelectCityDetails().keySet());
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag, @NonNull String backName) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backName)
                .commit();
    }
}
