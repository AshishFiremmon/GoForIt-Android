package com.moon.fire.goapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;
import java.util.Random;

import Util.AppLog;
import Util.AppUrl;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WellcomeActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

   */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_wellcome);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean yourLogin = sharedpreferences.getBoolean("LOGIN", false);
        AppUrl.user_id=sharedpreferences.getString("USER_ID", "");
        Log.i("hello", yourLogin + "");
        SharedPreferences.Editor editor = WellcomeActivity.sharedpreferences.edit();
        editor.putInt("NOTI", 0).commit();

        if(yourLogin)
        {
            Intent intent = new Intent(WellcomeActivity.this, BaseActivity.class);
            startActivity(intent);
            finish();

        }

      /*  AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();

        for(int i=0;i<list.length;i++)
        {
            Log.i("hello",list[i]+"");
        }
*/

findViewById(R.id.goBtn).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
              Intent i=new Intent(WellcomeActivity.this,LoginActivity.class);
        startActivity(i);
        finish();


//        showNotification("fghdfj");
    }
});
       /* findViewById(R.id.rootwellcome).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i=new Intent(WellcomeActivity.this,LoginActivity.class);
                startActivity(i);
                return false;
            }
        });*/
    }

    private void showNotification(String msg){
        //Creating a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentText(msg);
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Random r = new Random();
        int i1 = r.nextInt(80 - 65) + 65;
        notificationManager.notify(i1, builder.build());

        WellcomeActivity.sharedpreferences = getSharedPreferences(WellcomeActivity.MyPREFERENCES, Context.MODE_PRIVATE);


        int notify = WellcomeActivity.sharedpreferences.getInt("NOTI", 0);
        SharedPreferences.Editor editor = WellcomeActivity.sharedpreferences.edit();
        editor.putInt("NOTI", notify + 1).commit();
        setBadge(getApplicationContext(),notify+1);
    }

    public  void setBadge(Context context, int count) {
        AppLog.logPrint(count+"");
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

/* facebook,massage,terms n condition,country*/