package com.pk.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
import com.markupartist.android.widget.actionbar.R;

public class ActionBarUtil {
	
	public static Action emptyAction  = new Action() {
		public int getDrawable() {
			return R.drawable.ic_title_home_default;
		}

		public void performAction(View view) {
			//Don't do anything.
		}
	};
	
	public static Action getHomeAction(Context context) {
		return new IntentAction(context, FileUtil.createHomeIntent(context), R.drawable.ic_title_home_default);
	}
	
	public static  Action getSearchButton(Context context,String path) {
		Intent i = FileUtil.createIntent(context,SearchActivity.class);
		i.putExtra("path", path);
		return new IntentAction(context, i, R.drawable.ic_title_search_default);
	}
	
	public static  Action getUpButton(Context context) {
		return new IntentAction(context, FileUtil.createIntent(context,SearchActivity.class), android.R.drawable.arrow_up_float);
	}
	
	public static  Action getViewButton(Activity context) {
		return new ToggleAction(context);
	}
	
}
