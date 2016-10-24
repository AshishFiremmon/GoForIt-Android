package web;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetConnectio {

	static Context _context;
	

	public CheckInternetConnectio(Context applicationContext) {
		// TODO Auto-generated constructor stub
		_context=applicationContext;

	}

	public	boolean  checkInterntConnection()
	{
		ConnectivityManager cm = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			return false;
		} else
			return true;
	}
}
