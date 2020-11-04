package com.nihon.aki2.control;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nihon.aki2.R;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.MyViewHolder> {
    private List<Exam> teams;


    private GestureDetector mGestureDetector;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    /**/
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
        MyViewHolder viewHolder = new MyViewHolder(itemView);

     //   itemView.setOnClickListener( this);

       // viewHolder.more.setOnClickListener(ExamsAdapter.this);

        itemView.setTag(viewHolder);

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
        holder.getAdapterPosition();
       // Toast.makeText(ExamsAdapter.this, "RecyclerView Item onClick at " + position, Toast.LENGTH_SHORT).show();
       // holder.getItemId();



    }
    @Override
    public int getItemCount() {

         return teams.size();

    }
    /*
    public void onClick(View view) {
        MyViewHolder holder = (MyViewHolder) view.getTag();
        int position = holder.getPosition();

        if (view.getId() == holder.imageIV.getId()){
            Toast.makeText(context, "imageIV onClick at" + position, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "RecyclerView Item onClick at " + position, Toast.LENGTH_SHORT).show();
        }
    }
     */


}