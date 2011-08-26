package com.pk.manager;

import com.markupartist.android.widget.ActionBar;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.markupartist.android.widget.actionbar.R;

public class SearchActivity extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	}
	
	private final Activity getActivityContext(){
		return this;
	}
	
	private void attachListenerToBtn(Button btn){
		btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Toast.makeText(v.getContext(),
                        "Hola..",
                        Toast.LENGTH_SHORT).show();
            	ListView lv = getListView();
            	lv.setVisibility(View.VISIBLE);
             	FileObject fo[] = new FileObject[]{new FileObject("Sample","/demo",true,"no metadata")};
             	FileAdapter fa = new FileAdapter(getActivityContext(), R.layout.items,fo);
             	setListAdapter(fa);
             	
            	
            }
        });
	}
	
	
}
