package com.pk.manager;

import java.io.File;

import android.view.View;

import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.actionbar.R;

public class FileManager extends AbstractFileListActivity {

	@Override
	protected FileObject getParentFileObject(){
		  File parentFile = File.listRoots()[0];
		  FileObject fo = new FileObject(parentFile.getName(), parentFile.getPath(), false);
		  return fo;
		
	}

	@Override
	protected boolean showHomeAsUpEnabled() {
		return false;
	}
	
	protected Action getHomeAction() {
		Action emptyAction = new Action() {

			public int getDrawable() {
				return R.drawable.ic_title_home_default;
			}

			public void performAction(View view) {
				//Don't do anything.
			}
			
		};
		
		return emptyAction;
	}

    
}