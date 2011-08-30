package com.pk.manager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.actionbar.R;

public abstract class AbstractFileListActivity extends Activity implements IOnClickHandler{

	protected abstract FileObject getParentFileObject();
	
	private ToggleAction toggleAction = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setHomeAction(getHomeAction());
		actionBar.setDisplayHomeAsUpEnabled(showHomeAsUpEnabled());
		FileObject parentFileObject = getParentFileObject();
		actionBar.addAction(ActionBarUtil.getSearchButton(this,parentFileObject.getPath()));
		toggleAction =(ToggleAction)ActionBarUtil.getViewButton(this);
		actionBar.addAction(toggleAction);
		setAdapter();
		handleOnClickListener();
		setTitle(parentFileObject.getPath());
	}
	
	private void setAdapter() {
		List<FileObject> children = getChildren();
		FileAdapter fa = null;
		if (children != null&& !children.isEmpty()) {
			fa = new FileAdapter(this, R.layout.items, children);
		} else {
			fa = new FileAdapter(this,R.layout.items,new ArrayList<FileObject>());
		}
		ListView lv = (ListView)findViewById(android.R.id.list);
		lv.setAdapter(fa);
	}

	protected void handleOnClickListener() {
		attachOnClickListener((ListView)findViewById(android.R.id.list));
	}
	
	public void attachOnClickListener(AbsListView view) {
		view.setTextFilterEnabled(true);
		view.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FileObject fo = (FileObject) parent.getItemAtPosition(position);
				if (!fo.isFile()) {
					openDir(fo);
				}
			}

		});
	}
	
	
//	public void onContentChanged() {
//        super.onContentChanged();
//        if(toggleAction!=null) {
//	        AbsListView alv = null;
//	        if(toggleAction.isListView()) {
//	        	alv = (ListView)findViewById(android.R.id.list);
//	        }else {
//	        	alv = (GridView)findViewById(R.id.gridview);
//	        }
//	        
//	        IDataStore ids = (IDataStore)alv.getAdapter();
//	        View emptyView = findViewById(android.R.id.empty);
//	        if(ids.getDataList().isEmpty()) {
//	        	if(emptyView!=null) {
//	        	    alv.setEmptyView(emptyView);
//	        	}
//	        }
//        }
//	}
	
	protected abstract boolean showHomeAsUpEnabled();
	
	protected abstract Action getHomeAction();

	protected List<FileObject> getChildren() {
		FileObject rootObject = getParentFileObject();
		return FileUtil.getFileObjectList(rootObject);
	}
	
	private void openDir(FileObject fo) {
		FileUtil.openDirectory(this, fo);
	}

}
