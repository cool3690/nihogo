package com.nihon.aki2.control;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nihon.aki2.R;

public class TeamsAdapter extends ArrayAdapter<Team> {

    Context context;
    private ArrayList<Team> teams;

    public TeamsAdapter(Context context, int textViewResourceId, ArrayList<Team> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.teams = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.team, null);
        }
        Team o = teams.get(position);
        if (o != null) {
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView week = (TextView) v.findViewById(R.id.week);
            TextView time = (TextView) v.findViewById(R.id.time);
            TextView date=(TextView) v.findViewById(R.id.date);
            TextView price = (TextView) v.findViewById(R.id.price);
          //  Button bt=(Button)v.findViewById(R.id.bt);
            name.setText(String.valueOf(o.getName()));
            week.setText(String.valueOf(o.getWeek()));
            time.setText(String.valueOf(o.getTime()));
            price.setText(String.valueOf(o.getPrice()));
            date.setText(String.valueOf(o.getDate()));
            //bt.setText(String.valueOf(o.getBt()));
         
        }
        return v;
    }
}
