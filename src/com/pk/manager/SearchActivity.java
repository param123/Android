package com.pk.manager;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.actionbar.R;

public class SearchActivity extends ListActivity {
	
	private AsyncTask<String, FileObject, Void> asyncTask = null;
	private String currentDir = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentDir = getIntent().getStringExtra("path");
		
		setContentView(R.layout.search);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setHomeAction(ActionBarUtil.getHomeAction(this));
		actionBar.setDisplayHomeAsUpEnabled(true);
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
	    handleOnClickListener();
	    setTitle(getResources().getString(R.string.search_title)+" "+currentDir);
	}
	
	private final ListActivity getActivityContext(){
		return this;
	}
	
	protected void handleOnClickListener() {
		ListView lv = getListView();
		
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
	
	
	private void attachListenerToBtn(Button btn){
		btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            	String searchText = getSearchText();
            	long position = getSearchSelectedOption();
            	if(searchText.trim().length()>0) {
	            	ListView lv = getListView();
	            	lv.setVisibility(View.VISIBLE);
	            	FileAdapter fa = new FileAdapter(getActivityContext(), R.layout.items);
	             	getActivityContext().setListAdapter(fa);
	            	asyncTask = new SearchAsyncTask(getActivityContext());
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
	
}
