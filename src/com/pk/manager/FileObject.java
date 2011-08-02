package com.pk.manager;

public class FileObject {
	
	private String name = null;
	private boolean isFile = false;
	private FileObject parent = null;
	
	
	public FileObject(String name,FileObject parent,boolean isFile){
		this.name = name;
		this.parent = parent;
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
		   return parent.getPath()+"/"+getName();
		}else{
			return getName();
		}
	}
	
	@Override
	public String toString() {
		return isFile?"File-":"Dir-"+getName();
	}
	
	

}
