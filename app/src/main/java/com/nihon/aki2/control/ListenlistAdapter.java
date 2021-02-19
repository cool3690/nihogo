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
import java.util.regex.Pattern;

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
            int num=0;
            Pattern pattern = Pattern.compile("[0-9]*");
            if(tmp.contains("/")){
                String[] t1 = tmp.split("/");
                String t2= t1[0].replaceAll("L","");
                if( pattern.matcher(t2).matches() == true){
                    num=Integer.parseInt(t2);
                    //do somethings

                }

            }

            else{
                String[] t3 = tmp.split(" ");
                if(t3[0].contains("L")){
                    String[] t1 = t3[0].split("_L");
                    String t2= t1[1] ;
                    if( pattern.matcher(t2).matches() == true){
                        num=Integer.parseInt(t2);
                        //do somethings

                    }
                    else{
                        num=0;
                    }
                   // num=Integer.parseInt(t2);
                }
                else{
                    num=1;
                }

            }

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
