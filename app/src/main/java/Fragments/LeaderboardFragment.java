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

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.moon.fire.goapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Util.AppLog;
import Util.AppUrl;
import Util.DialogInterfacecustom;
import Util.LeaderboardGameListModal;
import Util.LeaderboardListModal;
import web.IResponse;
import web.Web;


public class LeaderboardFragment extends Fragment implements IResponse{

    private View mView;
    ListView lv,countryList;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<LeaderboardGameListModal> gamelist=new ArrayList<>();
    ArrayList<LeaderboardListModal> scorelist=new ArrayList<>();
    ArrayList<String> countrylist=new ArrayList<>();

    ListView listView;
    ProgressDialog mProgressDialog;
    private Handler handler;
    private DisplayImageOptions options;
    Typeface custom_font;
    int gameposition;
    boolean country=false;
    TextView scoreIV;
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
                        array = obj.getJSONArray("game_list");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        if (obj.getString("error").equals("false")) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jobj2 = array.getJSONObject(i);
                                gamelist.add(new LeaderboardGameListModal(jobj2.getString("bundle_id"), jobj2.getString("game_name"), jobj2.getString("game_image"), jobj2.getString("is_country_split")));

                            //    gamelist.add(new LeaderboardGameListModal(jobj2.getString("bundle_id"), jobj2.getString("game_name"), jobj2.getString("game_image"), "no"));

                            }
                            AppLog.logPrint(gamelist.size() + "list");
                            MyAdapter adapter=new MyAdapter(getActivity());
                            gridViewv.setAdapter(adapter);


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
        if (i == 200) {
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
                                scorelist.add(new LeaderboardListModal(jobj2.getString("avatar_name"), jobj2.getString("rank"), jobj2.getString("score"), jobj2.getString("avatar")));


                            }
                            AppLog.logPrint(gamelist.size() + "list");
                            MyScoreAdapter adapterScore = new MyScoreAdapter(getActivity());
                            listView.setAdapter(adapterScore);


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
        if (i == 300) {
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
                        array = obj.getJSONArray("country_data");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        if (obj.getString("error").equals("false")) {
                            countrylist.add("Rest of The world");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jobj2 = array.getJSONObject(i);
                                if(!countrylist.contains(jobj2.getString("country"))) {
                                    countrylist.add(jobj2.getString("country"));
                                }


                            }
                            AppLog.logPrint(gamelist.size() + "list");
                            CountryAdapter adapter=new CountryAdapter(getActivity());
                            countryList.setAdapter(adapter);

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

    GridView gridViewv;
    LinearLayout lyt,lytCountry;
    public LeaderboardFragment() {
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.leaderboard, container, false);

        } else {
//			((ViewGroup) mView.getParent()).removeView(mView);
        }
        custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/COMIC.TTF");
