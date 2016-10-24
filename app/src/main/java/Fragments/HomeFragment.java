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

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.moon.fire.goapp.BaseActivity;
import com.moon.fire.goapp.R;
import com.moon.fire.goapp.ResponsiblegamingActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import Util.AppLog;
import Util.AppUrl;
import Util.DialogInterfacecustom;
import Util.GameListModal;
import web.IResponse;
import web.Web;


public class HomeFragment extends Fragment implements IResponse {

	private View mView;
	ListView lv,msgLv;
	ProgressDialog mProgressDialog;
	private Handler handler;
String time1,time2;


	TextView tx,msgContentTxt;
	Button okBtn;
	public HomeFragment(){
		setRetainInstance(true);
	}
	ArrayList<String> list=new ArrayList<>();
	ImageView closeBtn,msgBtn;
	LinearLayout msgLyt;
	public ArrayList<GameListModal> gamelist=new ArrayList<>();
	public ArrayList<String> msgList=new ArrayList<>();
	public ArrayList<String> msgvisibilityList=new ArrayList<>();


	Typeface custom_font;
	private DisplayImageOptions options;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(mView == null){
			mView = inflater.inflate(R.layout.profile, container, false);

		}else{
//			((ViewGroup) mView.getParent()).removeView(mView);
		}
		// Listview Data
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
initView();
		downloadGameList();


		return mView;
	}
	void initView()
	{
		initImageLoader(getActivity().getApplicationContext());
		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();

		gamelist.clear();
		msgList.clear();
		msgvisibilityList.clear();
		handler = new Handler();
		 custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/COMIC.TTF");

		lv=(ListView)mView.findViewById(R.id.listView1);
		msgLv=(ListView)mView.findViewById(R.id.listViewmsg);
		msgBtn = (ImageView)mView.findViewById(R.id.msgIv);
		closeBtn = (ImageView)mView.findViewById(R.id.close_btn);

msgLyt=(LinearLayout)mView.findViewById(R.id.msgListViewLyt);
		tx = (TextView)mView.findViewById(R.id.msgTxt);
		okBtn = (Button)mView.findViewById(R.id.okBtn);
		Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/COMIC.TTF");

		tx.setTypeface(custom_font);
		msgContentTxt = (TextView)mView.findViewById(R.id.msgcontentTxt);

		msgContentTxt.setTypeface(custom_font);
		SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		Boolean msg = sharedpreferences.getBoolean("MSG", false);
if(msg) {
	msgLyt.setVisibility(View.VISIBLE);
}else
{
	msgLyt.setVisibility(View.GONE);
}

		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseActivity.tabChange();
			}
		});
		msgBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				msgLyt.setVisibility(View.VISIBLE);
					downloadMassageList();

			}
		});
		closeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				msgLyt.setVisibility(View.GONE);
				readMassage();
				msgList.clear();
				msgvisibilityList.clear();
			}
		});
		TextView txt1=(TextView)mView.findViewById(R.id.responsiblegamingTxt);

		txt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), ResponsiblegamingActivity.class));
			}
		});
		txt1.setTypeface(custom_font);
	}
	public static void initImageLoader(Context context) {
// This configuration tuning is custom. You can tune every option, you may tune some of them,
// or you can create default configuration by
//  ImageLoaderConfiguration.createDefault(this);
// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
	@Override
	public void onComplete(final String result, int i) {
		mProgressDialog.cancel();
if(i==100) {
	handler.post(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			JSONObject obj = null;
			JSONArray array = null;
			try {
				obj = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				array = obj.getJSONArray("data");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (obj.getString("error").equals("false")) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject jobj2 = array.getJSONObject(i);

						if(jobj2.has("end_date")) {
							gamelist.add(new GameListModal(jobj2.getString("anroid_game_download_link"), jobj2.getString("competition_time_left"), jobj2.getString("score"), jobj2.getString("rank"), jobj2.getString("game_name"), jobj2.getString("game_image"), jobj2.getString("end_date")));
						}else
						{
							gamelist.add(new GameListModal(jobj2.getString("anroid_game_download_link"), jobj2.getString("competition_time_left"), jobj2.getString("score"), jobj2.getString("rank"), jobj2.getString("game_name"), jobj2.getString("game_image"), ""));

						}

					}
					AppLog.logPrint(gamelist.size() + "list");
					MyAdapter adapter = new MyAdapter(getActivity());
					lv.setAdapter(adapter);
					downloadMassageList();

				} else {
					DialogInterfacecustom.loginResponceDialog(getActivity(), obj.getString("message").toString(), "");


				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
}else if(i==200)
{
	handler.post(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			JSONObject obj = null;
			JSONArray array = null;
			try {
				obj = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				array = obj.getJSONArray("data");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (obj.getString("error").equals("false")) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject jobj2 = array.getJSONObject(i);
                        msgList.add(jobj2.getString("message"));

						msgvisibilityList.add(jobj2.getString("visibility"));

					}
					AppLog.logPrint(gamelist.size() + "list");
					MyAdapterMsg adapter = new MyAdapterMsg(getActivity());
					msgLv.setAdapter(adapter);
     TextView msgcontent=(TextView)mView.findViewById(R.id.msgcontentTxt);
					msgcontent.setText(msgList.get(0));
					msgcontent.setTypeface(custom_font);

				} else {
					DialogInterfacecustom.loginResponceDialog(getActivity(), obj.getString("message").toString(), "");


				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
}
	}


	public void downloadGameList() {
		mProgressDialog = ProgressDialog.show(getActivity(), null,
				"Please Wait....", true);
		mProgressDialog.setCancelable(false);

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<NameValuePair> data=new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("user_id",  AppUrl.user_id));
				new Web().requestPostStringData(AppUrl.usergameUrl, data, HomeFragment.this, 100);



			}
		}).start();
	}
	public void readMassage() {
		mProgressDialog = ProgressDialog.show(getActivity(), null,
				"Please Wait....", true);
		mProgressDialog.setCancelable(false);

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<NameValuePair> data=new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("user_id",  AppUrl.user_id));
				new Web().requestPostStringData("http://go4itapp.com.gridhosted.co.uk/api/api.php?method=set_msg_read", data, HomeFragment.this, 500);



			}
		}).start();
	}

	public void downloadMassageList() {
		msgList.clear();
		mProgressDialog = ProgressDialog.show(getActivity(), null,
				"Please Wait....", true);
		mProgressDialog.setCancelable(false);


		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
//					new Web().requestGet(AppUrl.massageUrl,HomeFragment.this,200);
				List<NameValuePair> data=new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("user_id",  AppUrl.user_id));
				new Web().requestPostStringData(AppUrl.massageUrl, data, HomeFragment.this, 200);



			}
		}).start();
	}

	public class MyAdapter extends BaseAdapter {
		public MyAdapter(Context context)

		{

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return gamelist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView( final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView tv1, tv2;
			Button btn1;
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getActivity());
			View row1 = inflater.inflate(R.layout.home_list_item, parent, false);
			TextView scoretxt=(TextView)row1.findViewById(R.id.scoreTxt);
			final TextView time=(TextView)row1.findViewById(R.id.timeTxt);
			TextView gamename=(TextView)row1.findViewById(R.id.gameNameTxt);
			TextView leftTime=(TextView)row1.findViewById(R.id.lefttimeTxt);
            row1.findViewById(R.id.playBtn).setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {

	//	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(gamelist.get(position).getPackageName())));

		String servicestring = Context.DOWNLOAD_SERVICE;
		DownloadManager downloadmanager;
		downloadmanager = (DownloadManager) getActivity().getSystemService(servicestring);

		Uri uri = Uri
				.parse(gamelist.get(position).getPackageName());
		DownloadManager.Request request = new DownloadManager.Request(uri);
		Long reference = downloadmanager.enqueue(request);
		Toast.makeText(getActivity(),"Downloading Start.....",Toast.LENGTH_LONG).show();
	}
});

			scoretxt.setText("Your score " + gamelist.get(position).getScore() + "      " + "Rank# " + gamelist.get(position).getRank());
			gamename.setText(gamelist.get(position).getGameName());
			scoretxt.setTypeface(custom_font);
			time.setTypeface(custom_font);

			gamename.setTypeface(custom_font);
			leftTime.setTypeface(custom_font);

