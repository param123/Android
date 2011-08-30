package com.pk.manager;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

public class FileUtil {
	
	  public static List<FileObject> getFileObjectList(FileObject parent){
	    	
	    	File children[] = new File(parent.getPath()).listFiles(new FileFilter() {
				
				public boolean accept(File pathname) {
					return !pathname.isHidden() && pathname.getName().trim().length()>0;
				}
			});	    	
	    	List<FileObject> foList = null;
	    	if(children!=null){
	    		SimpleDateFormat format = getDateFormatter();
	    		foList = new ArrayList<FileObject>(children.length);
		    	for(File child:children){
		    		  foList.add(new FileObject(child.getName(),parent,child.isFile(),fileMetaData(child, format)));
		    	}
	    	}
	    	
	    	return foList;	    	
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
	  
	  public static CharSequence contentOfFile(File filename)  {
		  
		    FileInputStream fis = null; 
		    CharBuffer cbuf = null;
		    FileChannel fc = null;
		    ByteBuffer bbuf = null;
		    try {
			    fis = new FileInputStream(filename);
			    
			    fc = fis.getChannel();
			    Log.d("file size"+ fc.size(),"");
			    // Create a read-only CharBuffer on the file
			     bbuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int)fc.size());
			     cbuf = Charset.forName("8859_1").newDecoder().decode(bbuf);
		    }catch(Exception e) {
		    	Log.d("Error while reading file "+filename,Log.getStackTraceString(e));
		    }finally {
		    	
		    	if(fc!=null) {
		    		try {
						fc.close();
					} catch (IOException e) {
					}
		    	}
		    	
		    	if(fis!=null) {
		    		try {
						fis.close();
					} catch (IOException e) {
						
					}
		    	}
		    	cbuf = null;
		    	bbuf = null;
		    	
		    }
		    return cbuf;
		}
	  
	  public static Intent openFileIntent(String file,Context context) {
		 return openFileIntent(new File(file), context);
	  }
	  
	  public static Intent openFileIntent(File file,Context context) {
		  MimeTypeMap mime = MimeTypeMap.getSingleton();
		  Uri path = Uri.fromFile(file);
		  String type = mime.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(path.getPath()));
		  Intent intent = null;
		  if(type!=null) {
	          intent = new Intent(Intent.ACTION_VIEW);          
	          intent.setDataAndType(path, type);
	          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		  }
		  return intent;
	  }
	  
	  public static Drawable getDefaultIcon(Intent intent,Context context) {
             ResolveInfo resolveInfo =  context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
	          if(resolveInfo!=null) {
	            return resolveInfo.loadIcon(context.getPackageManager());
	          }
          return null;
		}
          



}
