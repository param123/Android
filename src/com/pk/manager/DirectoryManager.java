package com.pk.manager;

import com.markupartist.android.widget.ActionBar.Action;



public class DirectoryManager extends AbstractFileListActivity {

	@Override
	protected FileObject getParentFileObject() {
		 String path = getIntent().getStringExtra("path");
		 String name = getIntent().getStringExtra("name");
		 String metaData = getIntent().getStringExtra("metadata");
		 FileObject parent = new FileObject(name, path, false,metaData);
		return parent;
	}

	@Override
	protected boolean showHomeAsUpEnabled() {
		return true;
	}
	
	protected Action getHomeAction() {
		return ActionBarUtil.getHomeAction(this);
	}

}
