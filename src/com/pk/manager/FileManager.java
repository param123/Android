package com.pk.manager;

import java.io.File;

public class FileManager extends AbstractFileListActivity {
    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//      
//       
//        setListAdapter(new ArrayAdapter<FileObject>(this,R.layout.list_item, getChildren()));
//        handleOnClickListener();
//         
//    }

	@Override
	protected File getParentFile(){
		  File parentFile = File.listRoots()[0];
		  return parentFile;
		
	}
    
    
}