//				time.setText(gamelist.get(position).getTimeLeft());

			if(!gamelist.get(position).getTimeLeft().equals("Currently there are no competitions running")) {
//				AppLog.logPrint("check"+gamelist.get(position).getTimeLeft());

				Date convertedDate = new Date();
				try {
					convertedDate = dateFormat.parse(gamelist.get(position).getEnddate());
					TimeZone tz = TimeZone.getDefault();
					SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					destFormat.setTimeZone(tz);
					destFormat.format(convertedDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				AppLog.logPrint("position 1");
				long timeInMilliseconds = convertedDate.getTime();

				new CountDownTimer(timeInMilliseconds, 1000) {

					public void onTick(long millisUntilFinished) {
						try {
							getDateDifference(gamelist.get(position).getEnddate());
							time.setText(time1);
						}catch (Exception e) {

						}
					}

					public void onFinish() {
						time.setText("done!");
					}
				}.start();

			}else
			{
				time.setText(gamelist.get(position).getTimeLeft());
			}


			ImageView gameIv=(ImageView)row1.findViewById(R.id.gameImageView);
			ImageLoader.getInstance()
					.displayImage(gamelist.get(position).getLogoUrl(), gameIv, options, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
						}
					});


			return row1;
		}

	}
	public void getDateDifference(String FutureDate /*,final TextView txt*/) {

		try {
			Date currentDate = dateFormat.parse(FutureDate);

			System.out.println(currentDate);

			Date oldDate = new Date();
			TimeZone tz = TimeZone.getDefault();
			SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			destFormat.setTimeZone(tz);
			destFormat.format(currentDate);
			destFormat.format(oldDate);

			long diff = currentDate.getTime() - oldDate.getTime();

			long days = diff / (24 * 60 * 60 * 1000);
			diff -= days * (24 * 60 * 60 * 1000);

			long hours = diff / (60 * 60 * 1000);
			diff -= hours * (60 * 60 * 1000);

			long minutes = diff / (60 * 1000);
			diff -= minutes * (60 * 1000);

			long seconds = diff / 1000;
			time1= days + " days " + hours + " hrs " + minutes + " min " + seconds + " sec ";

		} catch (Exception e) {
			e.printStackTrace();
		}



	}
	public class MyAdapterMsg extends BaseAdapter {
		public MyAdapterMsg(Context context
						 )

		{

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return msgList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView tv = new TextView(getActivity());
			tv.setText(msgList.get(position));
			tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
			tv.setTypeface(custom_font);
			tv.setPadding(5, 5, 5, 5);
			tv.setTextSize(20);
			tv.setTypeface(null, Typeface.NORMAL);


			if(msgvisibilityList.get(position).equals("0")) {
				tv.setTextSize(21);
				tv.setTypeface(null, Typeface.BOLD);			}
					return tv;
		}

	}
}
/*	final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.tabbar_selector.list_dialog);
		dialog.setTitle("Title...");

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText("Android custom dialog example!");
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.logo);
		dialog.show();
	*/


