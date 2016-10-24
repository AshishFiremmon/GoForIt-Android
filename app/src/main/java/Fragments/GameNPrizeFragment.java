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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moon.fire.goapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import Util.AppLog;
import Util.AppUrl;
import Util.CompetitionListModal;
import Util.DialogInterfacecustom;
import Util.PrizeAmountListModal;
import Util.TopScoreListModal;
import web.IResponse;
import web.Web;


public class GameNPrizeFragment extends Fragment implements IResponse {

	private View mView;
	ListView lv,lv1;
	LinearLayout lyt;
	RelativeLayout prizeLyt;
	public GameNPrizeFragment(){
		setRetainInstance(true);
	}
	ArrayList<String> list=new ArrayList<>();
	ProgressDialog mProgressDialog;
	private Handler handler;
	public ArrayList<CompetitionListModal> competition=new ArrayList<>();
	public ArrayList<TopScoreListModal> competitionTopscore=new ArrayList<>();
	public ArrayList<PrizeAmountListModal> competitionPrize=new ArrayList<>();
	ImageView topscoreGameIcon,PrizeGameIcon;
	TextView topscoreGameName,PrizeGameName;
	private DisplayImageOptions options;
	Typeface custom_font;
	TextView firstprize,secprize,thirdprize,fourthprize,fifthprize;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		competition.clear();
		competitionTopscore.clear();
		competitionPrize.clear();

		if(mView == null){
			mView = inflater.inflate(R.layout.game_n_prize, container, false);

		}else{
//			((ViewGroup) mView.getParent()).removeView(mView);
		}
		initView();
		handler = new Handler();
         topscoreGameIcon=(ImageView)mView.findViewById(R.id.topscoreGameIcon);
		PrizeGameIcon=(ImageView)mView.findViewById(R.id.gameIconPrize);
		topscoreGameName=(TextView)mView.findViewById(R.id.topscoreGameName);
		PrizeGameName=(TextView)mView.findViewById(R.id.prizeGamename);

		lv=(ListView)mView.findViewById(R.id.listView1);
		lv1=(ListView)mView.findViewById(R.id.listView2);

		lyt=(LinearLayout)mView.findViewById(R.id.scoreListLyt);
		prizeLyt=(RelativeLayout)mView.findViewById(R.id.prizeLyt);

		firstprize=(TextView)mView.findViewById(R.id.firstprize);
		secprize=(TextView)mView.findViewById(R.id.secprize);
		thirdprize=(TextView)mView.findViewById(R.id.thirdprize);
		fourthprize=(TextView)mView.findViewById(R.id.fourthprize);
		fifthprize=(TextView)mView.findViewById(R.id.fifthprize);
		downloadCompetiotionList();
		lv.setVisibility(View.VISIBLE);
		lyt.setVisibility(View.GONE);
		prizeLyt.setVisibility(View.GONE);
		mView.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lv.setVisibility(View.VISIBLE);
				lyt.setVisibility(View.GONE);

			}
		});
		mView.findViewById(R.id.close_bt_prize).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lv.setVisibility(View.VISIBLE);
				prizeLyt.setVisibility(View.GONE);

			}
		});
		return mView;
	}


