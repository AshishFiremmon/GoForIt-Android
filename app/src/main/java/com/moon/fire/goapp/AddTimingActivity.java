package com.moon.fire.goapp;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddTimingActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    String timetxt;
    TextView txt,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11,txt12,txt13,txt14;
    String mon="Mon";
    String tues="Tues";
    String wed="Wed";
    String thur="Thur";
    String fri="Fri";
    String sat="Sat";
    String sun="Sun";
     String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_time_screen);
        txt1=(TextView)findViewById(R.id.timetxt1);
        txt2=(TextView)findViewById(R.id.timetxt2);
        txt3=(TextView)findViewById(R.id.timetxt3);
        txt4=(TextView)findViewById(R.id.timetxt4);
        txt5=(TextView)findViewById(R.id.timetxt5);
        txt6=(TextView)findViewById(R.id.timetxt6);
        txt7=(TextView)findViewById(R.id.timetxt7);
        txt8=(TextView)findViewById(R.id.timetxt8);
        txt9=(TextView)findViewById(R.id.timetxt9);
        txt10=(TextView)findViewById(R.id.timetxt10);
        txt11=(TextView)findViewById(R.id.timetxt11);
        txt12=(TextView)findViewById(R.id.timetxt12);
        txt13=(TextView)findViewById(R.id.timetxt13);
        txt14=(TextView)findViewById(R.id.timetxt14);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTimingActivity.this,mon,Toast.LENGTH_LONG).show();
            }
        });
*/
    }


  public   void timeSelect( View  v)
    {
      str="";



        txt=(TextView)v;
        timetxt="";
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTimingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        time.setText(selectedHour + ":" + selectedMinute);


                timetxt=selectedHour + ":" + selectedMinute;

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTimingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        timetxt+=" - "+selectedHour + ":" + selectedMinute;

                        txt.setText(timetxt);
                        str+=timetxt;
                      /*  if(txt.getId()==R.id.timetxt1 ||txt.getId()==R.id.timetxt2 )
                        {
                         mon+="\n"+timetxt;
                        }*/

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("To");
                mTimePicker.show();
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("From");
        mTimePicker.show();

    }
}