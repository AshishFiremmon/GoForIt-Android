package com.moon.fire.goapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Util.AppLog;
import Util.AppUrl;
import Util.DialogInterfacecustom;
import Util.Validation;
import web.CheckInternetConnectio;
import web.IResponse;
import web.Web;

public class LoginActivity extends AppCompatActivity implements IResponse {
EditText avtarEt,emailEt,confirmEmailEt;
    ProgressDialog mProgressDialog;
    private Handler handler;
    // GPSTracker class
    GPSTracker gps;
    String country;
    Typeface custom_font;
    Spinner mySpinner;
    String data[]={"Select Country of residence","United Kingdom","Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates",  "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_login);


        custom_font = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");
         mySpinner = (Spinner) findViewById(R.id.countryspinner);
        MyArrayAdapter ma = new MyArrayAdapter(this);
        mySpinner.setAdapter(ma);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Change the selected item's text color
                if( ((TextView) view).getText().toString().equals(data[0]))
                {

                }else {
                    ((TextView) view).setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        TextView discriptionTxt=(TextView)findViewById(R.id.discriptionTxt);
        TextView TopTxt=(TextView)findViewById(R.id.topTxt);
        discriptionTxt.setTypeface(custom_font);
        TopTxt.setTypeface(custom_font);

        handler = new Handler();
        avtarEt=(EditText)findViewById(R.id.avtarEt);
        emailEt=(EditText)findViewById(R.id.emailEt);
        confirmEmailEt=(EditText)findViewById(R.id.confirmemailEt);
        avtarEt.setTypeface(custom_font);
        emailEt.setTypeface(custom_font);
        confirmEmailEt.setTypeface(custom_font);



         findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("hello", Validation.isEmailAddress(emailEt, true) + "");
                CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                        LoginActivity.this);

                if (_checkInternetConnection.checkInterntConnection()) {

                    if (emailEt.getText().toString().equals(confirmEmailEt.getText().toString())) {
                        if (avtarEt.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(LoginActivity.this, "Enter Avtar Name", Toast.LENGTH_LONG).show();
                        }
//                if(emailEt.getText().toString().equalsIgnoreCase("") )

                        else if (Validation.isEmailAddress(emailEt, true)) {

                            if (!data[mySpinner.getSelectedItemPosition()].equals("Select your Country of residence")) {


                                country = data[mySpinner.getSelectedItemPosition()];
//                            AppLog.logPrint(getCountryName(LoginActivity.this,latitude,longitude)+"ghghjg"); ;
                                // \n is for new line
//                            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                                registerDevice();
                                mProgressDialog = ProgressDialog.show(LoginActivity.this, null,
                                        "Please Wait....", true);
                                mProgressDialog.setCancelable(false);

                            } else {
                                // can't get location
                                // GPS or Network is not enabled
                                // Ask user to enable GPS/network in settings
                                Toast.makeText(LoginActivity.this, "Select Country...", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Enter Email Id", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        emailEt.setText("");
                        confirmEmailEt.setText("");
                        Toast.makeText(LoginActivity.this, "Email Id dose not match", Toast.LENGTH_LONG).show();

                    }
                }else
                {
                    Toast.makeText(LoginActivity.this, "Check your Internet Connection...", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    public void registration(final String firebaseId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // check if GPS enabled

                List<NameValuePair> data=new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("avatar_name", avtarEt.getText().toString()));
                data.add(new BasicNameValuePair("email", emailEt.getText().toString()));
                data.add(new BasicNameValuePair("country",country));
                data.add(new BasicNameValuePair("device_id",firebaseId));
                data.add(new BasicNameValuePair("device_type","android"));


                new Web().requestPostStringData(AppUrl.loginUrl, data, LoginActivity.this, 100);



            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(LoginActivity.this, WellcomeActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onComplete(final String result, int i) {
        mProgressDialog.cancel();

        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    if (obj.getString("error").equals("false")) {
                        Toast.makeText(LoginActivity.this, "Successfully Login....", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this, BaseActivity.class);
                        startActivity(i);
                        SharedPreferences.Editor editor = WellcomeActivity.sharedpreferences.edit();
                        editor.putBoolean("LOGIN", true).commit();
                        editor.putString("AVTAR NAME", avtarEt.getText().toString()+"\n"+emailEt.getText().toString()).commit();
                        JSONObject jObj = obj.getJSONObject("data");
                        AppLog.logPrint(jObj.getString("user_id"));
                        editor.putString("USER_ID", jObj.getString("user_id")).commit();
                        editor.commit();
                        AppUrl.user_id = jObj.getString("user_id");

                        startService(new Intent(getBaseContext(), NotificationListener.class));
                        finish();
                    } else {
                        DialogInterfacecustom.loginResponceDialog(LoginActivity.this, obj.getString("message").toString(), "");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
    public static String getCountryName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            Address result;

            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getCountryName();
            }
//            return null;
        } catch (IOException ignored) {
            //do something
        }
return null;
    }
    private void registerDevice() {
        //Creating a firebase object
        Firebase firebase = new Firebase(Constants.FIREBASE_APP);

        //Pushing a new element to firebase it will automatically create a unique id
        Firebase newFirebase = firebase.push();

        //Creating a map to store name value pair
        Map<String, String> val = new HashMap<>();

        //pushing msg = none in the map
        val.put("msg", "none");

        //saving the map to firebase
        newFirebase.setValue(val);

        //Getting the unique id generated at firebase
        String uniqueId = newFirebase.getKey();

        Log.i("hello","unique"+ uniqueId);
        //Finally we need to implement a method to store this unique id to our server
//        sendIdToServer(uniqueId);
        //Opening shared preference
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);

        //Opening the shared preferences editor to save values
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Storing the unique id
        editor.putString(Constants.UNIQUE_ID, uniqueId);

        //Applying the changes on sharedpreferences
        editor.apply();

        registration(uniqueId);
    }
    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyArrayAdapter(Context con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.test, null);
                holder = new ListContent();

                holder.name = (TextView) v.findViewById(R.id.textView1);
                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }

            holder.name.setTypeface(custom_font);
            holder.name.setPadding(4, 10, 0, 0);

            holder.name.setText("" + data[position]);

            return v;
        }

    }

    static class ListContent {

        TextView name;
//        View seperater;

    }


}
