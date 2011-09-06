package com.pk.manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PopupAdapter extends ArrayAdapter<String> {

	
	
	public PopupAdapter(Context context, String[] objects) {
		super(context, 0, objects);
	}
	
	static class ViewHolder {
		public ImageView imageView;
		public TextView textView;
	}


	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater =  (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			rowView = inflater.inflate(R.layout.popup_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) rowView.findViewById(R.id.pop_text);
			holder.imageView = (ImageView) rowView.findViewById(R.id.pop_img);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		
		holder.textView.setText(getItem(position));
		holder.imageView.setImageResource(R.drawable.paste);
		
		return rowView;
	}

}
