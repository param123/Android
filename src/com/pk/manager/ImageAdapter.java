package com.pk.manager;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends ArrayAdapter<FileObject> implements IDataStore{
    private Activity mContext;
    List<FileObject> foList = null;

    
    public ImageAdapter(Activity c,int textResourceId, List<FileObject> foList) {
    	super(c, textResourceId, foList);
        mContext = c;
        this.foList = foList;
    }

    public int getCount() {
        return foList.size();
    }


    static class ViewHolder {
		public ImageView imageView;
		public TextView textView;
	}
    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
    	
    	
    	ViewHolder holder;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			
			rowView = inflater.inflate(R.layout.griditems, null);
			holder = new ViewHolder();
			holder.textView = (TextView) rowView.findViewById(R.id.grid_label);
			holder.imageView = (ImageView) rowView.findViewById(R.id.grid_img);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		
		holder.textView.setText(getItem(position).getName());
		// Change the icon for Windows and iPhone
		FileObject foi = getItem(position);
		if (foi.isFile()) {
			Intent intent = FileUtil.openFileIntent(getItem(position).getPath(), mContext);
			if(intent!=null) {
				Drawable d = FileUtil.getDefaultIcon(intent, mContext);
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
		
         rowView.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				EditManager.getInstance().showPopup(mContext, null);				
				return true;
			}
		});
		
		rowView.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				FileObject fo = (FileObject) getItem(position);
				if (fo.isFile()) {
					FileUtil.openFile(mContext, fo);
				}else {
					FileUtil.openDirectory(mContext, fo);
				}
			}
		});
    	
//        ImageView imageView;
//        if (convertView == null) {  // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }

        //imageView.setImageResource(mThumbIds[position]);
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
