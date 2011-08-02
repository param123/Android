package com.pk.manager;

import java.io.File;

import android.content.Context;
import android.content.Intent;

public class FileUtil {
	
	  public static FileObject[] getFileObjectList(File f,FileObject parent){
	    	
	    	File children[] = f.listFiles();
	    	FileObject fo[] = new FileObject[0];
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
	    	c.startActivity(i);	    	
	    }
	    

}
