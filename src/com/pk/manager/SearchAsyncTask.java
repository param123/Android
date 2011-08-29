package com.pk.manager;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.actionbar.R;

public class SearchAsyncTask extends AsyncTask<String, FileObject, Void>{
	
	private ListActivity activity = null;
	private FileAdapter adapter = null;
	private Pattern pattern = null;
	
	// default option
	private SearchAlgo sAlgo = new NameAlgo();
	
	public SearchAsyncTask(ListActivity activity) {
		this.activity = activity;
		adapter = (FileAdapter)activity.getListAdapter();
	}
	
	public void setAlgo(SearchAlgo algo) {
		this.sAlgo = algo;
	}

	

	@Override
	protected Void doInBackground(String... params) {
		String dirPath = params[0];
		final String searchToken = params[1];
		File startDir = null;
		if(dirPath==null||dirPath.trim().length()==0) {
			startDir = File.listRoots()[0];
		}else {
			startDir = new File(dirPath);
		}
		
		if(!startDir.exists()) {
			Toast.makeText(activity,"Search cann't be performed due to \r\n absence of directory "+startDir.getPath(),
                    Toast.LENGTH_SHORT).show();
		}
		
		search(startDir, searchToken);
		
		return null;
	}
	
	private File[] search(File f,final String searchToken) {
		pattern = Pattern.compile(searchToken);
		File listFile[] = f.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if(isCancelled()) {
					throw new RuntimeException();
				}
				if( !pathname.isHidden() && pathname.getName().trim().length()>0) {
					
					if(pathname.isFile()) {
					  //FileObject fo = new FileObject("Sample"+i,"/demo"+i,true,"no metadata"+i);
						if(sAlgo.matched(pathname,searchToken)) {
							FileObject fo = new FileObject(pathname.getName(),pathname.getPath(),true,pathname.getPath());
					       publishProgress(fo);
					       return false;
						}
					}else {
						return true;
					}
				}
				return false;
			}
		});
		
		if(listFile!=null&&listFile.length>0) {
			for(File child:listFile) {
				if(isCancelled()) {
					throw new RuntimeException();
				}
				search(child, searchToken);				
			}
		}
		
		return listFile;
	}
	
	

	@Override
	protected void onPreExecute() {
		ActionBar actionBar = (ActionBar) activity.findViewById(R.id.actionbar);
     	actionBar.setProgressBarVisibility(View.VISIBLE);
	}
	
	@Override
	protected void onProgressUpdate(FileObject... values) {
		adapter.add(values[0]);
	}
	
	@Override
	protected void onPostExecute(Void result) {
		ActionBar actionBar = (ActionBar) activity.findViewById(R.id.actionbar);
     	actionBar.setProgressBarVisibility(View.GONE);
	}
	
	@Override
	protected void onCancelled() {
		ActionBar actionBar = (ActionBar) activity.findViewById(R.id.actionbar);
     	actionBar.setProgressBarVisibility(View.GONE);
	}
	
	public interface SearchAlgo{
		public boolean matched(File file,String matchString) ;
	}
	
	
	public class NameAlgo implements SearchAlgo{

		public boolean matched(File file,String matchString) {
			  return pattern.matcher(file.getName()).find();
		}
		
	}
	
	public class TextAlgo implements SearchAlgo{
		
		public boolean matched(File file, String matchString) {
			CharSequence charSeq = FileUtil.contentOfFile(file);
			if(charSeq!=null) {
				 return pattern.matcher(charSeq).find();
			}
			
			return false;
		}
		
		
		
	}
}
