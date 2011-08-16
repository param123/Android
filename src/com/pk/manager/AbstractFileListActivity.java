package com.pk.manager;

import java.io.File;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public abstract class AbstractFileListActivity extends ListActivity {
	
	
	
	protected abstract File getParentFile();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new FileAdapter(this,R.layout.list_item, getChildren()));
        handleOnClickListener();
         
    }
	
	protected void handleOnClickListener(){
		    ListView lv = getListView();
	        lv.setTextFilterEnabled(true);
	        lv.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	            	Log.i("FileManager", ""+position);
	            	FileObject fo = (FileObject) parent.getItemAtPosition(position);
	            	openDir(fo);
	            }

				
	          });
	}
	
	protected FileObject[] getChildren(){
		 FileObject rootObject = new FileObject(getParentFile().getName(), null,getParentFile().isFile());
	     FileObject children[] = FileUtil.getFileObjectList(getParentFile(), rootObject);
		return children;
	}
	
	private void openDir(FileObject fo){
    	FileUtil.openDirectory(this, fo);
    }
    

}
