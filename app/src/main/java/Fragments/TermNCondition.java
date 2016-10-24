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

package Fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moon.fire.goapp.R;
import com.moon.fire.goapp.ResponsiblegamingActivity;


public class TermNCondition extends Fragment {

	private View mView;
	String time1;

	String dateString = "2016-09-25 00:00:00";


	public TermNCondition(){
		setRetainInstance(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(mView == null){
			mView = inflater.inflate(R.layout.term_n_condition, container, false);
			TextView txt=(TextView)mView.findViewById(R.id.termTxt);
			Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/COMIC.TTF");
			TextView txt1=(TextView)mView.findViewById(R.id.responsiblegamingTxt);
			txt1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), ResponsiblegamingActivity.class));
				}
			});

			txt.setTypeface(custom_font);
			txt1.setTypeface(custom_font);

		}else{
//			((ViewGroup) mView.getParent()).removeView(mView);
		}
		return mView;
	}
}
