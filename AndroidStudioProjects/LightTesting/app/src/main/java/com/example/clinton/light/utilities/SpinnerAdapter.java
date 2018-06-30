package com.example.clinton.light.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.clinton.light.R;

public class SpinnerAdapter  extends ArrayAdapter<String>  {
    Context context;
    String[] meanings;
    String[] types;

        public SpinnerAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            meanings = context.getResources().getStringArray(R.array.spinner_explain);
            types = context.getResources().getStringArray(R.array.spinner_other);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.spinner_item, parent, false);
            TextView label=(TextView)row.findViewById(R.id.wordName);
            label.setText(types[position]);

            TextView sub=(TextView)row.findViewById(R.id.word_explain);
            sub.setText(meanings[position]);

            return row;
        }
    }
