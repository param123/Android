package com.pk.manager;

import java.io.File;

public class FileManager extends AbstractFileListActivity {

	@Override
	protected FileObject getParentFileObject(){
		  File parentFile = File.listRoots()[0];
		  FileObject fo = new FileObject(parentFile.getName(), 
							  parentFile.getPath(), 
							  false,
							  FileUtil.fileMetaData(parentFile, FileUtil.getDateFormatter()));
		  return fo;
		
	}
    
    
}