/*TextView txt=(TextView)mView.findViewById(R.id.txtworld);

        txt.setTypeface(custom_font);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.game_icon)
                .showImageForEmptyUri(R.drawable.game_icon)
                .showImageOnFail(R.drawable.game_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        gridViewv=(GridView)mView.findViewById(R.id.listView1);
        listView  =(ListView)mView.findViewById(R.id.listViewScore);
        countryList  =(ListView)mView.findViewById(R.id.listCountry);
        scoreIV  =(TextView)mView.findViewById(R.id.topscoreIV);
        scoreIV.setTypeface(custom_font);
        scoreIV.setText("Global Top 50 Scores");
        handler=new Handler();
        lyt=(LinearLayout)mView.findViewById(R.id.scoreListLyt);
        lytCountry=(LinearLayout)mView.findViewById(R.id.countryListLyt);

        lytCountry.setVisibility(View.GONE);
        lyt.setVisibility(View.GONE);
        gridViewv.setVisibility(View.VISIBLE);

        gridViewv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gameposition = position;
                if (gamelist.get(position).getIs_country_split().equalsIgnoreCase("yes")) {
                    countrylist.clear();
                    downloadCountryList(position);
                    lytCountry.setVisibility(View.VISIBLE);
                    gridViewv.setVisibility(View.GONE);
                } else {
                    scorelist.clear();
                    downloadCompetionTop50ScoreList(position);
                    lyt.setVisibility(View.VISIBLE);
                    gridViewv.setVisibility(View.GONE);
                }
            }
        });


        countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                lytCountry.setVisibility(View.GONE);
                lyt.setVisibility(View.VISIBLE);
                gridViewv.setVisibility(View.GONE);
            }
        });
        mView.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gamelist.get(gameposition).getIs_country_split().equalsIgnoreCase("yes")) {
                    lytCountry.setVisibility(View.VISIBLE);
                }else
                {
                    gridViewv.setVisibility(View.VISIBLE);
                }
                lyt.setVisibility(View.GONE);
                country=false;

            }
        });
        mView.findViewById(R.id.country_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridViewv.setVisibility(View.VISIBLE);
                lytCountry.setVisibility(View.GONE);

            }
        });
        gamelist.clear();
        downloadGameList();
        return mView;
    }
    public void downloadGameList() {
        mProgressDialog = ProgressDialog.show(getActivity(), null,
                "Please Wait....", true);
        mProgressDialog.setCancelable(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                new Web().requestGet(AppUrl.gameListUrl, LeaderboardFragment.this, 100);

            }
        }).start();
    }

    public void downloadCompetionTop50ScoreList(final int pos) {
        mProgressDialog = ProgressDialog.show(getActivity(), null,
                "Please Wait....", true);
        mProgressDialog.setCancelable(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                    List<NameValuePair> data = new ArrayList<NameValuePair>();
                    data.add(new BasicNameValuePair("bundle_id", gamelist.get(pos).getBundle_id()));
                    new Web().requestPostStringData(AppUrl.leaderboard50Url, data, LeaderboardFragment.this, 200);

                }


        }).start();
    }
    public void restofworldScoreList(final int pos) {
        mProgressDialog = ProgressDialog.show(getActivity(), null,
                "Please Wait....", true);
        mProgressDialog.setCancelable(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("bundle_id", gamelist.get(pos).getBundle_id()));
                new Web().requestPostStringData(AppUrl.restofworldUrl, data, LeaderboardFragment.this, 200);

            }


        }).start();
    }


    public void downloadCountryList(final int pos) {
        mProgressDialog = ProgressDialog.show(getActivity(), null,
                "Please Wait....", true);
        mProgressDialog.setCancelable(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("bundle_id",  gamelist.get(pos).getBundle_id()));
                new Web().requestPostStringData(AppUrl.countryListUrl, data, LeaderboardFragment.this, 300);
            }
        }).start();
    }

    public void countrywiseTopScoreList(final int pos) {
        mProgressDialog = ProgressDialog.show(getActivity(), null,
                "Please Wait....", true);
        mProgressDialog.setCancelable(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("bundle_id",  gamelist.get(gameposition).getBundle_id()));
                data.add(new BasicNameValuePair("country",  countrylist.get(pos)));
                new Web().requestPostStringData(AppUrl.countrywiseTopScoreUrl, data, LeaderboardFragment.this, 200);



            }
        }).start();
    }
    public class MyAdapter extends BaseAdapter {
        public MyAdapter(Context context
                        )
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
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView tv1, tv2;
            Button btn1;
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getActivity());
            View row1 = inflater.inflate(R.layout.grid_item, parent, false);
            ImageView gameImg=(ImageView)row1.findViewById(R.id.gameIcon);
            TextView gameName=(TextView)row1.findViewById(R.id.gameName);
            gameName.setTypeface(custom_font);
            gameName.setText(gamelist.get(position).getGame_name());
            ImageLoader.getInstance()
                    .displayImage(gamelist.get(position).getGame_image(), gameImg, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                        }
                    });
            return row1;
        }

    }
    public class MyScoreAdapter extends BaseAdapter {
        public MyScoreAdapter(Context context)

        {

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return scorelist.size();
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
            View row1 = inflater.inflate(R.layout.leaderboard_list, parent, false);
            if(position%2==0)
            {
                row1.findViewById(R.id.msgLyt).setBackgroundColor(Color.WHITE);

            }else
            {
//                row1.findViewById(R.id.ranktxt).setBackgroundResource(R.drawable.rank_base_blue);

            }

            ImageView avtarlogo=(ImageView)row1.findViewById(R.id.avtarLogo);
			ImageLoader.getInstance()
					.displayImage(scorelist.get(position).getAvtarUrl(), avtarlogo, options, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
						}
					});


            TextView rank=(TextView)row1.findViewById(R.id.ranktxt);
            TextView score=(TextView)row1.findViewById(R.id.scoreTxt);
            TextView avtarname=(TextView)row1.findViewById(R.id.avtar_name);
            avtarname.setText(scorelist.get(position).getAvtarname());
            score.setText("Scores "+scorelist.get(position).getScore());
            rank.setText("Position "+scorelist.get(position).getRank());
            avtarname.setTypeface(custom_font);
            score.setTypeface(custom_font);
            rank.setTypeface(custom_font);
            return row1;
        }

    }



    public class CountryAdapter extends BaseAdapter {
        public CountryAdapter(Context context
        )
        {

        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return countrylist.size();
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
        public View getView(final  int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView tv1, tv2;
            Button btn1;
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getActivity());
            View row1 = inflater.inflate(R.layout.leaderboard_list_country, parent, false);
           final TextView countryTxt=(TextView)row1.findViewById(R.id.txtCountry);
          countryTxt.setText(countrylist.get(position)+" Top 50");

            countryTxt.setTypeface(custom_font);
            if(countryTxt.getText().equals("Rest of The world Top 50"))
            {
                row1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scorelist.clear();
                        restofworldScoreList(gameposition);
                        lyt.setVisibility(View.VISIBLE);
                        lytCountry.setVisibility(View.GONE);
                        gridViewv.setVisibility(View.GONE);
                        scoreIV.setText(countryTxt.getText()+" Scores");

                    }
                });
            }else {
                row1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scorelist.clear();
                        countrywiseTopScoreList(position);
                        country=true;
                        lyt.setVisibility(View.VISIBLE);
                        lytCountry.setVisibility(View.GONE);
                        gridViewv.setVisibility(View.GONE);
                        scoreIV.setText(countryTxt.getText()+" Scores");
                    }
                });
            }
            return row1;
        }
    }
}
