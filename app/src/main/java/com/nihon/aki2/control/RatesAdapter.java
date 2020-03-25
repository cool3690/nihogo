package com.nihon.aki2.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nihon.aki2.R;

import java.util.ArrayList;

public class RatesAdapter extends ArrayAdapter<Rate> {

    Context context;
    private ArrayList<Rate> rates;

    public RatesAdapter(Context context, int textViewResourceId, ArrayList<Rate> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.rates = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.rate, null);
        }
        Rate o = rates.get(position);
        if (o != null) {
            TextView coin = (TextView) v.findViewById(R.id.coin);
            TextView buy = (TextView) v.findViewById(R.id.buy);
            TextView sell = (TextView) v.findViewById(R.id.sell);

            coin.setText(String.valueOf(o.getCoin()));
            buy.setText(String.valueOf(o.getBuy()));
            sell.setText(String.valueOf(o.getSell()));

        }
        return v;
    }
}
