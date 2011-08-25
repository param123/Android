package com.pk.manager;


public class DirectoryManager extends AbstractFileListActivity {

	@Override
	protected FileObject getParentFileObject() {
		 String path = getIntent().getStringExtra("path");
		 String name = getIntent().getStringExtra("name");
		 String metaData = getIntent().getStringExtra("metadata");
		 FileObject parent = new FileObject(name, path, false,metaData);
		return parent;
	}
	
	
}
