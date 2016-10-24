/*
 * Copyright 2013 - learnNcode (learnncode@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.moon.fire.goapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import Fragments.GameNPrizeFragment;
import Fragments.HomeFragment;
import Fragments.LeaderboardFragment;
import Fragments.TermNCondition;


public class BaseActivity extends FragmentActivity {

	public static FragmentTabHost mTabHost;
	TextView tx,avtarTxt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	/*	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

	*/

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.BLACK);
		}

		setContentView(R.layout.activity_base);
		String yourLocked = WellcomeActivity.sharedpreferences.getString("AVTAR NAME", "");
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");
		avtarTxt = (TextView)findViewById(R.id.avtarNameTxt);
		avtarTxt.setTypeface(custom_font);
        avtarTxt.setText(yourLocked);

		mTabHost  = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("", getResources().getDrawable(R.drawable.home_btn)), HomeFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("",getResources().getDrawable(R.drawable.games_prizes)), GameNPrizeFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("",getResources().getDrawable(R.drawable.leaderboard)), LeaderboardFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("",getResources().getDrawable(R.drawable.tc)), TermNCondition.class, null);

		mTabHost.setCurrentTabByTag("tag1");

		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {

		/*	TextView textView = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			if(textView.getLayoutParams() instanceof RelativeLayout.LayoutParams){

				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.addRule(RelativeLayout.CENTER_VERTICAL);
				params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
				params.width  = RelativeLayout.LayoutParams.WRAP_CONTENT;
				mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title).setLayoutParams(params);

			}else if(textView.getLayoutParams() instanceof LinearLayout.LayoutParams){
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
				params.gravity = Gravity.CENTER;
				mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title).setLayoutParams(params);
			}*/
			View v =  mTabHost.getTabWidget().getChildAt(i);

			// Look for the title view to ensure this is an indicator and not a divider.
			TextView tv = (TextView)v.findViewById(android.R.id.title);
			if(tv == null) {
				continue;
			}
v.setBackgroundResource(R.drawable.tabbar_selector);
		}

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {

				android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
				HomeFragment doctorSearchFragment = (HomeFragment) fragmentManager.findFragmentByTag("tab1");
				GameNPrizeFragment tabTwoFrgment = (GameNPrizeFragment) fragmentManager.findFragmentByTag("tab2");
				LeaderboardFragment Profile = (LeaderboardFragment) fragmentManager.findFragmentByTag("tab3");
				TermNCondition more = (TermNCondition) fragmentManager.findFragmentByTag("tab4");

				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


				if(tabId.equalsIgnoreCase("tab1")){
					if(doctorSearchFragment != null){
						if(tabTwoFrgment != null){
							fragmentTransaction.hide(tabTwoFrgment);
						}
						if(Profile != null){
							fragmentTransaction.hide(Profile);
						}
						if(more != null){
							fragmentTransaction.hide(more);
						}
						fragmentTransaction.show(doctorSearchFragment);
					}
				}else if(tabId.equalsIgnoreCase("tab2"))

				{
					if(tabTwoFrgment != null){
						if(doctorSearchFragment != null){
							fragmentTransaction.hide(doctorSearchFragment);
						}
						if(Profile != null){
							fragmentTransaction.hide(Profile);
						}
						if(more != null){
							fragmentTransaction.hide(more);
						}
						fragmentTransaction.show(tabTwoFrgment);   
					}
				}else if(tabId.equalsIgnoreCase("tab3"))

				{
					if(Profile != null){
						if(doctorSearchFragment != null){
							fragmentTransaction.hide(doctorSearchFragment);
						}
						if(tabTwoFrgment != null){
							fragmentTransaction.hide(tabTwoFrgment);
						}
						if(more != null){
							fragmentTransaction.hide(more);
						}
						fragmentTransaction.show(Profile);
					}
				}else

				{
					if(more != null){
						if(doctorSearchFragment != null){
							fragmentTransaction.hide(doctorSearchFragment);
						}
						if(tabTwoFrgment != null){
							fragmentTransaction.hide(tabTwoFrgment);
						}
						if(Profile != null){
							fragmentTransaction.hide(Profile);
						}

						fragmentTransaction.show(more);
					}
				}
				fragmentTransaction.commit();         
			}
		});
	}
	public static void tabChange()
	{
		mTabHost.setCurrentTabByTag("tag2");

	}
}
