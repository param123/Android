package com.pk.manager;

import android.content.Context;
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
	
	public static  Action getSearchButton(Context context) {
		return new IntentAction(context, FileUtil.createIntent(context,SearchActivity.class), R.drawable.ic_title_search_default);
	}
	
	public static  Action getUpButton(Context context) {
		return new IntentAction(context, FileUtil.createIntent(context,SearchActivity.class), android.R.drawable.arrow_up_float);
	}
	
	public static  Action getViewButton(Context context) {
		return new IntentAction(context, FileUtil.createIntent(context,SearchActivity.class), android.R.drawable.ic_menu_view);
	}

}
