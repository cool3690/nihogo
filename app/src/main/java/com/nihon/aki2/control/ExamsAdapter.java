package com.nihon.aki2.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nihon.aki2.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.MyViewHolder> {
    private List<Exam> teams;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView testinfo, jointime, testtime,countday,title;
        ImageView more;
        MyViewHolder(View v) {
            super(v);
              testinfo=(TextView) v.findViewById(R.id.testinfo);
              jointime=(TextView) v.findViewById(R.id.jointime);
             testtime=(TextView) v.findViewById(R.id.testtime);
             countday=(TextView) v.findViewById(R.id.countday);
              title=(TextView) v.findViewById(R.id.title);
              more=(ImageView) v.findViewById(R.id.more);
              /*
              more.setOnClickListener(new View.OnClickListener(){
                  p
              });

               */
        }
    }
    public ExamsAdapter(List<Exam> teams) {
        this.teams = teams;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Exam o = teams.get(position);

        holder.testinfo.setText(String.valueOf(o.getTestinfo()));
        holder. jointime.setText(String.valueOf(o.getJointime()));
        holder.testtime.setText(String.valueOf(o.getTesttime()));
        holder.countday.setText(String.valueOf(o.getCountday()));
        holder.title.setText(String.valueOf(o.getTitle()));

        holder. more.setImageResource(o.getMore());
       // holder.getItemId();

    }
    @Override
    public int getItemCount() {

         return teams.size();

    }



}