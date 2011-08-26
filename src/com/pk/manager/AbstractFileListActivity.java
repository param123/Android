package com.pk.manager;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.actionbar.R;

public abstract class AbstractFileListActivity extends ListActivity {

	protected abstract FileObject getParentFileObject();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setHomeAction(getHomeAction());
		actionBar.setDisplayHomeAsUpEnabled(showHomeAsUpEnabled());
		FileObject parentFileObject = getParentFileObject();
		actionBar.addAction(ActionBarUtil.getSearchButton(this,parentFileObject.getPath()));
		actionBar.addAction(ActionBarUtil.getViewButton(this));
		List<FileObject> children = getChildren();
		FileAdapter fa = null;
		if (children != null&& !children.isEmpty()) {
			fa = new FileAdapter(this, R.layout.items, children);
		} else {
			fa = new FileAdapter(this,R.layout.items);
		}
		setListAdapter(fa);
		handleOnClickListener();
		setTitle(parentFileObject.getPath());
	}

	protected void handleOnClickListener() {
		ListView lv = getListView();
		
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FileObject fo = (FileObject) parent.getItemAtPosition(position);
				if (!fo.isFile()) {
					openDir(fo);
				}
			}

		});
	}
	
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
