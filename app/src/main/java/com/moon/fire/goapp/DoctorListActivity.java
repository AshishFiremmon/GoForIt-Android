package com.moon.fire.goapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity {
    ListView lv;
ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        for (int i = 0; i <6 ; i++) {
            list.add("hello");
        }
        lv = (ListView) findViewById(R.id.listView1);
        MyAdapter adapter=new MyAdapter(this,list);
        lv.setAdapter(adapter);
    }

    public class MyAdapter extends BaseAdapter {
        public MyAdapter(Context context,
                         ArrayList<String> save)

        {

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 5;
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
           /* TextView tv1, tv2;
            Button btn1;
            LayoutInflater inflater = getLayoutInflater();
            View row1 = inflater.inflate(R.layout.row, parent, false);
            tv1 = (TextView) row1.findViewById(R.id.textView1);
            tv2 = (TextView) row1.findViewById(R.id.textView2);
            btn1 = (Button) row1.findViewById(R.id.button1);

            tv1.setText("hello");
            btn1.setText("Jewelery #");
            tv2.setText("Done");*/
            return null;
        }

    }
}
