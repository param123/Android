package com.pk.manager;

import java.io.File;
import java.io.FileFilter;

import android.content.Context;
import android.content.Intent;

public class FileUtil {
	
	  public static FileObject[] getFileObjectList(FileObject parent){
	    	
	    	File children[] = new File(parent.getPath()).listFiles(new FileFilter() {
				
				public boolean accept(File pathname) {
					return !pathname.isHidden() && pathname.getName().trim().length()>0;
				}
			});	    	
	    	FileObject fo[] = null;
	    	if(children!=null){
		    	 fo = new FileObject[children.length];
		    	int count=0;
		    	for(File child:children){
		    		  fo[count++]= new FileObject(child.getName(),parent,child.isFile());
		    	}
	    	}
	    	
	    	return fo;	    	
	    }
	  
	  public static void openDirectory(Context c ,FileObject fo){
	    	Intent i = new Intent(c, DirectoryManager.class);
	    	i.putExtra("path", fo.getPath());
	    	i.putExtra("name", fo.getName());
	    	c.startActivity(i);	    	
	    }
	    

}
