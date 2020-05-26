package com.nihon.aki2.control;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nihon.aki2.R;

import java.util.ArrayList;

public class ListenlistAdapter extends ArrayAdapter<Listenlist> {

    Context context;
    private ArrayList<Listenlist> listenlists;

    public ListenlistAdapter(Context context, int textViewResourceId, ArrayList<Listenlist> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.listenlists = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listenlist, null);
        }
        Listenlist o = listenlists.get(position);
        if (o != null) {
            TextView name = (TextView) v.findViewById(R.id.name);
            String tmp=String.valueOf(o.getName());
            String[] t1 = tmp.split("/");
           String t2= t1[0].replaceAll("L","");
           // String.valueOf(o.getName())
            int num=Integer.parseInt(t2);
            if(num%2==0){
                name.setText(String.valueOf(o.getName()));
                name.setTextColor(Color.BLUE);
            }
           else{
                name.setText(String.valueOf(o.getName()));
                name.setTextColor(Color.RED);
            }
        }
        return v;
    }
}
