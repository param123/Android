package com.pk.manager;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

public abstract class AbstractFileListActivity extends ListActivity {

	protected abstract FileObject getParentFileObject();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setHomeAction(getHomeAction());
		actionBar.setDisplayHomeAsUpEnabled(showHomeAsUpEnabled());
		actionBar.addAction(ActionBarUtil.getSearchButton(this));
		actionBar.addAction(ActionBarUtil.getViewButton(this));
		FileObject children[] = getChildren();
		if (children != null) {
			setListAdapter(new FileAdapter(this, R.layout.items, children));
		} else {
			setListAdapter(new FileAdapter(this,R.layout.items));
		}
		handleOnClickListener();
		setTitle(getParentFileObject().getPath());
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

	protected FileObject[] getChildren() {
		FileObject rootObject = getParentFileObject();
		FileObject children[] = FileUtil.getFileObjectList(rootObject);
		return children;
	}
	
	private void openDir(FileObject fo) {
		FileUtil.openDirectory(this, fo);
	}

}
