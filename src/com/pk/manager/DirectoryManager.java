package com.pk.manager;

import java.io.File;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DirectoryManager extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    String path = getIntent().getStringExtra("path");
	    File parent = new File(path);
	    FileObject fo[] = FileUtil.getFileObjectList(parent, new FileObject(parent.getName(), 
	    		new FileObject(parent.getParentFile().getName(), null,false),false));
        
        setListAdapter(new ArrayAdapter<FileObject>(this,R.layout.list_item, fo));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	Log.i("DirectoryManager", ""+position);
            	FileObject fo = (FileObject) parent.getItemAtPosition(position);
            	openDir(fo);
            }

			
          });
	}
	
	 private void openDir(FileObject fo){
	    	FileUtil.openDirectory(this, fo);
	    }
	
	
}
