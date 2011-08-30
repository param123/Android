package com.pk.manager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.actionbar.R;

public class ToggleAction implements Action{
	
	private Activity currentActivity = null;
	boolean isListView = true;
	int drawable_id = R.drawable.ic_title_grid_default;
	public ToggleAction(Activity context) {
		this.currentActivity = context;
	}

	public int getDrawable() {
		return drawable_id;
	}

	public void performAction(View view) {
		if(isListView) {
			List<FileObject> list = removeListView();
			setGridView(list);
			drawable_id = R.drawable.ic_title_list_default;
		}else {
			List<FileObject> list = removeGridView();
			setListView(list);
			drawable_id = R.drawable.ic_title_grid_default;
		}
		isListView=!isListView;
		ActionBar actionBar = (ActionBar) currentActivity.findViewById(R.id.actionbar);
		actionBar.removeAction(this);
		actionBar.addAction(this);
	}
	
	private List<FileObject> removeListView() {
		ListView lv = (ListView)currentActivity.findViewById(android.R.id.list);
		List<FileObject> list  = null;
		if(lv.getAdapter()!=null) {
		   list = ((IDataStore)lv.getAdapter()).getDataList();
		}else {
			list = new ArrayList<FileObject>();
		}
		removeView(lv);
		
		return list;
	}
	
	private List<FileObject> removeGridView() {
		GridView gv = (GridView)currentActivity.findViewById(R.id.gridview);
		List<FileObject> list  = null;
		if(gv.getAdapter()!=null) {
		   list = ((IDataStore)gv.getAdapter()).getDataList();
		}else {
			list = new ArrayList<FileObject>();
		}
		removeView(gv);
		return list;
	}
	
	private void removeView(View v) {
		//ViewGroup viewGroup = (ViewGroup)v.getParent();
		//viewGroup.removeView(v);
		v.setVisibility(View.GONE);
	}
	
	private void setGridView(List<FileObject> list) {
		GridView gv = (GridView)currentActivity.findViewById(R.id.gridview);
		ImageAdapter adapter = new ImageAdapter(currentActivity,R.layout.griditems,list);
		gv.setAdapter(adapter);
		gv.setVisibility(View.VISIBLE);
		((IOnClickHandler)currentActivity).attachOnClickListener(gv);
	}
	
	private void setListView(List<FileObject> list) {
		ListView lv = (ListView)currentActivity.findViewById(android.R.id.list);
		FileAdapter adapter = new FileAdapter(currentActivity,R.layout.items, list);
		lv.setAdapter(adapter);
		lv.setVisibility(View.VISIBLE);
		((IOnClickHandler)currentActivity).attachOnClickListener(lv);
	}
	
	public boolean isListView() {
		return isListView;
	}
}
