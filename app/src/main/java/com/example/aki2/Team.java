package com.example.aki2;

import android.widget.Button;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Team {
    private String name, week, time,price,date;
    private Button  bt;
    public Team(String name, String week, String time,String date,String price)
    {
        this.setName(name);
        this.setWeek(week);
        this.setTime(time);
        this.setPrice(price);
        this.setDate(date);
       
    }
    public String getDate() {
        return date;
      }

      public void setDate(String date) {
        this.date = date;
      }
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getWeek() {
      return week;
    }

    public void setWeek(String week) {
      this.week = week;
    }

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }


}
