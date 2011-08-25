package com.pk.manager;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;

public class FileUtil {
	
	  public static FileObject[] getFileObjectList(FileObject parent){
	    	
	    	File children[] = new File(parent.getPath()).listFiles(new FileFilter() {
				
				public boolean accept(File pathname) {
					return !pathname.isHidden() && pathname.getName().trim().length()>0;
				}
			});	    	
	    	FileObject fo[] = null;
	    	if(children!=null){
	    		SimpleDateFormat format = getDateFormatter();
		    	 fo = new FileObject[children.length];
		    	int count=0;
		    	for(File child:children){
		    		  fo[count++]= new FileObject(child.getName(),parent,child.isFile(),fileMetaData(child, format));
		    	}
	    	}
	    	
	    	return fo;	    	
	    }
	  
	  public static void openDirectory(Context c ,FileObject fo){
	    	Intent i = new Intent(c, DirectoryManager.class);
	    	i.putExtra("path", fo.getPath());
	    	i.putExtra("name", fo.getName());
	    	i.putExtra("metadata", fo.getMetaData());
	    	c.startActivity(i);	    	
	    }
	  
<<<<<<< HEAD
	  public static String fileMetaData(File file,SimpleDateFormat format){
		  long time = file.lastModified();
		  String timeFormat = formatTime(time, format);
		  boolean isWritable = file.canWrite();
		  boolean isReadable = file.canRead();
		  boolean isExecutable = file.exists();
		  StringBuilder sb = new StringBuilder();
		  sb.append(timeFormat).append(" | ");
		  if(isWritable){
			  sb.append("rw");
			  if(isExecutable){
				  sb.append("-e");
			  }
		  }else if(isReadable){
			  sb.append("r");
			  if(isExecutable){
				  sb.append("-e");
			  }
		  }else if(isExecutable){
			  sb.append("e");
		  }
		  return sb.toString();		  
	  }
	  
	  public static SimpleDateFormat getDateFormatter(){
		  return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa");
	  }
	  
	  public static String formatTime(long time,SimpleDateFormat format){
		 return format.format(new Date(time));		  
	  }
=======
	  public static Intent createHomeIntent(Context context) {
	        Intent i = new Intent(context, FileManager.class);
	        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        return i;
	    }
	  
	  public static Intent createIntent(Context context,Class clazz) {
	        Intent i = new Intent(context, clazz);
	        
	        return i;
	    }
>>>>>>> 11261157436ab96342b99fd03f3d18755ea55fe1
	    

}
