package com.moon.fire.goapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by firemoonpc_11 on 09-09-2016.
 */
 class SpinnerAdapter extends ArrayAdapter {
    Typeface custom_font;
    public SpinnerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
         custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/COMIC.TTF");

    }

    public TextView getView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        v.setTypeface(custom_font);
        return v;
    }

    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        v.setTypeface(custom_font);
        return v;
    }

}