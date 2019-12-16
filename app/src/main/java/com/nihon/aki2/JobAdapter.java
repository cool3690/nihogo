package com.nihon.aki2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class JobAdapter extends ArrayAdapter<Job> {

    Context context;
    private ArrayList<Job> jobs;

    public JobAdapter(Context context, int textViewResourceId, ArrayList<Job> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.jobs = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.worknote, null);
        }
        Job o = jobs.get(position);
        if (o != null) {
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView place = (TextView) v.findViewById(R.id.place);

            TextView price = (TextView) v.findViewById(R.id.price);
            name.setText(String.valueOf(o.getName()));
            price.setText(String.valueOf(o.getPrice()));
            place.setText(String.valueOf(o.getPlace()));

        }
        return v;
    }
}
