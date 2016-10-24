package com.moon.fire.goapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;
import java.util.Random;

import Util.AppLog;

/**
 * Created by Belal on 3/18/2016.
 */
//Class extending service as it is a service that will run in background
public class NotificationListener extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //When the service is started
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Opening sharedpreferences
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);

        //Getting the firebase id from sharedpreferences
      final  String id = sharedPreferences.getString(Constants.UNIQUE_ID, null);

        //Creating a firebase object
        Firebase firebase = new Firebase(Constants.FIREBASE_APP + id);

        //Adding a valueevent listener to firebase
        //this will help us to  track the value changes on firebase
        firebase.addValueEventListener(new ValueEventListener() {

            //This method is called whenever we change the value in firebase
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Getting the value from firebase
                //We stored none as a initial value
                String msg = snapshot.child("msg").getValue().toString();

                AppLog.logPrint("notification"+msg);
             /*   AppLog.logPrint("notification..."+snapshot.child("msg").getValue());
                AppLog.logPrint("notification..."+snapshot.child("msg").getKey());
*/

                //So if the value is none we will not create any notification
                if (msg.equals("none"))
                    return;

                //If the value is anything other than none that means a notification has arrived
                //calling the method to show notification
                //String msg is containing the msg that has to be shown with the notification

                String[] output = msg.split(",");
                String last=output[0];
                String[] output2 = last.split("=");
                output2[1].replace("}"," ");

                showNotification(output2[1].replace("}"," "));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("The read failed: ", firebaseError.getMessage());
            }
        });

        return START_STICKY;
    }


    private void showNotification(String msg){
        //Creating a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("NOTIFICATION","notify");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//        builder.setContentTitle("Firebase Push Notification");
        builder.setContentText(msg);
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Random r = new Random();
        int i1 = r.nextInt(100);
        notificationManager.notify(i1, builder.build());
        WellcomeActivity.sharedpreferences = getSharedPreferences(WellcomeActivity.MyPREFERENCES, Context.MODE_PRIVATE);


        int notify = WellcomeActivity.sharedpreferences.getInt("NOTI", 0);
        SharedPreferences.Editor editor = WellcomeActivity.sharedpreferences.edit();
        editor.putInt("NOTI", notify + 1).commit();
        setBadge(getApplicationContext(),notify+1);
    }

    public  void setBadge(Context context, int count) {

        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }
}
