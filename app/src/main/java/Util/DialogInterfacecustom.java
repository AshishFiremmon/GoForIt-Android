package Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DialogInterfacecustom {
	
	public static void datePickerDialog(Activity activity,     final EditText et)
 {
		int fromYear, fromMonth, fromDay;
		Calendar cal = Calendar.getInstance();
		fromYear = cal.get(Calendar.YEAR);
		fromMonth = cal.get(Calendar.MONTH);
		fromDay = cal.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dpd = new DatePickerDialog(activity,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						et.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
					}
				}, fromYear, fromMonth, fromDay);
		dpd.setTitle("Select From date");
		dpd.show();
	}
	
	public static void loginResponceDialog(Activity activity, final String msg,final String errorcode)
 {

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	
		// Setting message manually and performing action on button click
		builder.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("Ok", new OnClickListener() {
						@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						
					}

					
				});

		// Creating dialog box
		AlertDialog alert = builder.create();
		// Setting the title manually
//		alert.setTitle("Error:"+ errorcode);
		alert.show();
	}
	
	public static void termNconditionDialog(Activity activity)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(activity); 
		alert.setTitle("Title here");

		WebView wv = new WebView(activity);
		wv.loadUrl("http:\\www.google.com");
		wv.setWebViewClient(new WebViewClient() {
		    @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);

		        return true;
		    }
		});

		alert.setView(wv);
		alert.setNegativeButton("Close", new OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int id) {
		        dialog.dismiss();
		    }
		});
		alert.show();
	}
	
	public static void validationDialog(Activity activity, final String msg,final String errorcode)
	 {

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		
			// Setting message manually and performing action on button click
			builder.setMessage(msg)
					.setCancelable(false)
					.setPositiveButton("Ok", new OnClickListener() {
							@Override
						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							
						}

						
					});

			// Creating dialog box
			AlertDialog alert = builder.create();
			// Setting the title manually
//			alert.setTitle("Error:"+ errorcode);
			alert.show();
		}
}
