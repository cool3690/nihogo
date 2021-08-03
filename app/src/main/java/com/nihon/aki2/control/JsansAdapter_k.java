package com.nihon.aki2.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nihon.aki2.R;

import java.util.ArrayList;

public class JsansAdapter_k extends ArrayAdapter<Jsan_k> {

    Context context;
    private ArrayList<Jsan_k> jsans;

    public JsansAdapter_k(Context context, int textViewResourceId, ArrayList<Jsan_k> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.jsans = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.jsant_k, null);
        }
        Jsan_k o = jsans.get(position);
        if (o != null) {
            TextView less = (TextView) v.findViewById(R.id.less);

            less.setText(String.valueOf(o.getJsan()));

        }
        return v;
    }
}
