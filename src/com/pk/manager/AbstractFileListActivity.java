package com.pk.manager;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

public abstract class AbstractFileListActivity extends ListActivity {

	protected abstract FileObject getParentFileObject();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setHomeAction(getHomeAction());
		actionBar.setDisplayHomeAsUpEnabled(showHomeAsUpEnabled());
		actionBar.addAction(addSearchButton());
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
	
	private Action addSearchButton() {
		return new IntentAction(this, FileUtil.createIntent(this,SearchActivity.class), android.R.drawable.ic_menu_search);
	}
	
	
	protected abstract boolean showHomeAsUpEnabled();
	
	protected abstract Action getHomeAction();

	protected FileObject[] getChildren() {
		// File parentFile = getParentFileObject();
		FileObject rootObject = getParentFileObject();
		FileObject children[] = FileUtil.getFileObjectList(rootObject);
		return children;
	}
	
	

	private void openDir(FileObject fo) {
		FileUtil.openDirectory(this, fo);
	}

}
