package com.pk.manager;

import java.io.File;

public class FileObject {
	
	private static final long serialVersionUID = 1L;
	
	private String name = null;
	private boolean isFile = false;
	private FileObject parent = null;
	private String path = null;
	
	public FileObject(String name,FileObject parent,boolean isFile){
		this.name = name;
		this.parent = parent;
		this.isFile = isFile;
	}
	
	public FileObject(String name,String path,boolean isFile){
		this.name = name;
		this.path = path;
		this.isFile = isFile;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o!=null && o instanceof FileObject ){
			FileObject fo = (FileObject)o;
			if(!fo.isNull()){
				return fo.getName().equals(getName());
			}
		}
		
		return false;
	}
	
	public boolean isNull(){
		return name!=null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public FileObject getParent() {
		return parent;
	}

	public void setParent(FileObject parent) {
		this.parent = parent;
	}
	
	public String getPath(){
		if(parent!=null){
		   return parent.getPath()+File.separator+getName();
		}else{
			return path;
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public boolean isFile(){
		return isFile;
	}
	
}