void	initView()
	{


		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.game_icon)
				.showImageForEmptyUri(R.drawable.game_icon)
				.showImageOnFail(R.drawable.game_icon)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/COMIC.TTF");

	}
	public void downloadCompetiotionList() {
		mProgressDialog = ProgressDialog.show(getActivity(), null,
				"Please Wait....", true);
		mProgressDialog.setCancelable(false);

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
					new Web().requestGet(AppUrl.gameNprizeCompetitionUrl, GameNPrizeFragment.this, 100);



			}
		}).start();
	}
	public void downloadCompetionPrizeList(final int pos) {
		mProgressDialog = ProgressDialog.show(getActivity(), null,
				"Please Wait....", true);
		mProgressDialog.setCancelable(false);

//AppLog.logPrint(competition.get(pos).getCompId()+"....position");


		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<NameValuePair> data=new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("competition_id",  competition.get(pos).getCompId()));
				new Web().requestPostStringData(AppUrl.competitionPrizeUrl, data, GameNPrizeFragment.this, 200);



			}
		}).start();
	}

	public void downloadCompetionTopScoreList(final int pos) {
		competitionTopscore.clear();
		mProgressDialog = ProgressDialog.show(getActivity(), null,
				"Please Wait....", true);
		mProgressDialog.setCancelable(false);

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<NameValuePair> data=new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("competition_id",  competition.get(pos).getCompId()));
				new Web().requestPostStringData(AppUrl.competitionTopScoreUrl, data, GameNPrizeFragment.this, 300);



			}
		}).start();
	}
	@Override
	public void onComplete(final String result, int i) {
		mProgressDialog.cancel();
		if (i == 100) {
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
						array = obj.getJSONArray("competition_data");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						if (obj.getString("error").equals("false")) {
							for (int i = 0; i < array.length(); i++) {
								JSONObject jobj2 = array.getJSONObject(i);
								competition.add(new CompetitionListModal( jobj2.getString("competition_id"), jobj2.getString("end_date"), jobj2.getString("game_name"), jobj2.getString("game_image"),jobj2.getString("anroid_game_download_link")));


							}
							AppLog.logPrint(competition.size() + "list");
							MyAdapter adapter = new MyAdapter(getActivity());
							lv.setAdapter(adapter);


						} else {
							DialogInterfacecustom.loginResponceDialog(getActivity(), obj.getString("message").toString(), "");


						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}else 	if (i == 200) {
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
						array = obj.getJSONArray("competition_prize_data");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						if (obj.getString("error").equals("false")) {
							for (int i = 0; i < array.length(); i++) {
								JSONObject jobj2 = array.getJSONObject(i);
//								competitionPrize.add(new PrizeAmountListModal( jobj2.getString("first_prize"), jobj2.getString("second_prize"), jobj2.getString("third_prize"), jobj2.getString("fourth_prize"),jobj2.getString("fifth_prize")));

								firstprize.setText("£"+jobj2.getString("first_prize"));
								secprize.setText("£"+jobj2.getString("second_prize"));
								thirdprize.setText("£"+jobj2.getString("third_prize"));
								fourthprize.setText("£"+jobj2.getString("fourth_prize"));
								fifthprize.setText("£"+jobj2.getString("fifth_prize"));
								Log.i("hello",jobj2.getString("fifth_prize")+"checkprize");
							}

						} else {
							DialogInterfacecustom.loginResponceDialog(getActivity(), obj.getString("message").toString(), "");


						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}else 	if (i == 300) {
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
						array = obj.getJSONArray("top_user_data");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						if (obj.getString("error").equals("false")) {
							for (int i = 0; i < array.length(); i++) {
								JSONObject jobj2 = array.getJSONObject(i);
								competitionTopscore.add(new TopScoreListModal( jobj2.getString("avatar_name"), jobj2.getString("rank"), jobj2.getString("score"), jobj2.getString("avatar")));


							}
							MyScoreAdapter adapter=new MyScoreAdapter(getActivity());
							lv1.setAdapter(adapter);


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

	public class MyAdapter extends BaseAdapter {
		public MyAdapter(Context context
						)

		{

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return competition.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView tv1, tv2;
			Button btn1;
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getActivity());
			View row1 = inflater.inflate(R.layout.list_item, parent, false);
			ImageView gameIcon=(ImageView)row1.findViewById(R.id.gameIcon);
			TextView gamename=(TextView)row1.findViewById(R.id.gameName);


			TextView endTime=(TextView)row1.findViewById(R.id.endTimeTxt);
			gamename.setTypeface(custom_font);
			endTime.setTypeface(custom_font);
			gamename.setText(competition.get(position).getGameName());
//			AppLog.logPrint(convertDate("2016-09-25 00:00:00")+"endTime");
			endTime.setText(getCurrentTimezoneOffset(competition.get(position).getTimeLeft()));

//			endTime.setText(competition.get(position).getTimeLeft());

			row1.findViewById(R.id.topscoreIv).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					lv.setVisibility(View.GONE);
					lyt.setVisibility(View.VISIBLE);
					topscoreGameName.setText(competition.get(position).getGameName());
					ImageLoader.getInstance()
							.displayImage(competition.get(position).getLogoUrl(), topscoreGameIcon, options, new SimpleImageLoadingListener() {
								@Override
								public void onLoadingStarted(String imageUri, View view) {
								}
							});

					downloadCompetionTopScoreList(position);
				}
			});
			row1.findViewById(R.id.prizeIv).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					lv.setVisibility(View.GONE);

					prizeLyt.setVisibility(View.VISIBLE);
					downloadCompetionPrizeList(position);
					PrizeGameName.setText(competition.get(position).getGameName());

					PrizeGameName.setTypeface(custom_font);

					ImageLoader.getInstance()
							.displayImage(competition.get(position).getLogoUrl(), PrizeGameIcon, options, new SimpleImageLoadingListener() {
								@Override
								public void onLoadingStarted(String imageUri, View view) {
								}
							});


				}
			});
			ImageLoader.getInstance()
					.displayImage(competition.get(position).getLogoUrl(), gameIcon, options, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
						}
					});
			row1.findViewById(R.id.playstoreIv).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(competition.get(position).getDownloadLink())));

					String servicestring = Context.DOWNLOAD_SERVICE;
					DownloadManager downloadmanager;
					downloadmanager = (DownloadManager) getActivity().getSystemService(servicestring);

					Uri uri = Uri
							.parse(competition.get(position).getDownloadLink());
					DownloadManager.Request request = new DownloadManager.Request(uri);
					Long reference = downloadmanager.enqueue(request);
					Toast.makeText(getActivity(),"Downloading Start .....",Toast.LENGTH_LONG).show();

				}
			});
			return row1;
		}

	}
	public String getCurrentTimezoneOffset(String dateStr) {
		String datetime="",result="";
		SimpleDateFormat	dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			Date date = dateFormat.parse(dateStr);
			System.out.println("Date    "+date);
			datetime = dateFormat.format(date);
			TimeZone tz = TimeZone.getDefault();
			SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			destFormat.setTimeZone(tz);

			 result = destFormat.format(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TimeZone tz = TimeZone.getDefault();
		Calendar cal = GregorianCalendar.getInstance(tz);
		int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
		String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
		offset = "GMT"+(offsetInMillis >= 0 ? "+" : "-") + offset;
		return result+" "+offset;
	}


	private String convertDate(String date) {
		try {
		/*	SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
			Date d = format.parse(date);
	*/		SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			serverFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			AppLog.logPrint(date);
			return serverFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public class MyScoreAdapter extends BaseAdapter {
		public MyScoreAdapter(Context context
						 )

		{

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return competitionTopscore.size();
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
			TextView tv1, tv2;
			Button btn1;
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getActivity());
			View row1 = inflater.inflate(R.layout.score_list, parent, false);
			if(position%2==0)
			{
				row1.findViewById(R.id.msgLyt).setBackgroundColor(Color.WHITE);

			}else
			{
				row1.findViewById(R.id.ranktxt).setBackgroundResource(R.drawable.rank_base_blue);

			}

ImageView avtarlogo=(ImageView)row1.findViewById(R.id.avtarLogo);
			ImageLoader.getInstance()
					.displayImage(competitionTopscore.get(position).getAvtarUrl(), avtarlogo, options, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
						}
					});


	TextView rank=(TextView)row1.findViewById(R.id.ranktxt);
			TextView score=(TextView)row1.findViewById(R.id.scoreTxt);
			TextView avtarname=(TextView)row1.findViewById(R.id.avtar_name);
			avtarname.setText(competitionTopscore.get(position).getAvtarname());

			score.setText(competitionTopscore.get(position).getScore());

			rank.setText("#"+competitionTopscore.get(position).getRank());
			avtarname.setTypeface(custom_font);
			score.setTypeface(custom_font);
			rank.setTypeface(custom_font);

			return row1;
		}

	}

}
