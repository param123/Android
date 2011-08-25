package com.pk.manager;

import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;



public class DirectoryManager extends AbstractFileListActivity {

	@Override
	protected FileObject getParentFileObject() {
		 String path = getIntent().getStringExtra("path");
		 String name = getIntent().getStringExtra("name");
		 FileObject parent = new FileObject(name, path, false);
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
