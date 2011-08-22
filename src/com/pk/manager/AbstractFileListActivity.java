package com.pk.manager;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public abstract class AbstractFileListActivity extends ListActivity {

	protected abstract FileObject getParentFileObject();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FileObject children[] = getChildren();
		if (children != null) {
			setListAdapter(new FileAdapter(this, R.layout.list_item, children));
		} else {
			setListAdapter(new FileAdapter(this, R.layout.list_item));
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
