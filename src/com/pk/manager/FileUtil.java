package com.pk.manager;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	  

	  public static String fileMetaData(File file,SimpleDateFormat format){
		  long time = file.lastModified();
		  String timeFormat = formatTime(time, format);
		  boolean isWritable = file.canWrite();
		  boolean isReadable = file.canRead();
		  boolean isExecutable = file.exists();
		  StringBuilder sb = new StringBuilder();
		  if(file.isFile()){
			  sb.append(humanReadableByteCount(file.length(), false));
			  sb.append(" | ");
		  }
		  sb.append(timeFormat);
//		  if(isWritable){
//			  sb.append("rw");
//			  if(isExecutable){
//				  sb.append("-e");
//			  }
//		  }else if(isReadable){
//			  sb.append("r");
//			  if(isExecutable){
//				  sb.append("-e");
//			  }
//		  }else if(isExecutable){
//			  sb.append("e");
//		  }
		  return sb.toString();		  
	  }
	  
	  public static String humanReadableByteCount(long bytes, boolean si) {
		    int unit = si ? 1000 : 1024;
		    if (bytes < unit) return bytes + " Bytes";
		    int exp = (int) (Math.log(bytes) / Math.log(unit));
		    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
		}
	  
	  public static SimpleDateFormat getDateFormatter(){
		  return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa");
	  }
	  
	  public static String formatTime(long time,SimpleDateFormat format){
		 return format.format(new Date(time));		  
	  }

	  public static Intent createHomeIntent(Context context) {
	        Intent i = new Intent(context, FileManager.class);
	        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        return i;
	    }
	  
	  public static Intent createIntent(Context context,Class clazz) {
	        Intent i = new Intent(context, clazz);	        
	        return i;
	    }


}
