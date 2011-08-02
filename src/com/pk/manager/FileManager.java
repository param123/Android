package com.pk.manager;

import java.io.File;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FileManager extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File parentFile = File.listRoots()[0];
        FileObject rootObject = new FileObject(parentFile.getName(), null,parentFile.isFile());
        final FileObject children[] = FileUtil.getFileObjectList(parentFile, rootObject);
        
        setListAdapter(new ArrayAdapter<FileObject>(this,R.layout.list_item, children));
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
    
    private void openDir(FileObject fo){
    	FileUtil.openDirectory(this, fo);
    }
    
    
}