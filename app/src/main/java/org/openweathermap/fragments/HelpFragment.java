package org.openweathermap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import org.openweathermap.openweathermap.R;
import org.openweathermap.openweathermap.SelectedLocationListActivity;

/**
 * Created by LENOVO on 19-06-2017.
 */

public class HelpFragment extends Fragment {
    private View view;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_help, container, false);

        ((SelectedLocationListActivity)getActivity()).setCollapsingToolbarLayoutTitle("Help");
        webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(null, html_value, "text/html", "utf-8", null);
        return view;
    }
    String html_value = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"></head><body style=\"width:300px; color: #00000; \">" +
            "<p><strong> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;HELP</strong></p>" +
            "<p><strong> 1. Add City</strong></p>" +
            "<p>&emsp;You can add Cities by opening Home Drawyer (Swipe from Left fo screen) " +
            "and select More Locations. you will be able to search cities with Names.</p>"+
            "<p><strong> 2. Remove Selected Cities</strong></p>"+
            "<p>&emsp;You can remove Cities by opening Home Drawyer (Swipe from Left fo screen) " +
            "and select Remove Locations. Click on the Cities you want to delete and Confirm the dialog that pops up.</p>"+
            "<p><strong> 3. View Today's weather details</strong></p>"+
            "<p>&emsp; By default it will show Current Location weather details. Add more Cities to get details of them.</p>"+
            "<p><strong> 4. View weather forecast info</strong></p>" +
            "<p>&emsp; Scroll down to view more! Its listed in the same page where Weather details has been shown.</p>"+
            "<p><strong> 5. View Current Location's weather(Default)</strong></p>" +
            "<p>&emsp; By default it will show Current Location weather details. Scroll up to view More!.</p>"+
            "</body></html>";

}
