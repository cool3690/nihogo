package com.example.aki2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class JsansAdapter extends ArrayAdapter<Jsan> {

    Context context;
    private ArrayList<Jsan> jsans;

    public JsansAdapter(Context context, int textViewResourceId, ArrayList<Jsan> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.jsans = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.jsant, null);
        }
        Jsan o = jsans.get(position);
        if (o != null) {
            TextView less = (TextView) v.findViewById(R.id.less);

            less.setText(String.valueOf(o.getJsan()));

        }
        return v;
    }
}
