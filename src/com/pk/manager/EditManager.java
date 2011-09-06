package com.pk.manager;

import java.io.File;

public class EditManager {
	
	private static EditManager SINGLETON = null;
	
	private EditManager(){
		
	}
	
	public static EditManager getInstance(){
		if(SINGLETON==null){
			SINGLETON = new EditManager();
		}
		return SINGLETON;
	}
	
	public static void setNull(){
		SINGLETON = null;
	}
	
	
	public boolean copy(File f){
		if(f.canRead()){
			 if(f.isDirectory()){
				
			 }else{
				 
			 }
		}
		
		return false;
	}

}
