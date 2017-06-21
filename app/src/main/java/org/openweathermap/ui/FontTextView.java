package org.openweathermap.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import org.openweathermap.openweathermap.SelectedLocationListActivity;

public class FontTextView extends TextView{

	public FontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(isInEditMode()){
			return ;
		}
		setTypeface(SelectedLocationListActivity.getTypeface());
	}

}
