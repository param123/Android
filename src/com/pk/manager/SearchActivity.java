package com.pk.manager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.actionbar.R;

public class SearchActivity extends Activity implements IOnClickHandler{
	
	private AsyncTask<String, FileObject, Void> asyncTask = null;
	private String currentDir = null;
	private ToggleAction toggleAction = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentDir = getIntent().getStringExtra("path");
		setContentView(R.layout.search);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setHomeAction(ActionBarUtil.getHomeAction(this));
		actionBar.setDisplayHomeAsUpEnabled(true);
		toggleAction = (ToggleAction)ActionBarUtil.getViewButton(this);
		actionBar.addAction(toggleAction);
		//actionBar.addAction(ActionBarUtil.getSearchButton(this));
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.file_search_options_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    spinner.setSelection(0);
	    
	    //we can attach onClick in xml as well.
	    Button searchButton = (Button)findViewById(R.id.searchBtn);
	    attachListenerToBtn(searchButton);
	    //handleOnClickListener();
	    setTitle(getResources().getString(R.string.search_title)+" "+currentDir);
	}
	
	private final Activity getActivityContext(){
		return this;
	}
	
//	protected void handleOnClickListener(AbsListView lv) {
////		ListView lv = (ListView)findViewById(android.R.id.list);
//		
//		lv.setTextFilterEnabled(true);
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				if(asyncTask!=null && asyncTask.getStatus()!=AsyncTask.Status.FINISHED) {
//				    asyncTask.cancel(true);
//				}
//			}
//
//		});
//	}
	
	
	private void attachListenerToBtn(Button btn){
		btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            	String searchText = getSearchText();
            	long position = getSearchSelectedOption();
            	if(searchText.trim().length()>0) {
            		ArrayAdapter<FileObject> foa = null;
            		AbsListView alv = null;
            		if(toggleAction.isListView()) {
            			alv = (ListView)findViewById(android.R.id.list);
            			alv.setVisibility(View.VISIBLE);
    	            	foa = new FileAdapter(getActivityContext(), R.layout.items,new ArrayList<FileObject>());
    	            	alv.setAdapter(foa);
            		}else {
            			alv = (GridView)findViewById(R.id.gridview);
            			alv.setVisibility(View.VISIBLE);
            			foa = new ImageAdapter(getActivityContext(), R.layout.griditems,new ArrayList<FileObject>());
            			alv.setAdapter(foa);
            		}
            		attachOnClickListener(alv);
            		asyncTask = new SearchAsyncTask(getActivityContext(),foa);
	            	if(position==1) {
	            		((SearchAsyncTask)asyncTask).setAlgo(((SearchAsyncTask)asyncTask).new TextAlgo());
	            	}
	               	asyncTask.execute(new String[] {currentDir,searchText});
            	}
     
            }
        });
	}
	
	private String getSearchText() {
		EditText text = (EditText)findViewById(R.id.searchEntry);
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
		return text.getText().toString();
	}
	
	private int getSearchSelectedOption() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		return spinner.getSelectedItemPosition();
	}
	
	@Override
	protected void onPause() {
		
		if(asyncTask!=null && asyncTask.getStatus()!=AsyncTask.Status.FINISHED) {
		    asyncTask.cancel(true);
		}
		super.onPause();
	}

	public void attachOnClickListener(AbsListView lv) {
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(asyncTask!=null && asyncTask.getStatus()!=AsyncTask.Status.FINISHED) {
				    asyncTask.cancel(true);
				}
			}

		});
		
	}
	
}
