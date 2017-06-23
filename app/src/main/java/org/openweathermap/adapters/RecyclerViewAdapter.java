package org.openweathermap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.openweathermap.openweathermap.R;
import org.openweathermap.utils.AppConstants;

import java.util.List;

/**
 * Created by LENOVO on 23-06-2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {

    private List<String> list;

    public class MyView extends RecyclerView.ViewHolder {

        public TextView placeName;
        public ImageView placeImage;

        public MyView(View view) {
            super(view);
            placeName = (TextView) view.findViewById(R.id.placeName);
            placeImage = (ImageView) view.findViewById(R.id.placeImage);
        }
    }

    public RecyclerViewAdapter(List<String> horizontalList) {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_horizontal, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        holder.placeName.setText(list.get(position).replace("-", ","));
        holder.placeImage.setBackgroundResource(AppConstants.getSelectCityImageDetails().get(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}