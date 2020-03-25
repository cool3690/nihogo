package com.nihon.aki2.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nihon.aki2.R;

import java.util.ArrayList;

public class JnewAdapter extends ArrayAdapter<Jnew> {

    Context context;
    private ArrayList<Jnew> rates;

    public JnewAdapter(Context context, int textViewResourceId, ArrayList<Jnew> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.rates = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.mynew, null);
        }
        Jnew o = rates.get(position);
        if (o != null) {
            TextView jpnew = (TextView) v.findViewById(R.id.jpnew);

        // jpnew.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/genkai-minco.ttf"));
          //jpnew.setTextSize(25);
            jpnew.setText(String.valueOf(o.getJpnew()));
        }
        return v;
    }
}
