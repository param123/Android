package com.pk.manager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileAdapter extends ArrayAdapter<FileObject> {
	
	private final Activity context;
	private final FileObject[] fo;

	public FileAdapter(Activity context, int textViewResourceId,
			FileObject[] objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.fo = objects;
	}
	
	static class ViewHolder {
		public ImageView imageView;
		public TextView textView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder will buffer the assess to the individual fields of the row
		// layout

		ViewHolder holder;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.list_item, null, true);
			holder = new ViewHolder();
			holder.textView = (TextView) rowView.findViewById(R.id.label);
			holder.imageView = (ImageView) rowView.findViewById(R.id.list_folder);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.textView.setText(fo[position].getName());
		// Change the icon for Windows and iPhone
		FileObject foi = fo[position];
		if (foi.isFile()) {
			holder.imageView.setImageResource(R.drawable.list_file);
		} else {
			holder.imageView.setImageResource(R.drawable.list_folder);
		}

		return rowView;
	}
	

	

}
