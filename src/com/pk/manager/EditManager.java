package com.pk.manager;

import java.io.File;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class EditManager {
	
	private static EditManager SINGLETON = null;
	
	private HashMap<String, Integer> stringImg = null;
	
	
	private EditManager(){
		stringImg = new HashMap<String, Integer>();
		stringImg.put("Copy", R.drawable.copy);
		stringImg.put("Paste", R.drawable.paste);		
		stringImg.put("Cut", R.drawable.cut);
		
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
	
	public void showPopup(final Context context,FileObject fo) {
		AlertDialog.Builder popupOpt = new AlertDialog.Builder(context);
		final String items[] = new String[] {"Copy","Paste","Cut","Properties"};
		popupOpt.setAdapter(new PopupAdapter(context,  items), new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				 Toast.makeText(context, items[which], Toast.LENGTH_SHORT).show();
				
			}
		});
		AlertDialog dialog = popupOpt.create();
        dialog.getListView().setPadding(5, 5, 5, 5);
        dialog.show();
	}
	
	public int getIcon(String key) {
		return stringImg.get(key);
	}
	
	public boolean imgAvaliable(String key) {
		return stringImg.containsKey(key);
	}

}
