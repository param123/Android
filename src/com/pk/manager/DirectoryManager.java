package com.pk.manager;


public class DirectoryManager extends AbstractFileListActivity {

	@Override
	protected FileObject getParentFileObject() {
		 String path = getIntent().getStringExtra("path");
		 String name = getIntent().getStringExtra("name");
		 FileObject parent = new FileObject(name, path, false);
		return parent;
	}
	
	
}
