package com.pk.manager;

import com.markupartist.android.widget.ActionBar;

import android.app.Activity;
import android.os.Bundle;

public class SearchActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setHomeAction(ActionBarUtil.getHomeAction(this));
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.addAction(ActionBarUtil.getSearchButton(this));
	}

}
