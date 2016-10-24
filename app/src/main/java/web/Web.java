package web;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import Util.AppLog;

public class Web {

	public void requestGet(String url, IResponse r, int i) {
		HttpClient client = new DefaultHttpClient();

		HttpGet get = new HttpGet(url);

		try {
			HttpResponse response = client.execute(get);
			InputStream in = response.getEntity().getContent();
			String result = getString(in);
			r.onComplete(result, i);
			AppLog.logPrint(result);
			Log.d("tag", result);
			// parser(result);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void requestPostStringData(String url, List<NameValuePair> data,
			IResponse r, int i) {
			
		HttpClient client = new DefaultHttpClient();
		

		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response = client.execute(post);
			InputStream in = response.getEntity().getContent();
			String result = getString(in);
			Log.d("tag", result);
			AppLog.logPrint(result);
			r.onComplete(result, i);
			

			// parser(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}

	
	private static String getString(InputStream in) {
		int c = -1;
		StringBuffer r = new StringBuffer();

		try {
			while ((c = in.read()) != -1)
				r.append((char) c);

			return r.toString();
		} catch (Exception e) {
		}

		return null;
	}

		

}
