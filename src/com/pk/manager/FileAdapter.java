package com.pk.manager;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FileAdapter extends ArrayAdapter<FileObject> implements IDataStore{
	
	private Activity context;
	List<FileObject> foList = null;

	public FileAdapter(Activity context, int textViewResourceId, List<FileObject> foList) {
		super(context, textViewResourceId, foList);
		this.context = context;
		this.foList = foList;
	}
	

//	public FileAdapter(Activity context, int textViewResourceId) {
//		super(context, textViewResourceId);
//		this.context = context;
//		this.foList = new ArrayList<FileObject>();
//	}
	
	static class ViewHolder {
		public ImageView imageView;
		public TextView textView;
		public TextView attrView;
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
			
			rowView = inflater.inflate(R.layout.items, null);
			holder = new ViewHolder();
			holder.textView = (TextView) rowView.findViewById(R.id.label);
			holder.imageView = (ImageView) rowView.findViewById(R.id.list_folder);
			holder.attrView = (TextView) rowView.findViewById(R.id.secondLine);
			
			rowView.setTag(holder);
			
			
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		
		rowView.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				AlertDialog.Builder popupOpt = new AlertDialog.Builder(context);
				final String items[] = new String[] {"Copy","Paste","Delete","Properties"};
				popupOpt.setItems(items, new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						 Toast.makeText(context, items[which], Toast.LENGTH_SHORT).show();
						
					}
				});
				System.out.println("long click ");
				popupOpt.create().show();
				
				return true;
			}
		});
		
		holder.textView.setText(getItem(position).getName());
		holder.attrView.setText(getItem(position).getMetaData());
		// Change the icon for Windows and iPhone
		FileObject foi = getItem(position);
		if (foi.isFile()) {
			Intent intent = FileUtil.openFileIntent(getItem(position).getPath(), context);
			
			if(intent!=null) {
				Drawable d = FileUtil.getDefaultIcon(intent, context);
				if(d!=null) {
					holder.imageView.setImageDrawable(d);
				}else {
					holder.imageView.setImageResource(R.drawable.list_file);
				}
			}else {
			holder.imageView.setImageResource(R.drawable.list_file);
			}
		} else {
			holder.imageView.setImageResource(R.drawable.list_folder);
		}

		return rowView;
	}
	
	@Override
	public void add(FileObject object) {
		super.add(object);
		foList.add(object);
	}
	
	@Override
	public FileObject getItem(int position) {
		return super.getItem(position);
	}
	

	public List<FileObject> getDataList() {
		return foList;
	}
	
	

}
